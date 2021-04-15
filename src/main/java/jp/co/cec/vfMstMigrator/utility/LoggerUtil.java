package jp.co.cec.vfMstMigrator.utility;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.NullEnumeration;

import jp.co.cec.vfMstMigrator.data.LogData;
import jp.co.cec.vfMstMigrator.data.constant.Constants;

/**
 * ログの出力先にログファイルを出力できるか確認するクラス
 */
public class LoggerUtil {
	
	/**
	 * ロガー
	 */
	private static Logger myLogger = null;
	
	/**
	 * ロガー名
	 */
	private static String name = null;
	
	/**
	 * ロガー名セッター
	 * @param name　ロガー名
	 */
	public static void setName(String name) {
		LoggerUtil.name = name;
	}
	
	/**
	 * ロガーゲッター
	 * @return　ロガー
	 */
	public static Logger getLogger() {
		LoggerUtil.setLogger();
		return LoggerUtil.myLogger;
	}
	/**
	 * ロガーをセット
	 */
	private static void setLogger() {
		if (LoggerUtil.myLogger == null) {
			LoggerUtil.myLogger = LogManager.getLogger(LoggerUtil.name);
		}
	}
	
	/**
	 * ログの出力先にログファイルを出力できるか確認を行う<br>
	 * 出力出来る場合はtrueを返す
	 * 
	 * @param logFileDestination ログ出力先のパス
	 * @param isDialog           trueのとき、ダイアログ表示もする. なお、どちらでも System.out.println する.
	 * @return ログが出力出来る場合はtrueを返す
	 */
	public static boolean canWriteLog(boolean isDialog, String name) {
		boolean canWriteLog = false;
		
		//　ロガー名セット
		setName(name);
		
		// Loggerのインスタンスを作成する
		myLogger = LogManager.getLogger(name);
		// アペンダが存在するか確認（設定ファイルが読み込めていない場合アペンダが存在しない）
		if (NullEnumeration.getInstance().equals(myLogger.getAllAppenders())) {
			String msg = "log4jの設定ファイルの読み込みに失敗しました。";
			DialogUtil.showMessageDialogWithTextArea(isDialog, msg);
			return canWriteLog;
		}
		canWriteLog = true;
		return canWriteLog;
	}
	
	public static boolean setLogPath(String logFileDestination, boolean isDialog, String name, String filePath) {
		boolean canWrite = false;
		System.setProperty(filePath, logFileDestination);
		try {
			File logFile = new File(logFileDestination);
			if (logFile.exists()) {
				if (!logFile.canWrite()) {
					String msg = "Logの出力先が書き込み不可のため、起動できませんでした。 logFileDestination:" + logFileDestination;
					DialogUtil.showMessageDialogWithTextArea(isDialog, msg);
					return canWrite;
				}
				FileChannel fc = null;
				try {
					fc = FileChannel.open(logFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
					FileLock lock = fc.tryLock();
					if (Objects.isNull(lock)) {
						throw new Exception();
					} else {
						lock.release();
					}
				} catch (Exception e) {
					String msg = "Logの出力先が他プロセスでロック中のため、起動できませんでした。 logFileDestination:" + logFileDestination;
					DialogUtil.showMessageDialogWithTextArea(isDialog, msg);
					return canWrite;
				} finally {
					if (Objects.nonNull(fc))
						fc.close();
				}
			} else {
				Boolean createNewFile = logFile.createNewFile();
				if (!createNewFile) {
					String msg = "Logの出力先が異常なため、起動できませんでした。 logFileDestination:" + logFileDestination;
					DialogUtil.showMessageDialogWithTextArea(isDialog, msg);
					return canWrite;
				}
			}
		} catch (Exception e) {
			String msg = "Logの出力先が異常なため、起動できませんでした。 logFileDestination:" + logFileDestination;
			DialogUtil.showMessageDialogWithTextArea(isDialog, msg);
			return canWrite;
		}
		
		return true;
	}
	
	/**
	 * ダイアログ及びログ、ログファイルに出力
	 * @param message　出力メッセージ
	 * @param success　ログレベル　(true - info, false - error)
	 */
	public static void printMessageLogData(String message, boolean success) {
		LoggerUtil.setLogger();
		printMessage(message, success);
		LogData.get().content.append(message);
	}
	
	/**
	 * ダイアログ及びログ出力
	 * @param msg　出力メッセージ
	 * @param success　ログレベル　(true - info, false - error)
	 */
	public static void printMessage(String message, boolean success) {
		LoggerUtil.setLogger();
		DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, message);
		if (success) {
			myLogger.info(message);
		} else {
			myLogger.error(message);
		}
	}
}
