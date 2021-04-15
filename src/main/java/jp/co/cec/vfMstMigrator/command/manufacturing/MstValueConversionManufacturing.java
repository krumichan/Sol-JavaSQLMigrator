package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_threshold
 * V2：mst_value_conversion
 */
public class MstValueConversionManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_value_conversion");
		oiq.setColumnName(new String[] { "signal_class", "signal_category", "signal_name", "resource_name", "value", "conversion_value", "conversion_type", "created_by", "created_at" });
		oiq.setPrimaryKey(new String[] { "signal_class", "signal_category", "signal_name", "value", "resource_name" });
		
		oiq.put(rs.getString("signal_class"))
		.put(rs.getString("signal_category"))
		.put(rs.getString("signal_name"))
		.put(rs.getString("resource_name"))
		.put(rs.getString("value"))
		.put(rs.getString("conversion_value"))
		.put(rs.getString("conversion_type"))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")));

		return oiq.toString();
	}
}
