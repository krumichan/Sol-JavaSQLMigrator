package jp.co.cec.vfMstMigrator.command.executor;

import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.executor.ScriptEx;
import jp.co.cec.vfMstMigrator.template.Command;

/**
 * V1から取得し、保存したファイル(Insert文)を読み込み、V2に挿入するクラス
 */
public class InsertV2Command extends Command {

	/**
	 * V1から取得し、保存したファイル(Insert文)を読み込み、V2に挿入するメソッド
	 */
	@Override
	public String execute(String... data) {
		String result = null;
		String path = data[0];
		try {
			ScriptEx.execute(
					DatabaseConnector.getConnection(DatabaseConnector.DB.V2)
					,path);
		} catch (Exception e) {
			result = e.getMessage();
		}
		
		return result;
	}
}
