package jp.co.cec.vfMstMigrator.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import jp.co.cec.vfMstMigrator.utility.FileUtil;

/**
 * 共通データベースアクセスオブジェクト 
 */
public class CommonDao {
	
	private Connection conn = null;
	public CommonDao(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * データベースが存在しなければ生成する。
	 * @param dbName　生成するデータベース名
	 * @param useFlag　生成後、使用するか
	 * @return　生成結果
	 * @throws Exception　生成失敗
	 */
	public boolean createDatabase(String dbName, boolean useFlag) throws Exception {
		boolean result = false;
		String sql = "CREATE DATABASE IF NOT EXISTS" + dbName;
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			if (count == 1) {
				result = true;
				
				if (useFlag == true) {
					stmt.execute("USE " + dbName);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	/**
	 * 特定のテーブル名があるかをチェック
	 * @param tableName 特定のテーブル名
	 * @return 存在可否
	 * @throws Exception チェックに失敗
	 */
	public boolean checkTableExists(String tableName) throws Exception {
		boolean result = false;
		String sql = "SHOW TABLES LIKE '" + tableName + "'";
		
		Statement stmt = conn.createStatement();
		result = stmt.executeQuery(sql).next();
		
		return result;
	}
	
	/**
	 * 特定のテーブル名があるかをチェック
	 * @param tableName 特定のテーブル名
	 * @return 存在可否
	 * @throws Exception チェックに失敗
	 */
	public boolean checkDatabaseExists(String databaseName) throws Exception {
		boolean result = false;
		String sql = "SHOW DATABASES LIKE '" + databaseName + "'";
		
		Statement stmt = conn.createStatement();
		result = stmt.executeQuery(sql).next();
		
		return result;
	}
	
	/**
	 * 特定のテーブルとファイルの定義が一致しているかをチェック
	 * @param dbName 対象のデータベース名
	 * @param tableName 対象のテーブル名
	 * @param path 比較対象のファイル
	 * @return 一致するか
	 * @throws Exception 発生しない 
	 */
	public boolean checkTableDefinition(String dbName, String tableName, String path) throws Exception {
		boolean result = true;
		String sql = " show columns from " + dbName + "." + tableName;
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			List<String> list = FileUtil.readToStringArray(path);
			String primaryKeyLine = null;
			for (String line : list) {
				if (line.contains("PRIMARY KEY")) {
					primaryKeyLine = line;
					break;
				}
			}
			
			while (rs.next()) {
				boolean find = false;
				boolean isPrimary = false;
				for (String line : list) {
					boolean type = DBUtils.typeComp(line, rs.getString("Type"));
					if (line.contains(rs.getString("Field")) && type) {
						find = true;
						if (rs.getString("Key").equals(("PRI"))) {
							if (!primaryKeyLine.contains(rs.getString("Field"))) {
								find = false;
								break;
							} else {
								isPrimary = true;
							}
						}
						if (!isPrimary && rs.getString("Null").equals("NO")) {
							if (!line.contains("NOT NULL")) {
								find = false;
								break;
							}
						}
					}
					if (find) {
						break;
					}
				}
				
				if (!find) {
					result = false;
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtils.close(rs);
			DBUtils.close(stmt);
		}
		
		return result;
	}

	/**
	 * 特定のキャラクタセットを取得
	 * @param name 特定のキャラクタセット名
	 * @return キャラクタセットに該当するデータ
	 * @throws Exception クエリ実行失敗
	 */
	public String getCharacterSet(String name) throws Exception {
		String cs = DBUtils.convertCharacterSetName(name);
		
		String result = null;
		String sql = " SELECT @@" + cs;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new Exception(cs + "の読み込みに失敗しました。");
		} finally {
			DBUtils.close(rs);
			DBUtils.close(stmt);
		}
		
		return result;
	}
	
}	
