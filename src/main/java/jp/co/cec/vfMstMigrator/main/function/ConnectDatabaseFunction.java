package jp.co.cec.vfMstMigrator.main.function;

import java.util.Map;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.CommonDao;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.executor.ScriptEx;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.FileUtil;

/**
 * V1, V2 データベースに接続するクラス<br>
 * V1接続、V2生成及び接続、V2文字コード検査を行う
 */
public class ConnectDatabaseFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public ConnectDatabaseFunction() {
		startMsg = "データベースに接続します。";
		endMsg = "データベースに接続しました。";
	}
	
	/**
	 * V1, V2 データベースに接続するメソッド<br>
     * V1接続、V2生成及び接続、V2文字コード検査を行う
	 * @throws Exception 接続失敗又は文字コードが一致しない
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		//　V1接続
		String v1DbName = config.v1DBName;
		String v1Address = config.v1ServerAddress;
		String v1User = config.v1UserName;
		String v1Pass = config.v1Password;
		try {
			DatabaseConnector.connect(DatabaseConnector.DB.V1, v1DbName, v1Address, v1User, v1Pass);
		} catch (Exception e) {
			throw new Exception("message : " + e.getMessage() + "\n"
					+ v1DbName + " db name : " + v1DbName + "\n"
					+ v1DbName + " db server address : " + v1Address + "\n"
					+ v1DbName + " db user name : " + v1User + "\n"
					+ v1DbName + " db password : " + v1Pass + "\n"
					+ v1DbName + " dbの接続に失敗しました。");
		}
		
		//　V2接続
		String v2DbName = config.v2DBName;
		String v2Address = config.v2ServerAddress;
		String v2User = config.v2UserName;
		String v2Pass = config.v2Password;
		
		String ddlPath = config.ddlPath;
		String path = ddlPath + "fileName" + Constants.CREATE_EXTENSION;
		//　DDLフォルダが存在するかを確認
		if (!FileUtil.exists(ddlPath)) {
			throw new Exception(ddlPath + "フォルダが存在しません。");
		}
				
		try {
			CommonDao dao = null;
			try {
				DatabaseConnector.connect(DatabaseConnector.DB.V2, "", v2Address, v2User, v2Pass);
				dao = new CommonDao(DatabaseConnector.getConnection(DatabaseConnector.DB.V2));
				
				//　V2データベース生成
				if (!dao.checkDatabaseExists(v2DbName)) {
					myLogger.info(v2DbName + "dbが存在しないので、生成します。");
					ScriptEx.execute(DatabaseConnector.getConnection(DatabaseConnector.DB.V2), path.replace("fileName", v2DbName));
					myLogger.info(v2DbName + "dbを生成しました。");
				}
				
				DatabaseConnector.connect(DatabaseConnector.DB.V2, v2DbName, v2Address, v2User, v2Pass);
				dao = new CommonDao(DatabaseConnector.getConnection(DatabaseConnector.DB.V2));
			} catch (Exception e) {
				throw new Exception("message : " + e.getMessage() + "\n"
						+ v2DbName + " db name : " + v2DbName + "\n"
						+ v2DbName + " db server address : " + v2Address + "\n"
						+ v2DbName + " db user name : " + v2User + "\n"
						+ v2DbName + " db password : " + v2Pass + "\n"
						+ v2DbName + " dbの接続に失敗しました。");
			}
			
			//　文字コード検査
			String codeClient = config.characterSet.Client;
			String codeConnection = config.characterSet.Connection;
			String codeDatabase = config.characterSet.Database;
			String codeFilesystem = config.characterSet.Filesystem;
			String codeResults = config.characterSet.Results;
			String codeServer = config.characterSet.Server;
			String dbClient = dao.getCharacterSet("Client");
			String dbConnection = dao.getCharacterSet("Connection");
			String dbDatabase = dao.getCharacterSet("Database");
			String dbFilesystem = dao.getCharacterSet("Filesystem");
			String dbResults = dao.getCharacterSet("Results");
			String dbServer = dao.getCharacterSet("Server");
			if (!dbClient.equals(codeClient) ||
				!dbConnection.equals(codeConnection) ||
				!dbDatabase.equals(codeDatabase) ||
				!dbFilesystem.equals(codeFilesystem) ||
				!dbResults.equals(codeResults) ||
				!dbServer.equals(codeServer)) {
				throw new Exception(v2DbName + " Databaseの文字コードが違っています。" + "\n"
						+ " code 設定のClient : " + codeClient + "/" + "DBのClient : " + dbClient + "\n"
						+ " code 設定のConnection : " + codeConnection + "/" + "DBのConnection : " + dbConnection + "\n"
						+ " code 設定のDatabase : " + codeDatabase + "/" + "DBのDatabase : " + dbDatabase + "\n"
						+ " code 設定のFilesystem : " + codeFilesystem + "/" + "DBのFilesystem : " + dbFilesystem + "\n"
						+ " code 設定のResults : " + codeResults + "/" + "DBのResults : " + dbResults + "\n"
						+ " code 設定のServer : " + codeServer + "/" + "DBのServer : " + dbServer + "\n");
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
