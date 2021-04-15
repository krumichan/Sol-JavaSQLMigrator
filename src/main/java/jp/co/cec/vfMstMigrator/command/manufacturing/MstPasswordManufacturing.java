package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_human
 * V2：mst_password
 */
public class MstPasswordManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_password");
		oiq.setColumnName(new String[] { "password_id", "user_id", "user_name", "password", "permission", "validity_start_on", "validity_end_on", "created_by", "created_at", "updated_by", "updated_at" });
		oiq.setPrimaryKey(new String[] { "password_id", "validity_start_on", "validity_end_on" });
		
		oiq.put(rs.getString("password_id"))
		.put(rs.getString("user_id"))
		.put(rs.getString("user_name"))
		.put(rs.getString("password"))
		.put(rs.getString("permission"))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")))
		.put(rs.getString("updated_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("updated_at")));

		return oiq.toString();
	}
}
