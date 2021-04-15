package jp.co.cec.vfMstMigrator.executor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.DBUtils;

/**
 * ファイルを読み込み、クエリを実行するクラス
 */
public class ScriptEx {
	private ScriptEx() { }
	
	private static final String UTF8_BOM = "\uFEFF";
	
	private static List<String> strList = 
			Arrays.asList(new String[] {
				"INSERT"
				,"CREATE"
				,"DROP"
			});
	
	/**
	 * sqlファイルを実行
	 * @param filePath 実行対象ファイルのパス
	 * @throws Exception SQLの実行エラー(もしくはファイルの読み込み失敗)
	 */
	public static void execute(Connection conn, String filePath) throws Exception {
		Statement stmt = null;
		try (FileInputStream fis = new FileInputStream(filePath);
			 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			 BufferedReader br = new BufferedReader(isr);) {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			int batchCount = 0;
			boolean existsFlag = false;
			String line = null;
			StringBuilder query = null;
			
			while ((line = br.readLine()) != null) {
				if (contains(line)) {
					batchCount++;
					existsFlag = true;
					
					if (query == null) {
						query = new StringBuilder(line);
						
					} else if (query != null) {
						stmt.addBatch(deleteBom(query.toString()));
						query = new StringBuilder(line);
						
					}
				} else {
					query.append(line);
				}
				
				if (batchCount == Constants.BATCH_SIZE) {
					stmt.executeBatch();
					stmt.clearBatch();
					batchCount = 0;
				}
				
			}
			
			if (!existsFlag) {
				throw new SQLException("実行クエリが1件も存在しません。");
			}
		
			//　必ず1行残っている
			stmt.addBatch(deleteBom(query.toString()));
			stmt.executeBatch();
			stmt.clearBatch();
			
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(stmt);
			DBUtils.rollback(conn);
			DBUtils.setAutoCommit(conn, true);
		}
	}
	
	/**
	 * UTF8のファイルの場合、 <br>
	 * 先頭にBOM文字が入っている場合がある。 <br>
	 * それの削除後の文字列を取得 <br>
	 * @param str　対象文字列
	 * @return　結果文字列
	 */
	private static String deleteBom(String str) {
		if (str.startsWith(UTF8_BOM)) {
			str = str.substring(1);
		}
		return str;
	}
	
	private static boolean contains(String line) {
		for (String str : strList) {
			if (line.contains(str)) {
				return true;
			}
		}
		return false;
	}
}