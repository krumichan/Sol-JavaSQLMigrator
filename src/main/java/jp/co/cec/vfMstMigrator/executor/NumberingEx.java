package jp.co.cec.vfMstMigrator.executor;

import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;

/**
 * 採番APIから採番を取得
 */
public class NumberingEx {
	
	private static final String COL_NAME_TEST_CODE = "test_code";
	private static final String COL_NAME_RESOURCE_NAME = "resource_name";
	private static final String COL_NAME_SIGNAL_CLASS = "signal_class";
	private static final String COL_NAME_SIGNAL_CATEGORY = "signal_category";
	private static final String COL_NAME_SIGNAL_NAME = "signal_name";
	private static final String COL_NAME_TIME_DIVISION = "time_division";
	private static final String COL_NAME_CONNECTOR_INSTANCE = "connector_instance";
	private static final String COL_NAME_SUBJECT_INSTANCE = "subject_instance";
	private static final String COL_NAME_VALUE_INSTANCE = "value_instance";
	
	private static final String PREFIX_RESOURCE_ID = "res_id";
	private static final String PREFIX_LOG_ID = "log_id";
	private static final String PREFIX_SIGNAL_ID = "signal_id";
	
	private static final String SEPARATOR = "_";
	
	private NumberingEx() { }
	
	/**
	 * 資源IDを取得
	 * @return 資源ID
	 * @throws Exception　資源ID取得失敗
	 */
	public static String getResourceId(String testCode, String resourceName) {
		
		//　各項目を登録(登録済みの場合スキップ)
		String snTestCode = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_TEST_CODE, testCode);
		if (snTestCode == null) {
			snTestCode = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_TEST_CODE, testCode);
		}
		String snResourceName = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_RESOURCE_NAME, resourceName);
		if (snResourceName == null) {
			snResourceName = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_RESOURCE_NAME, resourceName);
		}
		
		return PREFIX_RESOURCE_ID + SEPARATOR + snTestCode + SEPARATOR + snResourceName;
	}
	
	/**
	 * 信号IDを取得
	 * @return 信号ID
	 * @throws Exception　信号ID取得失敗
	 */
	public static String getSignalId(String testCode, String signalClass, String signalCategory, String signalName, String connectorInstance, String subjectInstance, String valueInstance) {
		
		//　各項目を登録(登録済みの場合スキップ)
		String snTestCode = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_TEST_CODE, testCode);
		if (snTestCode == null) {
			snTestCode = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_TEST_CODE, testCode);
		}
		String snSignalClass = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_CLASS, signalClass);
		if (snSignalClass == null) {
			snSignalClass = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_CLASS, signalClass);
		}
		String snSignalCategory = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_CATEGORY, signalCategory);
		if (snSignalCategory == null) {
			snSignalCategory = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_CATEGORY, signalCategory);
		}
		String snSignalName = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_NAME, signalName);
		if (snSignalName == null) {
			snSignalName = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_NAME, signalName);
		}
		String snConnectorInstance = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_CONNECTOR_INSTANCE, connectorInstance);
		if (snConnectorInstance == null) {
			snConnectorInstance = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_CONNECTOR_INSTANCE, connectorInstance);
		}
		String snSubjectInstance = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SUBJECT_INSTANCE, subjectInstance);
		if (snSubjectInstance == null) {
			snSubjectInstance = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SUBJECT_INSTANCE, subjectInstance);
		}
		String snValueInstance = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_VALUE_INSTANCE, valueInstance);
		if (snValueInstance == null) {
			snValueInstance = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_VALUE_INSTANCE, valueInstance);
		}
		
		return PREFIX_SIGNAL_ID + SEPARATOR 
				+ snTestCode + SEPARATOR 
				+ snSignalClass + SEPARATOR 
				+ snSignalCategory + SEPARATOR 
				+ snSignalName + SEPARATOR
				+ snConnectorInstance + SEPARATOR
				+ snSubjectInstance + SEPARATOR
				+ snValueInstance;
	}
	
	/**
	 * ログIDを取得
	 * @return ログID
	 * @throws Exception　ログID取得失敗
	 */
	public static String getLogId(String testCode, String signalClass, String signalCategory, String signalName, String timeDivision) {

		//　各項目を登録(登録済みの場合スキップ)
		String snTestCode = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_TEST_CODE, testCode);
		if (snTestCode == null) {
			snTestCode = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_TEST_CODE, testCode);
		}
		String snSignalClass = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_CLASS, signalClass);
		if (snSignalClass == null) {
			snSignalClass = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_CLASS, signalClass);
		}
		String snSignalCategory = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_CATEGORY, signalCategory);
		if (snSignalCategory == null) {
			snSignalCategory = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_CATEGORY, signalCategory);
		}
		String snSignalName = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_SIGNAL_NAME, signalName);
		if (snSignalName == null) {
			snSignalName = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_SIGNAL_NAME, signalName);
		}
		String snTimeDivision = MstMigratorHashMaps.containsAndGetCurSeiralNumber(COL_NAME_TIME_DIVISION, timeDivision);
		if (snTimeDivision == null) {
			snTimeDivision = MstMigratorHashMaps.getNewSerialNumber(COL_NAME_TIME_DIVISION, timeDivision);
		}
		
		return PREFIX_LOG_ID + SEPARATOR
				+ snTestCode + SEPARATOR 
				+ snSignalClass + SEPARATOR 
				+ snSignalCategory + SEPARATOR 
				+ snSignalName + SEPARATOR
				+ snTimeDivision;
	}
}
