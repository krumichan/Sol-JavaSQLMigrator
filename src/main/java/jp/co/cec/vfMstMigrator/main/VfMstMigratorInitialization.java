package jp.co.cec.vfMstMigrator.main;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration.IdmMaster;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration.Medium;
import jp.co.cec.vfMstMigrator.utility.JsonUtil;

public class VfMstMigratorInitialization {

	/**
	 * エラーメッセージ：configulationがnullです
	 */
	public static final String ERROR_STATE_CONFIGULATION_NULL = "configulationがnullです";

	/**
	 * エラーメッセージ：configulationの取得に失敗しました
	 */
	public static final String ERROR_STATE_CONFIGULATION_TYPE_ERROR = "configulationの取得に失敗しました";

	/**
	 * エラーメッセージ：v1ServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY = "v1ServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v1DBNameがnull又は空です
	 */
	public static final String ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY = "v1DBNameがnull又は空です";
	
	/**
	 * エラーメッセージ：v1UserNameがnull又は空です
	 */
	public static final String ERROR_STATE_V1_USER_NAME_NULLOREMPTY = "v1UserNameがnull又は空です";
	
	/**
	 * エラーメッセージ：V1Passwordがnull又は空です
	 */
	public static final String ERROR_STATE_V1_PASSWORD_NULLOREMPTY = "V1Passwordがnull又は空です";
	
	/**
	 * エラーメッセージ：v2ServerAddressがnull又は空です
	 */
	public static final String ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY = "v2ServerAddressがnull又は空です";
	
	/**
	 * エラーメッセージ：v2DBNameがnull又は空です
	 */
	public static final String ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY = "v2DBNameがnull又は空です";
	
	/**
	 * エラーメッセージ：v2UserNameがnull又は空です
	 */
	public static final String ERROR_STATE_V2_USER_NAME_NULLOREMPTY = "v2UserNameがnull又は空です";
	
	/**
	 * エラーメッセージ：V2Passwordがnull又は空です
	 */
	public static final String ERROR_STATE_V2_PASSWORD_NULLOREMPTY = "V2Passwordがnull又は空です";
	
	/**
	 * エラーメッセージ：characterSetがnullです
	 */
	public static final String ERROR_STATE_CHARACTER_SET_NULL = "characterSetがnullです";
	
	/**
	 * エラーメッセージ：Clientがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_CLIENT_NULLOREMPTY = "Clientがnull又は空です";
	
	/**
	 * エラーメッセージ：Connectionがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_CONNECTION_NULLOREMPTY = "Connectionがnull又は空です";
	
	/**
	 * エラーメッセージ：Databaseがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_DATABASE_NULLOREMPTY = "Databaseがnull又は空です";
	
	/**
	 * エラーメッセージ：Filesystemがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_FILESYSTEM_NULLOREMPTY = "Filesystemがnull又は空です";
	
	/**
	 * エラーメッセージ：Resultsがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_RESULTS_NULLOREMPTY = "Resultsがnull又は空です";
	
	/**
	 * エラーメッセージ：Serverがnull又は空です
	 */
	public static final String ERROR_STATE_CHARACTER_SET_SERVER_NULLOREMPTY = "Serverがnull又は空です";
	
	/**
	 * エラーメッセージ：validityDateがnull又は空です
	 */
	public static final String ERROR_STATE_VALIDITY_DATE_NULLOREMPTY = "validityDateがnull又は空です";
	
	/**
	 * エラーメッセージ：ddlPathがnull又は空です
	 */
	public static final String ERROR_STATE_DDL_PATH_NULLOREMPTY = "ddlPathがnull又は空です";
	
	/**
	 * エラーメッセージ：resultLogPathがnull又は空です
	 */
	public static final String ERROR_STATE_RESULT_LOG_PATH_NULLOREMPTY = "resultLogPathがnull又は空です";
	
	/**
	 * エラーメッセージ：errorLogPathがnull又は空です
	 */
	public static final String ERROR_STATE_ERROR_LOG_PATH_NULLOREMPTY = "errorLogPathがnull又は空です";
	
	/**
	 * エラーメッセージ：backupPathがnull又は空です
	 */
	public static final String ERROR_STATE_BACKUP_PATH_NULLOREMPTY = "backupPathがnull又は空です";
	
	/**
	 * エラーメッセージ：dmlPathがnull又は空です
	 */
	public static final String ERROR_STATE_DML_PATH_NULLOREMPTY = "dmlPathがnull又は空です";
	
	/**
	 * エラーメッセージ：idmMasterがnull又は空です
	 */
	public static final String ERROR_STATE_IDM_MASTER_NULLOREMPTY = "idmMasterがnull又は空です";
	
	/**
	 * エラーメッセージ：majorがnullです
	 */
	public static final String ERROR_STATE_MAJOR_NULL = "majorがnullです";
	
	/**
	 * エラーメッセージ：mediumがnull又は空です
	 */
	public static final String ERROR_STATE_MEDIUM_NULLOREMPTY = "mediumがnull又は空です";
	
	/**
	 * エラーメッセージ：valueがnullです
	 */
	public static final String ERROR_STATE_MEDIUM_VALUE_NULL = "valueがnullです";
	
	/**
	 * エラーメッセージ：smallがnull又は空です
	 */
	public static final String ERROR_STATE_SMALL_NULLOREMPTY = "smallがnull又は空です";
	
	/**
	 * エラーメッセージ：値がnullです
	 */
	public static final String ERROR_STATE_SMALL_VALUE_NULL = "値がnullです";
	
	/**
	 * FunctionのConfigurationを取得<br>
	 * 単体テストを可能にするためpublicとする
	 * @param configNode JSONのconfigurationノード
	 * @return Configuration
	 * @throws Exception(Configurationが異常で救いようがない)
	 */
	public MstMigratorConfiguration convertConfiguration(JsonNode configNode) throws Exception {
		
		if (configNode == null) {
			throw new Exception(ERROR_STATE_CONFIGULATION_NULL);
		}
		
		JsonUtil mapper = new JsonUtil();
		MstMigratorConfiguration config = null;
		try {
			config = mapper.convert(configNode, MstMigratorConfiguration.class);
		} catch (Exception e) {
		}
		if (config == null) {
			throw new Exception(ERROR_STATE_CONFIGULATION_TYPE_ERROR);
		}
		
		if (isNullOrEmpty(config.v1ServerAddress)) {
			throw new Exception(ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1DBName)) {
			throw new Exception(ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY);
		}

		if (isNullOrEmpty(config.v1UserName)) {
			throw new Exception(ERROR_STATE_V1_USER_NAME_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v1Password)) {
			throw new Exception(ERROR_STATE_V1_PASSWORD_NULLOREMPTY);
		}

		if (isNullOrEmpty(config.v2ServerAddress)) {
			throw new Exception(ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2DBName)) {
			throw new Exception(ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY);
		}

		if (isNullOrEmpty(config.v2UserName)) {
			throw new Exception(ERROR_STATE_V2_USER_NAME_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.v2Password)) {
			throw new Exception(ERROR_STATE_V2_PASSWORD_NULLOREMPTY);
		}
		
		if (Objects.isNull(config.characterSet)) {
			throw new Exception(ERROR_STATE_CHARACTER_SET_NULL);
		}
		
		if (isNullOrEmpty(config.characterSet.Client)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_CLIENT_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.characterSet.Connection)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_CONNECTION_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.characterSet.Database)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_DATABASE_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.characterSet.Filesystem)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_FILESYSTEM_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.characterSet.Results)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_RESULTS_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.characterSet.Server)) {
			throw new Exception("characterSet/" + ERROR_STATE_CHARACTER_SET_SERVER_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.validityDate)) {
			throw new Exception(ERROR_STATE_VALIDITY_DATE_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.ddlPath)) {
			throw new Exception(ERROR_STATE_DDL_PATH_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.resultLogPath)) {
			throw new Exception(ERROR_STATE_RESULT_LOG_PATH_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.errorLogPath)) {
			throw new Exception(ERROR_STATE_ERROR_LOG_PATH_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.backupPath)) {
			throw new Exception(ERROR_STATE_BACKUP_PATH_NULLOREMPTY);
		}
		
		if (isNullOrEmpty(config.dmlPath)) {
			throw new Exception(ERROR_STATE_DML_PATH_NULLOREMPTY);
		}
		
		List<IdmMaster> idmMasterList = config.idmMaster;
		if (isNullOrEmpty(idmMasterList)) {
			throw new Exception(ERROR_STATE_IDM_MASTER_NULLOREMPTY);
		}
		for (int idmIndex = 0; idmIndex < idmMasterList.size(); idmIndex++) {
			IdmMaster master = idmMasterList.get(idmIndex);
			
			if (Objects.isNull(master.major)) {
				throw new Exception("idmMaster[" + idmIndex + "]/" + ERROR_STATE_MAJOR_NULL);
			}
			
			if (isNullOrEmpty(master.medium)) {
				throw new Exception("idmMaster[" + idmIndex + "]/" + ERROR_STATE_MEDIUM_NULLOREMPTY);
			}
			
			List<Medium> mediumList = master.medium;
			for (int medIndex = 0; medIndex < mediumList.size(); medIndex++) {
				Medium medium = mediumList.get(medIndex);
				
				if (Objects.isNull(medium.value)) {
					throw new Exception("idmMaster[" + idmIndex + "]/" + "medium[" + medIndex + "]/" + ERROR_STATE_MEDIUM_VALUE_NULL);
				}
				
				if (isNullOrEmpty(medium.small)) {
					throw new Exception("idmMaster[" + idmIndex + "]/" + "medium[" + medIndex + "]/" + ERROR_STATE_SMALL_NULLOREMPTY);
				}
				
				List<String> smallList = medium.small;
				for (int smlIndex = 0; smlIndex < smallList.size(); smlIndex++) {
					String small = smallList.get(smlIndex);
					
					if (Objects.isNull(small)) {
						throw new Exception("idmMaster[" + idmIndex + "]/" + "medium[" + medIndex + "]/" + "[" + smlIndex + "]/" + ERROR_STATE_SMALL_VALUE_NULL);
					}
				}
			}
		}
		
		return config;
	}
	
	/**
	 * null又は空かを確認
	 * @param tar 確認対象
	 * @return 確認結果
	 */
	private boolean isNullOrEmpty(String tar) {
		return Objects.isNull(tar) || tar.isEmpty();
	}
	
	/**
	 * null又は空かを確認
	 * @param tar 確認対象
	 * @return 確認結果
	 */
	@SuppressWarnings("rawtypes")
	private boolean isNullOrEmpty(List tar) {
		return Objects.isNull(tar) || tar.isEmpty();
	}
}
