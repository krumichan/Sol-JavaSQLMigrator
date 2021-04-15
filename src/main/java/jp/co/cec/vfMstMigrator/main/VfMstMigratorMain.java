package jp.co.cec.vfMstMigrator.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.model.ApplicationConfigurationKeyModel;
import jp.co.cec.vfMstMigrator.utility.DialogUtil;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;
import jp.co.cec.vfMstMigrator.utility.PropertyUtil;

/**
 * VF-MstMigratorメインクラス
 */
public class VfMstMigratorMain {
	
	/**
	 * ロガー
	 */
	public static Logger myLogger;
	
	/**
	 * 本処理
	 */
	private static VfMstMigratorRunner dts = null;
	
	/**
	 * DDL・DML実行結果を保持したメモリのバックアップ保存先
	 */
	public static String backupPath;
	
	/**
	 * doEndEventメソッドの重複実行を防ぐ為の変数	
	 */
	public static volatile boolean isEnded = false;

	/**
	 * VF-MstMigratorメインメソッド
	 * @param args 読み込む設定ファイルのパス
	 * @throws Exception 移行処理失敗
	 */
	public static void main(String[] args) throws Exception {
		//引数チェック
		if (!checkArguments(args)) {
			System.exit(1);
			return;
		}
		
		//　設定データ取得
		if (!readAppConf(args)) {
			System.exit(1);
			return;
		}
		
		//　ロガー前の準備
		if (!prepareBeforeLogger(null)) {
			System.exit(1);
			return;
		}
		
		myLogger.info("" + Constants.APPLICATION_NAME + "を起動します。");
		try {
			checkBackupPath(null);
		} catch (Exception e) {
			myLogger.fatal("プログラムが異常終了しました。" + ": " + e + (e.getCause() == null ? "" : ": " + e.getCause()), e);
		}
		
		try {
			// Ctrl+Cでシャットダウン
			Runtime.getRuntime().addShutdownHook(new Thread(Constants.APPLICATION_NAME + "-shutdown-hook") {
				@Override
				public void run() {
					//　サービスの処理が終わったら、終了処理実行チェック
					if (!isEnded) {
						myLogger.info(Constants.APPLICATION_NAME + " Shutdown-Hook");
						doEndEvent();
					}
				}
			});
			
			//　データ移行処理準備
			dts = new VfMstMigratorRunner(PropertyUtil.getProperty());
			dts.init();
			
			//　データ移行処理開始
			dts.execute();
		} catch (Exception e) {
			myLogger.fatal("プログラムが異常終了しました。\n", e);
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, "プログラムが異常終了しました。" + "\n" + e.getMessage());
		} finally {
			//　サービスの処理が終わったら、終了処理実行チェック
			if (!isEnded) {
				doEndEvent();
			}
		}
	}
	
	/**
	 * 終了処理
	 */
	public static void doEndEvent() {
		isEnded = true;
		
		myLogger.info("データ移行サービスを終了します。");
		if (dts != null) {
			dts.doEndEvent();
		}
		myLogger.info("データ移行サービスを終了しました。");
	}
	
	/**
	 * 設定ファイルパスがあるかを確認
	 * @param args 外から入った設定ファイルパス
	 * @return　設定ファイルの存在要否
	 */
	public static boolean checkArguments(String[] args) {
		//　引数チェック
		if (Objects.isNull(args) || args.length < 1) {
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, "Javaコマンドへの起動引数が異常なため、起動できませんでした。");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 設定ファイルパスから設定データを取得
	 * @param args 外から入った設定ファイルパス
	 * @return　設定データ取得成功要否
	 */
	public static boolean readAppConf(String[] args) {
		try {
			PropertyUtil.readProperties(args);
		} catch (Exception e) {
			String msg = String.format("共通設定ファイルの読み込みに失敗しました。ファイル名:%s", args[0]);
			DialogUtil.showMessageDialogWithTextArea(Constants.IS_SHOW_MESSAGE_DIALOG, msg);
			return false;
		}
		return true;
	}
	
	/**
	 *　ロガーを準備する
	 * @param cwdPath　現在場所
	 * @return　ロガー準備成功要否
	 */
	public static boolean prepareBeforeLogger(Path cwdPath) {
		//　カレントディレクトリ
		if (cwdPath == null) {
			cwdPath = getCWD();
		}
		
		String logFileDestination = PropertyUtil.get(ApplicationConfigurationKeyModel.ERROR_LOG_PATH);
		if (logFileDestination == null || logFileDestination.isEmpty()) {
			String message = ApplicationConfigurationKeyModel.ERROR_LOG_PATH + "がnull又は空なので、起動できませんでした。";
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, message);
			return false;
		}
		
		logFileDestination += Constants.LOG_FILE_NAME + ".log";
		if (!Paths.get(logFileDestination).isAbsolute()) {
			logFileDestination = cwdPath.resolve(logFileDestination).normalize().toString();
		}
		
		if (LoggerUtil.setLogPath(logFileDestination, Constants.IS_SHOW_MESSAGE_DIALOG, "myLogger", "dir_log")) {
		} else {
			DialogUtil.showMessageDialogWithText(Constants.IS_SHOW_MESSAGE_DIALOG, "LogPathの登録に失敗したので、起動できませんでした。");
			return false;
		}
		
		if (LoggerUtil.canWriteLog(Constants.IS_SHOW_MESSAGE_DIALOG, "myLogger")) {
			myLogger = LogManager.getLogger("myLogger");
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 「バックアップフォルダ（メモリ）」を確認 
	 * @param cwdPath カレントディレクトリ. nullの場合はシステムプロパティ user.dir が使用される.
	 * @returnカレントディレクトリ. nullの場合はシステムプロパティ user.dir が使用される.
	 * @throws Exception
	 */
	public static boolean checkBackupPath(Path cwdPath) throws Exception {
		//　カレントディレクトリ
		if (cwdPath == null) {
			cwdPath = getCWD();
		}
		
		backupPath = PropertyUtil.get(ApplicationConfigurationKeyModel.BACKUP_PATH);
		if (backupPath == null || backupPath.isEmpty()) {
			return false;
		}
		
		if (!Paths.get(backupPath).isAbsolute()) {
			backupPath = cwdPath.resolve(backupPath).normalize().toString();
		}
		
		if (!FileUtil.makeDir(backupPath)) {
			myLogger.error("バックアップフォルダ（メモリ） が異常です。アプリ終了します。");
			throw new Exception("バックアップフォルダ（メモリ） が異常です。アプリ終了します。");
		}
		
		return true;
	}
	
	/**
	 * カレントディレクトリを取得
	 * 
	 * @return
	 */
	public static Path getCWD() {
		String cwd = System.getProperty("user.dir", "");
		Path cwdPath = Paths.get(cwd);
		return cwdPath;
	}
	
}
