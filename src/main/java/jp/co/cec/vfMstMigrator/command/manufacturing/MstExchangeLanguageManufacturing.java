package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_exchange_language
 * V2：mst_exchange_language
 */
public class MstExchangeLanguageManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_exchange_language");
		oiq.setColumnName(new String[] { "name_id", "language_type", "name", "validity_start_on", "validity_end_on", "created_by", "created_at", "updated_by", "updated_at" });
		oiq.setPrimaryKey(new String[] { "name_id", "language_type", "validity_start_on", "validity_end_on" });
		
		oiq.put(rs.getString("name_id"))
		.put(rs.getString("language_type"))
		.put(rs.getString("name"))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")))
		.put(rs.getString("updated_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("updated_at")));
		
		return oiq.toString();
	}
}
