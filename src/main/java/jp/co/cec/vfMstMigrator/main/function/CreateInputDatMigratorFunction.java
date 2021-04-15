package jp.co.cec.vfMstMigrator.main.function;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.data.enums.KeyEnum;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.executor.NumberingEx;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;
import jp.co.cec.vfMstMigrator.utility.LoggerUtil;

/**
 * 手入力データ移行用のデータを作成
 */
public class CreateInputDatMigratorFunction extends AbstractFunction {

	/**
	 * 対象マップに入れるキー
	 */
	private String key = null;
	
	/**
	 * コンストラクタ
	 */
	public CreateInputDatMigratorFunction() {
		startMsg = "手入力データ移行ツール専用のデータを取得します。";
		endMsg = "手入力データ移行ツール専用のデータを取得しました。";
		key = KeyEnum.ToolToSigAndSigAcq.toString();
	}
	
	/**
	 * 手入力データ移行用のデータを作成
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		
		List<String[]> signalTypeList = new ArrayList<String[]>();
		
		//　以前処理で問題がなかったかを確認
		boolean signal = false;
		for (Entry<String, String[]> entry : CollectionUtil.clone(target).entrySet()) {
			String[] val = entry.getValue();
			if (val[0].contains("mst_signal")) {
				signal = true;
				break;
			}
		}
		
		//　対象マップに無ければ、処理を省略
		if (!signal) {
			return;
		}
		
		//　大分類を取得
		for (jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration.IdmMaster master : config.idmMaster) {
			String majorValue = master.major;
	
			//　中分類を取得
			for (jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration.Medium medium : master.medium) {
				String mediumValue = medium.value;
				
				//　小分類を取得
				for (String smallValue : medium.small) {
					signalTypeList.add(new String[] { majorValue, mediumValue, smallValue });
				}
			}
		}
		
		//　移行対象マップに入れる為のデータ準備
		String filePath = FormatValidationUtil.setLastSeparator(config.dmlPath) + key + ".sql";
		String[] data = new String[] { 
				key,
				filePath
				,config.validityDate
				};
		
		//　SQL文取得及びファイルに書込める
		LocalDateTime date = LocalDateTime.now();
		String message = null;
		boolean result = false;
		try (FileOutputStream fos = new FileOutputStream(filePath, false);
			 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			 BufferedWriter writer = new BufferedWriter(osw);) {
			
			for (String[] signalType : signalTypeList) {
				writer.write(createMstSignal(signalType, date));
				writer.write(createMstSignalAcquisition(signalType, date));
			}

			result = true;
			message = "SELECT SUCCESS:" + key + "-- " + (signalTypeList.size() * 2) + "\n";
		} catch (Exception e) {
			result = false;
			message = "SELECT FAIL:" + key + " -- <理由> " + e.getMessage() + "\n";
		}
		
		//　書き込めるのに成功したら、対象マップに入れる
		if (result) {
			target.put(key, data);
		}
		
		LoggerUtil.printMessageLogData(message, result);
		
		/////////// test ///////////
		MstMigratorHashMaps.check();
		////////////////////////////
	}
	
	/**
	 * 信号マスタ向けのDMLを作成するメソッド
	 * @param signalType　信号3点タイプ
	 * @param date　作成日時
	 * @return　DML文
	 */
	private String createMstSignal(String[] signalType, LocalDateTime date) {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_signal");
		oiq.setColumnName(new String[] { "log_id", "signal_class", "signal_category", "signal_name", "data_type", "value_type", "time_division", "value_key_format", "retention_type", "retention_period", "test_code", "created_by", "created_at" });
		oiq.setPrimaryKey(new String[] { "log_id" });
		
		String signalClass = signalType[0];
		String signalCategory = signalType[1];
		String signalName = signalType[2];
		String dataType = "Cassandra";
		String valueType = "";
		String timeDivision = "";
		String valueKeyFormat = "";
		String rententionType = "{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}";
		Long rententionPeriod = 86400L;
		String testCode = "01";
		String createdBy = "MstMigrator";
		String createdAt = FormatValidationUtil.dateToString(date, false);
		
		oiq.put(NumberingEx.getLogId(testCode, signalClass, signalCategory, signalName, timeDivision))
		.put(signalClass)
		.put(signalCategory)
		.put(signalName)
		.put(dataType)
		.put(valueType)
		.put(timeDivision)
		.put(valueKeyFormat)
		.put(rententionType)
		.put(rententionPeriod)
		.put(testCode)
		.put(createdBy)
		.put(createdAt);

		return oiq.toString();
	}

	/**
	 * 信号取得マスタ向けのDMLを作成するメソッド
	 * @param signalType 信号3点タイプ
	 * @param date 作成日時
	 * @return DML文
	 */
	private String createMstSignalAcquisition(String[] signalType, LocalDateTime date) {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_signal_acquisition");
		oiq.setColumnName(new String[] { "signal_id", "connector", "connector_instance", "signal_class", "signal_category", "signal_name", "subject_instance", "value_instance", "conversion", "test_code", "created_by", "created_at" });
		oiq.setPrimaryKey(new String[] { "signal_id" });
		
		String connector = "facteye";
		String connectorInstance = "MstMigrator";
		String signalClass = signalType[0];
		String signalCategory = signalType[1];
		String signalName = signalType[2];
		String subjectInstance = "none";
		String valueInstance = "none";
		String conversion = "";
		String testCode = "01";
		String createdBy = "MstMigrator";
		String createdAt = FormatValidationUtil.dateToString(date, false);
		
		oiq.put(NumberingEx.getSignalId(testCode, signalClass, signalCategory, signalName, connectorInstance, subjectInstance, valueInstance))
		.put(connector)
		.put(connectorInstance)
		.put(signalClass)
		.put(signalCategory)
		.put(signalName)
		.put(subjectInstance)
		.put(valueInstance)
		.put(conversion)
		.put(testCode)
		.put(createdBy)
		.put(createdAt);
		
		return oiq.toString();
	}
}
