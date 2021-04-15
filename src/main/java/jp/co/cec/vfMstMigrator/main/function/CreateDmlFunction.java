package jp.co.cec.vfMstMigrator.main.function;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.db.MstSignalConversionDao;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;

/**
 * V1からデータを取得し、V2用に変換
 */
public class CreateDmlFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public CreateDmlFunction() {
		startMsg = "DML生成処理を開始します。";
		endMsg = "DML生成処理が終わりました。";
	}
	
	/**
	 * V1からデータを取得し、V2用に変換
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		//　生成したsql文を保存する為のフォルダ生成
		String dmlPath = FormatValidationUtil.setLastSeparator(config.dmlPath);
		if (!FileUtil.makeDir(FileUtil.absolute(dmlPath))) {
			throw new Exception("DMLフォルダの生成に失敗しました。アプリを終了します。");
		}
		
		//　Dml取得処理開始
		String validityDate = config.validityDate;
		for (Entry<String, String[]> entry : CollectionUtil.clone(target).entrySet()) {
			String key = entry.getKey();
			String[] data = new String[] { 
					key,
					dmlPath + key + (key.contains("configuration") ? ".json" : ".sql")
					,validityDate
					};
			
			//　V1 DatabaseからSql File(又は Json File)を生成
			LoggerUtil.printMessage(key + "DMLを生成します。", true);
			String result = CommandFactory.get().getCommand(key)[0].execute(data);
			LoggerUtil.printMessage(result, result.contains("SELECT SUCCESS"));
			
			//　保存した対象ファイルのパスを保持
			if (result.contains("fail cause")) {
				myLogger.error(key + "のDml生成に失敗しまして、関する処理を除外します。");
				CollectionUtil.remove(target, key);
				continue;
			}
			
			//　成功した場合、マップのデータを更新
			target.put(key, data);
		}
		
		//　信号変換マスタから該当するデータが無いキーをファイルで作成
		String prefix = "該当信号3点無し.txt";
		String fromFacilityConditionPath = dmlPath + "MstFacilityCondition" + prefix;
		String fromDataItemPath = dmlPath + "MstDataItem" + prefix;
		String fromThreshold = dmlPath + "MstThreshold" + prefix;
		printFile(fromFacilityConditionPath, validityDate);
		printFile(fromDataItemPath, validityDate);
		printFile(fromThreshold, validityDate);
	}
	
	/**
	 * fileを出力
	 * @param fileName　ファイル名
	 * @param resList　出力データ
	 */
	private void printFile(String fileName, String validityDate) throws Exception {
		List<String[]> resList = null;
		
		try {
			MstSignalConversionDao dao = new MstSignalConversionDao();
			if (fileName.contains("MstFacilityCondition")) {
				resList = dao.selectByMstFacilityCondition(validityDate);
			} else if (fileName.contains("MstDataItem")) {
				resList = dao.selectByMstDataItem(validityDate);
			} else if (fileName.contains("MstThreshold")) {
				resList = dao.selectByMstThreshold(validityDate);
			} else {
				return;
			}
			
			try (FileOutputStream tFos = new FileOutputStream(fileName, true);
				 OutputStreamWriter tOsw = new OutputStreamWriter(tFos, StandardCharsets.UTF_8);
				 BufferedWriter tWriter = new BufferedWriter(tOsw);) {
				if (!resList.isEmpty()) {
					myLogger.info("信号3点無しのデータが有ります。" + "\n" + "filepath : " + fileName);
				}
				for (String[] res : resList) {
					tWriter.write("[" + res[0] + "," + res[1] + "]\n");
				}
			}
		} catch (Exception e) {
			myLogger.error("fail cause:" + e.getMessage() + "\n" + "信号変換マスタのキーに該当無しのファイル作成に失敗しました。");
		}
	}
}
