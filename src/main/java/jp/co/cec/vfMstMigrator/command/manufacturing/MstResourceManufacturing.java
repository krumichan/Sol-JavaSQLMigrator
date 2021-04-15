package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.executor.NumberingEx;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_place_setting
 * V2：mst_resource
 */
public class MstResourceManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_resource");
		oiq.setColumnName(new String[] { "resource_id", "object_category", "resource_type", "resource_name", "resource_name_key", "parent_id", "test_code", "validity_start_on", "validity_end_on", "created_by", "created_at", "updated_by", "updated_at" });
		oiq.setPrimaryKey(new String[] { "resource_id", "validity_start_on", "validity_end_on" });
		
		oiq.put(NumberingEx.getResourceId(rs.getString("test_code"), rs.getString("resource_name_key")))
		.put(rs.getString("object_category"))
		.put(rs.getString("resource_type"))
		.put(rs.getString("resource_name"))
		.put(rs.getString("resource_name_key"))
		.put(rs.getString("parent_resource_name_key") == null ? "" : NumberingEx.getResourceId(rs.getString("test_code"), rs.getString("parent_resource_name_key")))
		.put(rs.getString("test_code"))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")))
		.put(rs.getString("updated_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("updated_at")));
		
		return oiq.toString();
	}
}
