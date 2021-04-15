package jp.co.cec.vfMstMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.dao.TestMstMigratorDao;
import jp.co.cec.vfMstMigrator.data.enums.KeyEnum;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.executor.FunctionEx;
import jp.co.cec.vfMstMigrator.function.TestMstMigratorFunction;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorRunner;
import jp.co.cec.vfMstMigrator.main.function.ConnectDatabaseFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateInputDatMigratorFunction;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

public class MstMigratorCreateDmlTest {

	private static ByteArrayOutputStream logOutputStream;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logOutputStream = new ByteArrayOutputStream();
		WriterAppender appender = new WriterAppender(new PatternLayout("[%p] %d %c %l%n - %m%n"), logOutputStream);
		LogManager.getRootLogger().addAppender(appender);
		LogManager.getRootLogger().setAdditivity(false);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		logOutputStream.reset();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test400_100() {
		// TO : mst_exchange_language
		// FROM : mst_exchange_language 
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_exchange_language (name_id,language_type,name,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('cec','String','cec','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE name='cec',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.ExLangToExLang.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_100" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.ExLangToExLang.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_100" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_200() {
		// TO : mst_map
		// FROM : mst_place_setting 
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_map (resource_id,spec,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','[{ \"backGround\": { \"rect\":[],\"image\":null},\"area\":{ \"polygon\":{\"outerLoop\":[-74.0,-207.0,-14.0,-207.0,-14.0,-160.0,-74.0,-147.0],\"innerLoops\":[]}}}]','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE spec='[{ \"backGround\": { \"rect\":[],\"image\":null},\"area\":{ \"polygon\":{\"outerLoop\":[-74.0,-207.0,-14.0,-207.0,-14.0,-160.0,-74.0,-147.0],\"innerLoops\":[]}}}]',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						,"INSERT INTO mst_map (resource_id,spec,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_2','[{ \"backGround\": { \"rect\":[-600.0,-400.0,600.0,400.0],\"image\":\"HEAD.png\"},\"area\":{ \"polygon\":{\"outerLoop\":[329.0,-75.0,549.0,-75.0,549.0,19.0,329.0,145.0],\"innerLoops\":[]}}}]','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE spec='[{ \"backGround\": { \"rect\":[-600.0,-400.0,600.0,400.0],\"image\":\"HEAD.png\"},\"area\":{ \"polygon\":{\"outerLoop\":[329.0,-75.0,549.0,-75.0,549.0,19.0,329.0,145.0],\"innerLoops\":[]}}}]',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.PlacSetToMap.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_200" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.PlacSetToMap.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_200" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_300() {
		// TO : mst_password
		// FROM : mst_human
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_password (password_id,user_id,user_name,password,permission,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('junit','junit','テスト利用者','junit','user','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE user_id='junit',user_name='テスト利用者',password='junit',permission='user',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.HumanToPass.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_300" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.HumanToPass.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_300" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_400() {
		// TO : mst_resource
		// FROM : mst_facility(重複)
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_resource (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','ローダー／コンベア','facility','電力PLC-01','電力PLC-01','res_id_1_2','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='ローダー／コンベア',resource_type='facility',resource_name='電力PLC-01',resource_name_key='電力PLC-01',parent_id='res_id_1_2',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.FaciToRes.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_400" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.FaciToRes.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_400" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_600() {
		// TO : mst_resource
		// FROM : mst_place_setting
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_resource (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','設備','work_place','電力PLC-02','電力PLC-02','res_id_1_2','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='設備',resource_type='work_place',resource_name='電力PLC-02',resource_name_key='電力PLC-02',parent_id='res_id_1_2',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.PlacSetToRes.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_600" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.PlacSetToRes.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_600" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_700() {
		// TO : mst_signal_acquisition
		// FROM : mst_data_item
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expectedForward = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_1_1_1_1_1','VF','FERSV5','トレサビ','現品入力結果','ー','EM29_STR','EM29_STR','','01','MstMigrator',"
						)
				);
		
		List<String> expectedBack = new ArrayList<String>( 
				Arrays.asList(
						") ON DUPLICATE KEY UPDATE connector='VF',connector_instance='FERSV5',signal_class='トレサビ',signal_category='現品入力結果',signal_name='ー',subject_instance='EM29_STR',value_instance='EM29_STR',conversion='',test_code='01',created_by='MstMigrator'"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.DatToSigAcq.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_700" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.DatToSigAcq.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.contains(expectedForward.get(index)));
					assertTrue(line.contains(expectedBack.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_700" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_800() {
		// TO : mst_signal
		// FROM : mst_data_item
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expectedForward = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_1_1','トレサビ','現品入力結果','ー','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator'"
						)
				);
		
		List<String> expectedBack = new ArrayList<String>( 
				Arrays.asList(
						") ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='ー',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator'"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.DatToSig.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_800" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.DatToSig.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.contains(expectedForward.get(index)));
					assertTrue(line.contains(expectedBack.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_800" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_900() {
		// TO : mst_value_conversion
		// FROM : mst_facility_condition
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_value_conversion (signal_class,signal_category,signal_name,resource_name,value,conversion_value,conversion_type,created_by,created_at) VALUES ('アラーム','アラーム情報','QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ','BH-1397','0000','QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ','Replacement','åå\\¥','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE conversion_value='QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ',conversion_type='Replacement',created_by='åå\\¥',created_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.FaciConToValCon.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_900" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.FaciConToValCon.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_900" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_950() {
		// TO : mst_signal
		// FROM : mst_facility_condition
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expectedForward = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_1_1','アラーム','アラーム情報','QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
						)
				);
		
		List<String> expectedBack = new ArrayList<String>( 
				Arrays.asList(
						"') ON DUPLICATE KEY UPDATE signal_class='アラーム',signal_category='アラーム情報',signal_name='QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.FaciConToSig.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_950" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.FaciConToSig.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.contains(expectedForward.get(index)));
					assertTrue(line.contains(expectedBack.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_950" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_1000() {
		// TO : mst_value_conversion
		// FROM : mst_threshold
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"INSERT INTO mst_value_conversion (signal_class,signal_category,signal_name,resource_name,value,conversion_value,conversion_type,created_by,created_at) VALUES ('稼働状態','稼働状態コード','0005_aos','','0005','操作電源入','Replacement','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE conversion_value='操作電源入',conversion_type='Replacement',created_by='junit',created_at='2020-01-08 00:00:00';"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.ThreToValCon.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_1000" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.ThreToValCon.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_1000" + ".sql"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_1100() {
		// TO : view_part_configuration
		// FROM : mst_threshold
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			conf = TestMstMigratorFunction.getConfigNode(fileName);
			dts = new VfMstMigratorRunner();
			dts.init();
			dts.testInit(conf);
			config = MstMigratorHashMaps.config;
			dao = new TestMstMigratorDao(
					config.v1ServerAddress
					,config.v1UserName
					,config.v1Password
					,"");
			dao.runScript("400/mstUtV1_400.sql");
			dao.runScript("400/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		List<String> expected = new ArrayList<String>( 
				Arrays.asList(
						"{\"boxColor\":[{\"priority\":1,\"maxValue\":1.0E8,\"minValue\":10.0,\"targetString\":null,\"colorCode\":\"#FF0000\"}],"
						,"\"color\":[{\"priority\":1,\"column\":null,\"targetString\":\"0005\",\"colorCode\":\"#CC99FF\"}]}"
						)
				);
		
		try {
			String[] data = new String[] { 
					KeyEnum.ThreToViePaConf.toString()
					,FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_1100" + ".sql"
					,config.validityDate
					}; 
			
			String successed = CommandFactory.get().getCommand(KeyEnum.ThreToViePaConf.toString())[0].execute(data);
			assertTrue (successed.contains("SELECT SUCCESS"));
			
			try (FileInputStream fis = new FileInputStream(data[1]);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.equals(expected.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				dao.dropDb("mstUtV1_400");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath) + "test400_1100" + ".json"));
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test400_5000() {
		//　手入力移行ツール用のデータ作成
		
		VfMstMigratorRunner runner = null;
		String dmlPath = null;
		try {
			String fileName = "400/ApplicationConf_400.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			dmlPath = configNode.get("dmlPath").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configuration load fail...");
		}
		
		Map<String, String[]> testMap = new HashMap<String, String[]>();
		testMap.put("test", new String[] { "mst_signal", "test" });
		String testPath = FormatValidationUtil.setLastSeparator(dmlPath) + KeyEnum.ToolToSigAndSigAcq.toString() + ".sql";;
		List<String> expectedForward = Arrays.asList(new String[] { 
				"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_1_1','トレサビ','ワーク搬出入','EJECTION','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_1_1_1_1_1','facteye','MstMigrator','トレサビ','ワーク搬出入','EJECTION','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_2_1','トレサビ','ワーク搬出入','GET_BACK','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_1_2_1_1_1','facteye','MstMigrator','トレサビ','ワーク搬出入','GET_BACK','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_2_3_1','トレサビ','現品入力結果','INSPECTION','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_2_3_1_1_1','facteye','MstMigrator','トレサビ','現品入力結果','INSPECTION','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_2_4_1','トレサビ','現品入力結果','DISCARD','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_2_4_1_1_1','facteye','MstMigrator','トレサビ','現品入力結果','DISCARD','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_2_5_1','トレサビ','現品入力結果','NO_RECLOSING','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_2_5_1_1_1','facteye','MstMigrator','トレサビ','現品入力結果','NO_RECLOSING','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_2_6_1','トレサビ','現品入力結果','OP_CHECK','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_2_6_1_1_1','facteye','MstMigrator','トレサビ','現品入力結果','OP_CHECK','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_3_3_1','トレサビ','外観検査NG','INSPECTION','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_3_3_1_1_1','facteye','MstMigrator','トレサビ','外観検査NG','INSPECTION','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_3_4_1','トレサビ','外観検査NG','DISCARD','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_3_4_1_1_1','facteye','MstMigrator','トレサビ','外観検査NG','DISCARD','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_3_5_1','トレサビ','外観検査NG','NO_RECLOSING','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_3_5_1_1_1','facteye','MstMigrator','トレサビ','外観検査NG','NO_RECLOSING','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_3_6_1','トレサビ','外観検査NG','OP_CHECK','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_3_6_1_1_1','facteye','MstMigrator','トレサビ','外観検査NG','OP_CHECK','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_3_7_1','トレサビ','外観検査NG','STAY','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_3_7_1_1_1','facteye','MstMigrator','トレサビ','外観検査NG','STAY','none','none','','01','MstMigrator','"
				,"INSERT INTO mst_signal (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_4_8_1','トレサビ','外観検査OK','CHK_OK','Cassandra','','','','{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',86400,'01','MstMigrator','"
				,"INSERT INTO mst_signal_acquisition (signal_id,connector,connector_instance,signal_class,signal_category,signal_name,subject_instance,value_instance,conversion,test_code,created_by,created_at) VALUES ('signal_id_1_1_4_8_1_1_1','facteye','MstMigrator','トレサビ','外観検査OK','CHK_OK','none','none','','01','MstMigrator','"
		});
		
		List<String> expectedBack = Arrays.asList(new String[] {
				"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='ワーク搬出入',signal_name='EJECTION',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='ワーク搬出入',signal_name='EJECTION',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='ワーク搬出入',signal_name='GET_BACK',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='ワーク搬出入',signal_name='GET_BACK',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='INSPECTION',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='現品入力結果',signal_name='INSPECTION',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='DISCARD',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='現品入力結果',signal_name='DISCARD',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='NO_RECLOSING',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='現品入力結果',signal_name='NO_RECLOSING',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='OP_CHECK',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='現品入力結果',signal_name='OP_CHECK',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査NG',signal_name='INSPECTION',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査NG',signal_name='INSPECTION',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査NG',signal_name='DISCARD',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査NG',signal_name='DISCARD',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査NG',signal_name='NO_RECLOSING',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査NG',signal_name='NO_RECLOSING',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査NG',signal_name='OP_CHECK',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査NG',signal_name='OP_CHECK',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査NG',signal_name='STAY',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査NG',signal_name='STAY',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='外観検査OK',signal_name='CHK_OK',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='"
				,"') ON DUPLICATE KEY UPDATE connector='facteye',connector_instance='MstMigrator',signal_class='トレサビ',signal_category='外観検査OK',signal_name='CHK_OK',subject_instance='none',value_instance='none',conversion='',test_code='01',created_by='MstMigrator',created_at='"
		});
		
		try {
			FunctionEx.executeFunction(CreateInputDatMigratorFunction.class, testMap, testPath);
			
			try (FileInputStream fis = new FileInputStream(testPath);
				 InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				 BufferedReader br = new BufferedReader(isr);) {
				String line = null;
				int index = 0;
				while ((line = br.readLine()) != null) {
					assertTrue(line.contains(expectedForward.get(index)));
					assertTrue(line.contains(expectedBack.get(index++)));
				}
				
				assertTrue(index != 0);
			}
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				FileUtil.delete(testPath);
			} catch (Exception e) {
			}
		}
	}
}
