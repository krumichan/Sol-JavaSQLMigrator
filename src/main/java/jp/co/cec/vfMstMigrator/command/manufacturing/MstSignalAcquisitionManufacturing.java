package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.executor.NumberingEx;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_data_item
 * V2：mst_signal_acquisition
 */
public class MstSignalAcquisitionManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_signal_acquisition");
		oiq.setColumnName(new String[] { "signal_id", "connector", "connector_instance", "signal_class", "signal_category", "signal_name", "subject_instance", "value_instance", "conversion", "test_code", "created_by", "created_at" });
		oiq.setPrimaryKey(new String[] { "signal_id" });
		
		oiq.put(NumberingEx.getSignalId(rs.getString("test_code"), rs.getString("signal_class"), rs.getString("signal_category"), rs.getString("signal_name"), rs.getString("host_name"), rs.getString("element_index_key"), rs.getString("element_index")))
		.put(rs.getString("connector"))
		.put(rs.getString("connector_instance"))
		.put(rs.getString("signal_class"))
		.put(rs.getString("signal_category"))
		.put(rs.getString("signal_name"))
		.put(rs.getString("subject_instance"))
		.put(rs.getString("value_instance"))
		.put(rs.getString("conversion"))
		.put(rs.getString("test_code"))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")));
		
		return oiq.toString();
	}
}
