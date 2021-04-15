package jp.co.cec.vfMstMigrator.template;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 実行コマンド親
 */
public abstract class Command {
	
	/**
	 * ロガー
	 */
	protected Logger myLogger;
	
	/**
	 * コンストラクタ
	 */
	public Command() {
		myLogger = LogManager.getLogger("myLogger");
	}
	
	/**
	 * 取得メソッド
	 */
	public String execute(String... data) { return null; }
	
	/**
	 * 加工メソッド
	 */
	public String execute(java.sql.ResultSet rs) throws Exception { return null; }
}
