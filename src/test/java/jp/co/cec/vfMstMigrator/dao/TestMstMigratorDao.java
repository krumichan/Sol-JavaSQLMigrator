package jp.co.cec.vfMstMigrator.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.util.TestJsonUtil;

public class TestMstMigratorDao implements AutoCloseable {

	private static final String UTF8_BOM = "\uFEFF";
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public TestMstMigratorDao() { }
	public TestMstMigratorDao(String server, String user, String password, String dbName) throws Exception {
		try {
			String url = "jdbc:mariadb://" + server + "/" + dbName;
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			throw new Exception();
		}
	}
	
	public ResultSet selectAndGetResultSet(String tableName) throws Exception {
		if (rs != null && !rs.isClosed()) {
			DBUtils.close(rs);
		}
		String sql = " SELECT * FROM " + tableName;
		rs = stmt.executeQuery(sql);
		return rs;
	}
	
	public void dropDb(String dbName) throws Exception {
		String sql = "drop database " + dbName;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
		} finally {
			DBUtils.close(stmt);
			conn.setAutoCommit(true);
		}
	}

	public void runScript(String filename) throws Exception {
		String filePath = "MstMigrator" + "/" + filename;
		filePath = TestJsonUtil.getResourceFileFullPath(filePath);
		
		StringBuilder sb = new StringBuilder();
		Statement stmt = null;
		try (FileInputStream fis = new FileInputStream(filePath);
			 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			 BufferedReader br = new BufferedReader(isr);){
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}
				
				sb.append(line);
				if (line.contains(";")) {
					stmt.execute(deleteBom(sb.toString()));
					sb = new StringBuilder();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(stmt);
			conn.setAutoCommit(true);
		}
	}

	private String deleteBom(String str) {
		if (str.startsWith(UTF8_BOM)) {
			str = str.substring(1);
		}
		return str;
	}
	
	public void close() throws Exception {
		DBUtils.close(conn);
		DBUtils.close(stmt);
		DBUtils.close(rs);
	}
}
