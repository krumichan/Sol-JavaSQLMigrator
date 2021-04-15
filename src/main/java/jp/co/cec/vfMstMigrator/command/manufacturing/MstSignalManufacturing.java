package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.executor.NumberingEx;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_data_item
 * V2：mst_signal
 */
public class MstSignalManufacturing extends Command {
	
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_signal");
		oiq.setColumnName(new String[] { "log_id", "signal_class", "signal_category", "signal_name", "data_type", "value_type", "time_division", "value_key_format", "retention_type", "retention_period", "test_code", "created_by", "created_at" });
		oiq.setPrimaryKey(new String[] { "log_id" });
		
		oiq.put(NumberingEx.getLogId("01", rs.getString("signal_class"), rs.getString("signal_category"), rs.getString("signal_name"), rs.getString("time_division")))
		.put(rs.getString("signal_class"))
		.put(rs.getString("signal_category"))
		.put(rs.getString("signal_name"))
		.put(rs.getString("data_type"))
		.put(rs.getString("value_type"))
		.put(rs.getString("time_division"))
		.put(rs.getString("value_key_format"))
		.put(rs.getString("retention_type"))
		.put(rs.getLong("retention_period"))
		.put(rs.getString("test_code"))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")));

		return oiq.toString();
	}
}
