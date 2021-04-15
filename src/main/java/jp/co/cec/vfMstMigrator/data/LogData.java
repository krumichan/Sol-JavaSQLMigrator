package jp.co.cec.vfMstMigrator.data;

/**
 * (Singleton Pattern)<br>
 * 全域データ
 */
public class LogData {
	
	/**
	 *　コンストラクタ- 
	 */
	private LogData() { init(); }
	
	/**
	 *　インスタンス
	 */
	private static LogData instance = null;
	
	/**
	 * インスタンス取得
	 * @return　インスタンス
	 */
	public static LogData get() {
		return ((instance == null) ? (instance = new LogData()) : instance);
	}

	
	/**
	 * 全域データ
	 */
	public StringBuilder content;
	
	/**
	 * DataCenterのデータ初期化
	 */
	private void init() {
		content = new StringBuilder();
	}
}
