package jp.co.cec.vfMstMigrator.main.function;

import java.util.Map;
import java.util.Set;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.CommonDao;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.executor.ScriptEx;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * V2側のテーブルの生成又は定義検査
 */
public class CreateTableFunction extends AbstractFunction {
	
	/**
	 * コンストラクタ
	 */
	public CreateTableFunction() {
		startMsg = "テーブル生成処理を開始します。";
		endMsg = "テーブル生成処理を終了します。";
	}
	
	/**
	 * V2側のテーブルの生成又は定義検査
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		final Set<String> v2TableNameSet = CommandFactory.get().getV2TableNames();
		CommonDao dao = new CommonDao(DatabaseConnector.getConnection(DatabaseConnector.DB.V2));
		String ddlPath = FormatValidationUtil.setLastSeparator(config.ddlPath);
		String path = ddlPath + "fileName" + Constants.CREATE_EXTENSION;
		String dbName =  config.v2DBName;
		
		//　対象テーブル検査
		for (String name : v2TableNameSet) {
			
			try {
				//　テーブルが既に存在するかをチェック
				if (dao.checkTableExists(name)) {
					
					//　テーブル定義が一致するかをチェック
					if (!dao.checkTableDefinition(dbName, name, path.replace("fileName", name))) {
						throw new Exception("テーブルの定義が違っています。");
					}
					myLogger.info(name + "テーブルが既に存在するので、生成をスキップします。");
				} else {
					//　テーブル存在しないと生成する
					myLogger.info(name + "テーブルが存在しないので、生成します。");
					ScriptEx.execute(DatabaseConnector.getConnection(DatabaseConnector.DB.V2), path.replace("fileName", name));
					myLogger.info(name + "テーブルを生成しました。");
				}
			} catch (Exception e) {
				StringBuilder sb = new StringBuilder(name + "テーブル生成及び検査に失敗しました。" + "\n");
				sb.append(name + "テーブルに関する移行対象を除外します。理由：" + e.getMessage());
				myLogger.error(sb.toString());
				CollectionUtil.remove(target, name);
			}
		}
		
		//　ナンバリングテーブル検査
		try {
			//　テーブルが既に存在するかをチェック
			if (dao.checkTableExists(Constants.MST_NUMBERING)) {
				
				//　テーブル定義が一致するかをチェック
				if (!dao.checkTableDefinition(dbName, Constants.MST_NUMBERING, path.replace("fileName", Constants.MST_NUMBERING))) {
					throw new Exception(Constants.MST_NUMBERING + "の定義が違います。");
				}
				myLogger.info(Constants.MST_NUMBERING + "テーブルが既に存在するので、生成をスキップします。");
			} else {
				//　テーブル存在しないと生成する
				myLogger.info(Constants.MST_NUMBERING + "テーブルが存在しないので、生成します。");
				ScriptEx.execute(DatabaseConnector.getConnection(DatabaseConnector.DB.V2), path.replace("fileName", Constants.MST_NUMBERING));
				myLogger.info(Constants.MST_NUMBERING + "テーブルを生成しました。");
			}
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.MST_NUMBERING + "テーブル生成及び検査に失敗しました。" + "\n");
			sb.append(Constants.MST_NUMBERING + "テーブルに関する移行対象を除外します。理由：" + e.getMessage());
			myLogger.error(sb.toString());
			CollectionUtil.remove(target, "mst_signal");
			CollectionUtil.remove(target, "mst_signal_acquisition");
			CollectionUtil.remove(target, "mst_resource");
			CollectionUtil.remove(target, "mst_map");
		}
		
	}
}
