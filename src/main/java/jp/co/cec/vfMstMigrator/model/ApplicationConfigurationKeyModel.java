package jp.co.cec.vfMstMigrator.model;

/**
 * CommonAppricationで使用する設定項目名を保持するクラス
 */
public class ApplicationConfigurationKeyModel {
	
	/**
	 * V1サーバ接続先<br>
	 * 移行先DBサーバのアドレス<br>
	 * 初期値：
	 */
	public static final String V1_SERVER_ADDRESS = "v1ServerAddress";
	
	/**
	 * V1DB名<br>
	 * 移行元DB名<br>
	 * 初期値：
	 */
	public static final String V1_DB_NAME = "v1DBName";
	
	/**
	 * V1DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 * 初期値：
	 */
	public static final String V1_USER_NAME = "v1UserName";
	
	/**
	 * V1DBパスワード名<br>
	 * 移行元パスワード<br>
	 * 初期値：
	 */
	public static final String V1_PASSWORD = "v1Password";
	
	/**
	 * V2サーバ接続先<br>
	 * 移行先DBサーバのアドレス<br>
	 * 初期値：
	 */
	public static final String V2_SERVER_ADDRESS = "v2ServerAddress";
	
	/**
	 * V2DB名<br>
	 * 移行元DB名<br>
	 * 初期値：
	 */
	public static final String V2_DB_NAME = "v2DBName";
	
	/**
	 * V2DBユーザ名<br>
	 * 移行元ユーザ名<br>
	 * 初期値：
	 */
	public static final String V2_USER_NAME = "v2UserName";

	/**
	 * V2DBパスワード名<br>
	 * 移行元パスワード<br>
	 * 初期値：
	 */
	public static final String V2_PASSWORD = "v2Password";
	
	/**
	 * V2DB文字コード設定<br>
	 * 移行先DBに指定する文字コード設定<br>
	 * 初期値：
	 */
	public static final String CHARACTER_SET = "characterSet";
	
	/**
	 * V1データベースデータの有効期間<br>
	 * 初期値：無し
	 */
	public static final String VALIDITY_DATE = "validityDate";
	
	/**
	 * DDL配置フォルダ<br>
	 * 移行先DBと全テーブルのCREATE文格納先フォルダ<br>
	 * 初期値：
	 */
	public static final String DDL_PATH = "ddlPath";
	
	/**
	 * 実行結果ログ出力先<br>
	 * 実行結果ログ出力フォルダ<br>
	 * 初期値：
	 */
	public static final String RESULT_LOG_PATH = "resultLogPath";

	/**
	 * エラーログ出力先<br>
	 * エラーログ出力フォルダ<br>
	 * 初期値：
	 */
	public static final String ERROR_LOG_PATH = "errorLogPath";
	
	/**
	 * DDL/DML退避フォルダ<br>
	 * 実行結果DDL・DMLの退避フォルダ<br>
	 * 初期値：
	 */
	public static final String BACKUP_PATH = "backupPath";
	
	/**
	 * DML生成フォルダ<br>
	 * 移行DML生成フォルダ<br>
	 * 初期値：
	 */
	public static final String DML_PATH = "dmlPath";
	
	/**
	 * 手入力マスター移行対象<br>
	 * 手入力移行ツールで使用するデータ用<br>
	 * 初期値：
	 */
	public static final String IDM_MASTER = "idmMaster";
	
	public static class CS {
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： utf8mb4 
		 */
		public static final String CHARACTER_SET_CLIENT = "Client";
		
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： utf8mb4
		 */
		public static final String CHARACTER_SET_CONNECTION = "Connection";
		
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： latin1
		 */
		public static final String CHARACTER_SET_DATABASE = "Database";
		
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： binary
		 */
		public static final String CHARACTER_SET_FILESYSTEM = "Filesystem";
		
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： utf8mb4
		 */
		public static final String CHARACTER_SET_RESULTS = "Results";
		
		/**
		 * V2DB文字コード<br>
		 * 移行先DBに指定する文字コード<br>
		 * 初期値： latin1
		 */
		public static final String CHARACTER_SET_SERVER = "Server";
	}
}
