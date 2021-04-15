package jp.co.cec.vfMstMigrator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * mariaDBコネクタく荒らす
 */
public class DatabaseConnector {
	
	public static enum DB { V1, V2 }
	private static Map<DB, Connection> connMap = new HashMap<DB, Connection>();
	private DatabaseConnector() { }
	
	/**
	 * mariaDBに接続する
	 * @param dbName　データベース名
	 * @param address　接続先のアドレス
	 * @param user　接続先のユーザ名
	 * @param pass　接続先のパースワード
	 * @throws Exception　接続失敗
	 */
	public static void connect(DB connKey, String dbName, String address, String user, String pass) throws Exception {
		if (connMap.containsKey(connKey)) {
			disconnect(connKey);
		}
		
		String url = getUrl(dbName, address);
		
		Class.forName("org.mariadb.jdbc.Driver");
		connMap.put(connKey, DriverManager.getConnection(url, user, pass));
	}
	
	/**
	 * mariaDBを切断する
	 */
	public static void disconnect() throws Exception {
		for (Entry<DB, Connection> entry : connMap.entrySet()) {
			DBUtils.unlock(entry.getValue());
			DBUtils.close(entry.getValue());
		}
	}
	
	/**
	 * mariaDBを切断する
	 */
	private static void disconnect(DB key) {
		DBUtils.close(connMap.get(key));
	}
	
	/**
	 * 取得データからmariaDBのUrlを取得
	 * @param dbName　データベース名
	 * @param address　接続先のアドレス
	 * @return　背sつ族先のmariaDBのURL
	 */
	private static String getUrl(String dbName, String address) {
		return "jdbc:mariadb://" + address + "/" + dbName;
	}
	
	/**
	 * mariaDBコネクタを取得<br>
	 * 接続した履歴が無ければnullを返すので、
	 * connect()メソッドを使用して接続する必要がある
	 * @return　コネクタ
	 */
	public static Connection getConnection(DB db) {
		return connMap.get(db);
	}
}
