package jp.co.cec.vfMstMigrator.template;

import java.util.Map;

import org.apache.log4j.Logger;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.utility.DialogUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;

/**
 * 実行クラス
 */
public abstract class AbstractFunction {
	
	/**
	 * 実行前に出力するメッセージ
	 */
	protected String startMsg = null;
	
	/**
	 * 実行後に出力するメッセージ
	 */
	protected String endMsg = null;
	
	/**
	 * ロガー
	 */
	protected Logger myLogger = null;
	
	/**
	 * 設定データ
	 */
	protected MstMigratorConfiguration config = null;
	
	/**
	 * コンストラクタ
	 */
	public AbstractFunction() {
		myLogger = LoggerUtil.getLogger();
		config = MstMigratorHashMaps.config;
	}
	
	/**
	 * 実行メソッドを呼び出すメソッド
	 * @param target　処理対象テーブル情報
	 * @param errorFilePath　エラー時のバックアップの保存先
	 * @throws Exception　実行際、エラー発生
	 */
	public void run(Map<String, String[]> target, String errorFilePath) throws Exception {
		notice(startMsg);
		execute(target, errorFilePath);
		notice(endMsg);
	}

	/**
	 * 実行メソッド
	 * @param target　処理対象テーブル情報
	 * @param errorFilePath　エラー時のバックアップの保存先
	 * @throws Exception　実行際、エラー発生
	 */
	protected abstract void execute(Map<String, String[]> target, String errorFilePath) throws Exception;
	
	/**
	 * ダイアログ及びログ出力
	 * @param message　出力メッセージ
	 */
	private void notice(String message) {
		if (message == null || message.isEmpty()) {
			return;
		}
		
		DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, message);
		myLogger.info(message);
	}
}
