package jp.co.cec.vfMstMigrator.main.function;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import jp.co.cec.vfMstMigrator.data.LogData;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 *　終了処理
 */
public class EndFunction extends AbstractFunction {

	/**
	 * 終了処理
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		StringBuilder logData = LogData.get().content;
		String resultLogPath = FormatValidationUtil.setLastSeparator(config.resultLogPath);
		
		try {
			//　全てのデータベースロック解除
			DatabaseConnector.disconnect();
		} catch (Exception e) {
			myLogger.error(e.getMessage());
		}
		
		//　実行結果を書き出し
		int infoCount = 0, debugCount = 0, traceCount = 0, warnCount = 0, errorCount = 0, fatalCount = 0;
		try {
			ArrayList<String> array = FileUtil.readToStringArray(System.getProperty("dir_log"));
			
			//　ログレベルの計算			
			for (String str : array) {
				if 		(str.contains("INFO")) 	{ infoCount++; 	}
				else if (str.contains("DEBUG")) { debugCount++;	}
				else if (str.contains("TRACE")) { traceCount++;	}
				else if (str.contains("WARN")) 	{ warnCount++;	} 
				else if (str.contains("ERROR")) { errorCount++;	} 
				else if (str.contains("FATAL")) { fatalCount++;	}
			}
		} catch (Exception e) {
			myLogger.error("エラー頻度の調査ができないので全部0になります。");
		}
		
		// errorの個数 登録
		// backup path 登録
		// log level個数 登録
		logData
			.append("実行時エラーの件数:" + errorCount + "\n")
			.append("backup path:" + errorFilePath + "\n")
			.append("info:" + infoCount + ", debug:" + debugCount + ", trace:" + traceCount + ", warn:" + warnCount + ", error:" + errorCount + ", fatal:" + fatalCount + "\n\n");
		
		try {
			logData.insert(0, "[UPDATED AT]:" + FormatValidationUtil.dateToString(LocalDateTime.now(), false) + "\n");
			FileUtil.write("結果履歴", resultLogPath, logData.toString(), "txt", true);
		} catch (Exception e) {
			myLogger.error("結果ログの作成に失敗しました。");
		}
	}

}
