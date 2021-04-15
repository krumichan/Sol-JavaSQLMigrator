package jp.co.cec.vfMstMigrator.main.function;

import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;

/**
 * V2データベースに挿入
 */
public class InsertDmlFunction extends AbstractFunction {
	
	/**
	 * コンストラクタ
	 */
	public InsertDmlFunction() {
		startMsg = "データ挿入処理を開始します。";
		endMsg = "データ挿入処理が終了しました。";
	}
	
	/**
	 * V2データベースに挿入 
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		
		Map<String, String[]> clone = CollectionUtil.clone(target);
		CollectionUtil.remove(clone, "view_part_configuration");
		
		for (Entry<String, String[]> entry : clone.entrySet()) {
			String key = entry.getKey();
			String[] path = new String[] { entry.getValue()[1] };
			LoggerUtil.printMessage(key + "Dmlを挿入します。", true);
			String result = CommandFactory.get().insertCommand().execute(path);
			String message = null;
			if (result == null) {
				//　成功
				message = "INSERT SUCCESS:" + key + "\n";
				
			} else {
				message = "INSERT FAIL:" + key + " -- <理由> " + result + "\n";
				FileUtil.copy(path[0], errorFilePath + key + ".sql");
			}
			LoggerUtil.printMessageLogData(message, message.contains("INSERT SUCCESS"));
		}
	}
}
