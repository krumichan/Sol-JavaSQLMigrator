package jp.co.cec.vfMstMigrator.main;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.executor.FunctionEx;
import jp.co.cec.vfMstMigrator.main.function.BuildMstNumberingFunction;
import jp.co.cec.vfMstMigrator.main.function.ConnectDatabaseFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateDmlFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateInputDatMigratorFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateTableFunction;
import jp.co.cec.vfMstMigrator.main.function.EndFunction;
import jp.co.cec.vfMstMigrator.main.function.InsertDmlFunction;
import jp.co.cec.vfMstMigrator.main.function.InsertMstNumberingFunction;
import jp.co.cec.vfMstMigrator.main.function.LockMstNumberingFunction;
import jp.co.cec.vfMstMigrator.main.function.UnlockMstNumberingFunction;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;

/**
 * マスタデータ移行処理
 *
 */
public class VfMstMigratorRunner {

	/**
	 * v1データベースから取得済みデータを持っているマップ
	 * <String, String[]> -> <v2/v1マスタ名, 保存データ>
	 */
	public Map<String, String[]> mapDmlPath = new HashMap<String, String[]>();
	
	/**
	 * エラー発生し、エラーファイルをコピーするフォルダ
	 */
	public String errorFileBackupPath;
	
	/**
	 * コンストラクタ
	 */
	public VfMstMigratorRunner() { }
	
	/**
	 * コンストラクタ
	 */
	public VfMstMigratorRunner(JsonNode configNode) throws Exception {

		VfMstMigratorInitialization initializtion = new VfMstMigratorInitialization();
		MstMigratorConfiguration conf = initializtion.convertConfiguration(configNode); 
		MstMigratorHashMaps.config = conf;
		
		errorFileBackupPath = FormatValidationUtil.setLastSeparator(
				FormatValidationUtil.setLastSeparator(VfMstMigratorMain.backupPath)
				+ FormatValidationUtil.dateToString(LocalDateTime.now(), true)
				+ "_backup"
				);
		if (!FileUtil.makeDir(errorFileBackupPath)) {
			throw new Exception("backupファイル生成に失敗しました。");
		}
	}
	
	/**
	 * 移行処理起動準備
	 */
	public void init() {
		//　何もしない
	}

	/**
	 * 移行処理実行
	 */
	public void execute() throws Exception {
		//　DB接続
		FunctionEx.executeFunction(
				ConnectDatabaseFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		//　Setting Target keys
		setTargetKey();
		
		//　テーブル生成
		FunctionEx.executeFunction(
				CreateTableFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		// V2 mst_numbering lock
		FunctionEx.executeFunction(
				LockMstNumberingFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		// numbering master setting
		FunctionEx.executeFunction(
				BuildMstNumberingFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		//　登録用DML・画面設定ファイル生成
		FunctionEx.executeFunction(
				CreateDmlFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		//　手入力データ用の信号追加
		FunctionEx.executeFunction(
				CreateInputDatMigratorFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		//　ナンバリングマスタ更新
		FunctionEx.executeFunction(
				InsertMstNumberingFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		// V2 table unlock
		FunctionEx.executeFunction(
				UnlockMstNumberingFunction.class
				,mapDmlPath
				,errorFileBackupPath);
				
		//　データ挿入
		FunctionEx.executeFunction(
				InsertDmlFunction.class
				,mapDmlPath
				,errorFileBackupPath);
		
		//　終了処理　⇒　以降のdoEndEvent()から実施
	}
	
	/**
	 *　移行対象のキーをコマンドファクトリーから取得
	 */
	public void setTargetKey() {
		for (String key : CommandFactory.get().getKeys()) {
			mapDmlPath.put(key, null);
		}
	}
	
	/**
	 * Serviceを終了
	 */
	public void doEndEvent() {
		try {
			FunctionEx.executeFunction(
					EndFunction.class
					,mapDmlPath
					,errorFileBackupPath);
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * JUnitテスト用の初期化メソッド
	 */
	public void testInit(JsonNode configNode) throws Exception {
		
		//　設定データチェック
		VfMstMigratorInitialization init = new VfMstMigratorInitialization();
		MstMigratorConfiguration conf = init.convertConfiguration(configNode); 
		MstMigratorHashMaps.config = conf;
		
		errorFileBackupPath = "";
		MstMigratorHashMaps.refresh();
		
		LoggerUtil.setName("myLogger");
	}
}