package jp.co.cec.vfMstMigrator.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

public class MstSignalConversionDao {

	/**
	 * データ項目マスタをキーとして信号変換マスタから該当無しのデータを取得
	 * @param validityDate　有効期間
	 * @return　取得結果
	 * @throws Exception　取得失敗
	 */
	public List<String[]> selectByMstDataItem(String validityDate) throws Exception {
		return this.selectBy("element", "original_item_name", "mst_data_item", validityDate);
	}
	
	/**
	 * 設備状態マスタをキーとして信号変換マスタから該当無しのデータを取得
	 * @param validityDate　有効期間
	 * @return　取得結果
	 * @throws Exception　取得失敗
	 */
	public List<String[]> selectByMstFacilityCondition(String validityDate) throws Exception {
		return this.selectBy("element_key", "condition_id", "mst_facility_condition", validityDate);
	}
	
	/**
	 * 閾値マスタをキーとして信号変換マスタから該当無しのデータを取得
	 * @param validityDate　有効期間
	 * @return　取得結果
	 * @throws Exception　取得失敗
	 */
	public List<String[]> selectByMstThreshold(String validityDate) throws Exception {
		String sql = (
				""
				+ " SELECT "
				+ "		tb.#1 "
				+ " , 	tb.#2 "
				+ " FROM #5 tb "
				+ " LEFT JOIN mst_signal_conversion conv "
				+ " ON 		tb.#1 					= conv.#3 "
				+ " AND 	tb.#2 					= conv.#4 "
				+ " AND 	conv.validity_start_on <= ? "
				+ " AND 	conv.validity_end_on 	> ? "
				+ " WHERE 	tb.validity_start_on   <= ? "
				+ " AND 	tb.validity_end_on 		> ? "
				+ " AND		tb.#1 != 'quality_logid' "
				+ " AND		tb.#2 IS NOT NULL "
				+ " AND  (	conv.#3 IS NULL "
				+ " OR 		conv.#4 IS NULL) "
				+ " GROUP BY tb.#1, tb.#2 ")
				.replace("#1", "view_parts_id")
				.replace("#2", "conditions")
				.replace("#3", "view_parts_id")
				.replace("#4", "conditions")
				.replace("#5", "mst_threshold");
		return this.select(sql, validityDate);
	}
	
	/**
	 * mst_signal_conversionから該当しないデータを取得
	 * @param datas 取得するデータ ([0] key1, [1] key2, [2] tableName, [3] validityDate)
	 * @return
	 * @throws Exception
	 */
	private List<String[]> selectBy(String... datas) throws Exception {
		String sql = (
				""
				+ " SELECT "
				+ " 	tb.#1 "
				+ " ,	tb.#2 "
				+ " FROM #5 tb "
				+ " LEFT JOIN mst_signal_conversion conv "
				+ " ON 	tb.#1 					= conv.#3 "
				+ " AND tb.#2 					= conv.#4 "
				+ " AND conv.validity_start_on <= ? "
				+ " AND conv.validity_end_on 	> ? "
				+ " WHERE tb.validity_start_on <= ? "
				+ " AND   tb.validity_end_on 	> ? "
				+ " AND  (conv.#3 IS NULL "
				+ " OR 	  conv.#4 IS NULL) "
				+ " GROUP BY tb.#1, tb.#2 ")
				.replace("#1", datas[0])
				.replace("#2", datas[1])
				.replace("#3", "element_key")
				.replace("#4", "original_item_name")
				.replace("#5", datas[2]);
		return this.select(sql, datas[3]);
	}
	
	/**
	 * mst_signal_conversionから該当しないデータを取得
	 * @param sql　sql文
	 * @return　データ
	 * @throws Exception 取得失敗
	 */
	private List<String[]> select(String sql, String validityDate) throws Exception {
		List<String[]> resultList = new ArrayList<String[]>();
		
		Date date = FormatValidationUtil.stringToDate(validityDate);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DatabaseConnector.getConnection(DatabaseConnector.DB.V1).prepareStatement(sql);
			for (int index = 1; index <= FormatValidationUtil.specificStrCount(sql, "?"); index++) {
				stmt.setObject(index, date);
			}
			rs = stmt.executeQuery();
			while (rs.next()) {
				resultList.add(new String[]{ rs.getString(1), rs.getString(2) });
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DBUtils.close(rs);
			DBUtils.close(stmt);
		}
		return resultList;
	}
}
