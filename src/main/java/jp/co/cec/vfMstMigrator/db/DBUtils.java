package jp.co.cec.vfMstMigrator.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfMstMigrator.data.constant.Constants;

import java.util.Objects;

/**
 * Database utility
 */
public class DBUtils {
	
	/**
	 * コネクタをクローズする
	 */
	public static void close(Connection conn) {
		if (Objects.isNull(conn)) {
			return;
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * ステートメントをクローズする
	 */
	public static void close(Statement stmt) {
		if (Objects.isNull(stmt)) {
			return;
		}
		
		try {
			stmt.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * リザルトセットをクローズする
	 */
	public static void close(ResultSet rs) {
		if (Objects.isNull(rs)) {
			return;
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * コミットする<br>
	 * 例外を発生しない
	 */
	public static void commit(Connection conn) {
		if (Objects.isNull(conn)) {
			return;
		}
		
		try {
			conn.commit();
		} catch(SQLException e) {
		}
	}
	
	/**
	 * ロールバックする<br>
	 * 例外を発生しない
	 */
	public static void rollback(Connection conn) {
		if (Objects.isNull(conn)) {
			return;
		}
		
		try {
			conn.rollback();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * オートコミットを設定する<br>
	 * 例外を発生しない
	 */
	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		if (Objects.isNull(conn)) {
			return;
		}
		
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
		}
	}

	/**
	 * 特定テーブルをロックする
	 * @param conn　ロックをかけるコネクタ
	 * @param tableName　ロック対象テーブル
	 * @throws Exception　ロック失敗
	 */
	public static void lock(Connection conn, String tableName) throws Exception {
		String sql = " LOCK TABLE " + tableName + " WRITE ";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.setQueryTimeout(Constants.QUERY_WAIT_TIME_OUT);
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new Exception(
					"failed table lock... " + "\n" 
					+ "cause:" + e.getMessage());
		} finally {
			DBUtils.close(stmt);
		}
	}
	
	public static PreparedStatement lock(Connection conn, Map<String, String> nameAsMap) throws Exception {
		String sql = createLockSql(nameAsMap);
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			throw new Exception(
					"failed table lock... " + "\n" 
					+ "cause:" + e.getMessage());
		}
		return stmt;
	}
	
	private static String createLockSql(Map<String, String> nameAsMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(" LOCK TABLE ");
		boolean first = true;
		for (Entry<String, String> entry : nameAsMap.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.isEmpty()) {
				sb.append(value + " WRITE ");
			} else {
				sb.append(value + " AS " + key + " WRITE ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 全てのテーブルのロックを解除する
	 * @param conn　ロックを解除するコネクタ
	 * @throws Exception　解除失敗
	 */
	public static void unlock(Connection conn) throws Exception {
		String sql = " UNLOCK TABLES ";
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new Exception(
					"failed table unlock... " + "\n" 
					+ "cause:" + e.getMessage());
		} finally {
			close(stmt);
		}
	}
	
	/**
	 * mariaDB のタイプを比較する
	 * @param line　(DML)ファイルの内容1行
	 * @param type　比較するタイプ
	 * @return　比較結果
	 */
	public static boolean typeComp(String line, String type) {
		String onlyType = null;
		try {
			//　タイプがサイズを持っているかを確認
			onlyType = type.substring(0, type.indexOf("("));
		} catch (Exception e) {
		}

		//　サイズが無ければ、そのまま比較
		if (onlyType == null) {
			return line.contains(type);
			
		} else {
			//　サイズがあれば、lineにタイプを含めているかを確認
			if (!line.contains(onlyType)) {
				return false;
			}
			
			line = line.replace(" ", "");
			line = line.replace("\t", "");
			
			//　lineにもサイズがあれば、そのまま比較
			if (line.contains(onlyType + "(")) {
				return line.contains(type);
			}
			
			//　lineにサイズが無ければ、デフォルト設定だと判断し、デフォルト値を取得
			String defaultValueForOnlyType = null;
			if (Constants.DB_TYPE_DEFAULT_SIZE_MAP.containsKey(onlyType)) {
				defaultValueForOnlyType = Constants.DB_TYPE_DEFAULT_SIZE_MAP.get(onlyType);
			}
			
			//　タイプのサイズがデフォルト値と同じかを確認
			return type.contains(defaultValueForOnlyType);
		}
	}
	
	/**
	 * 入力されたデータ名のデータベース用に変換
	 * @param name 入寮されたデータ名
	 * @return データベース用の入力
	 * @throws Exception サポートしないキャラクタセット名
	 */
	public static String convertCharacterSetName(String name) throws Exception {
		String result = name.toLowerCase();
		switch (result) {
		case "client":		result = "CHARACTER_SET_CLIENT";		break;
		case "connection":	result = "CHARACTER_SET_CONNECTION";	break;
		case "database":	result = "CHARACTER_SET_DATABASE";		break;
		case "filesystem":	result = "CHARACTER_SET_FILESYSTEM";	break;
		case "results":		result = "CHARACTER_SET_RESULTS";		break;
		case "server":		result = "CHARACTER_SET_SERVER";		break;
		default:
			throw new Exception(result + "の文字コードはサポートしない文字コードです。");
		}
		
		return result;
	}
}
