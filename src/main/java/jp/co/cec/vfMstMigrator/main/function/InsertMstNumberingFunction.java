package jp.co.cec.vfMstMigrator.main.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * V1から取得し、生成したナンバリングデータをナンバリングマスタに挿入
 */
public class InsertMstNumberingFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public InsertMstNumberingFunction() {
		startMsg = "ナンバリングマスタを更新します。";
		endMsg = "ナンバリングマスタを更新しました。";
	}
	
	/**
	 * V1から取得し、生成したナンバリングデータをナンバリングマスタに挿入
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		String sql = ""
				+ " INSERT INTO mst_numbering "
				+ " ( column_name, serial_number, value, created_by, created_at, updated_by, updated_at ) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
		
		int batchCount = 0;
		boolean executedFlag = false;
		final String by = "MstMigrator";
		final String date = LocalDateTime.now().toString().replace("T", " ");
		final Date at = FormatValidationUtil.stringToDate(date);
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DatabaseConnector.getConnection(DatabaseConnector.DB.V2);
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement(sql);
			final ConcurrentMap<String, CopyOnWriteArrayList<String[]>> newNumberingMap = MstMigratorHashMaps.newNumberingMap;
			
			for (Entry<String, CopyOnWriteArrayList<String[]>> entry : newNumberingMap.entrySet()) {
				final String columnName = entry.getKey();
				final List<String[]> numberingList = entry.getValue();
				
				for (String[] dat : numberingList) {
					String seralNumber = dat[1];
					String value = dat[0];
					executedFlag = false;
					
					stmt.setString(1, columnName);
					stmt.setString(2, seralNumber);
					stmt.setString(3, value);
					stmt.setString(4, by);
					stmt.setObject(5, at);
					stmt.setString(6, by);
					stmt.setObject(7, at);
					
					stmt.addBatch();
					batchCount++;
					if (batchCount >= Constants.BATCH_SIZE) {
						executeBatch(stmt);
						batchCount = 0;
						executedFlag = true;
					}
				}
			}
			
			if (executedFlag == false) {
				executeBatch(stmt);
			}
			
			conn.commit();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.MST_NUMBERING + "挿入に失敗しました。" + "\n");
			sb.append(Constants.MST_NUMBERING + "テーブルに関する移行対象を除外します。理由：" + e.getMessage());
			myLogger.error(sb.toString());
			CollectionUtil.remove(target, "mst_signal");
			CollectionUtil.remove(target, "mst_signal_acquisition");
			CollectionUtil.remove(target, "mst_resource");
			CollectionUtil.remove(target, "mst_map");
		} finally {
			DBUtils.close(stmt);
			DBUtils.rollback(conn);
			DBUtils.setAutoCommit(conn, true);
		}
	}
	
	private void executeBatch(PreparedStatement stmt) throws Exception {
		stmt.executeBatch();
		stmt.clearBatch();
	}
}
