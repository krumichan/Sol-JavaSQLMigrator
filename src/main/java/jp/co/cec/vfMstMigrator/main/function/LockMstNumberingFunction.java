package jp.co.cec.vfMstMigrator.main.function;

import java.util.Map;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;

/**
 * V2のmst_numberingテーブルにロックを設定
 */
public class LockMstNumberingFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public LockMstNumberingFunction() {
		startMsg = "V2DBのmst_numberingをロック処理を開始します。";
		endMsg = "V2DBのmst_numberingをロック処理を終了しました。";
	}
	
	/**
	 * V2のmst_numberingテーブルにロックを設定
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		try {
			DBUtils.lock(
					DatabaseConnector.getConnection(DatabaseConnector.DB.V2)
					,Constants.MST_NUMBERING);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.MST_NUMBERING + "テーブルからデータ取得に失敗しました。" + "\n");
			sb.append(Constants.MST_NUMBERING + "テーブルに関する移行対象を除外します。理由：" + e.getMessage());
			myLogger.error(sb.toString());
			CollectionUtil.remove(target, "mst_signal");
			CollectionUtil.remove(target, "mst_signal_acquisition");
			CollectionUtil.remove(target, "mst_resource");
			CollectionUtil.remove(target, "mst_map");
		}
	}
}
