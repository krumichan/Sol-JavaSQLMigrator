package jp.co.cec.vfMstMigrator.main.function;

import java.util.Map;

import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;

/**
 * V2データベースの全てのロックを解除 
 */
public class UnlockMstNumberingFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public UnlockMstNumberingFunction() {
		startMsg = "V2DBのロックを解除します。";
		endMsg = "V2DBのロックを解除しました。";
	}
	
	/**
	 * V2データベースの全てのロックを解除
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		try {
			DBUtils.unlock(
					DatabaseConnector.getConnection(DatabaseConnector.DB.V2));
		} catch (Exception e) {
			myLogger.error("V2DBのテーブルロック解除に失敗しました。");
		}
	}
}
