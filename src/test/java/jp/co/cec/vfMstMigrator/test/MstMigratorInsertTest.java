package jp.co.cec.vfMstMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.command.executor.InsertV2Command;
import jp.co.cec.vfMstMigrator.dao.TestMstMigratorDao;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.executor.FunctionEx;
import jp.co.cec.vfMstMigrator.function.TestMstMigratorFunction;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorRunner;
import jp.co.cec.vfMstMigrator.main.function.ConnectDatabaseFunction;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

public class MstMigratorInsertTest {

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
	public void test600_100() {
		// TO : mst_exchange_language
		// FROM : mst_exchange_language 
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_100.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_exchange_language");
			rs.next();
			assertTrue(rs.getString("name_id").equals("cec"));
			assertTrue(rs.getString("language_type").equals("String"));
			assertTrue(rs.getString("name").equals("after"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_200() {
		// TO : mst_place_setting
		// FROM : mst_map 
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_200.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_map");
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_1"));
			assertTrue(rs.getString("spec").equals("[{ \"backGround\": { \"rect\":[],\"image\":null},\"area\":{ \"polygon\":{\"outerLoop\":[-20.0,-40.0,30.0,-40.0,30.0,20.0,-20.0,20.0],\"innerLoops\":[]}}}]"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_300() {
		// TO : mst_password
		// FROM : mst_human
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_300.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_password");
			rs.next();
			assertTrue(rs.getString("password_id").equals("junit"));
			assertTrue(rs.getString("user_id").equals("junit"));
			assertTrue(rs.getString("user_name").equals("junit_name"));
			assertTrue(rs.getString("password").equals("junit_pass"));
			assertTrue(rs.getString("permission").equals("tester"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_400() {
		// TO : mst_resource
		// FROM : mst_facility
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_400.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_resource");
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_1"));
			assertTrue(rs.getString("object_category").equals("junit"));
			assertTrue(rs.getString("resource_type").equals("facility"));
			assertTrue(rs.getString("resource_name").equals("BH-1322"));
			assertTrue(rs.getString("resource_name_key").equals("BH-1322"));
			assertTrue(rs.getString("parent_id").equals("junit1000"));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_500() {
		// TO : mst_resource
		// FROM : mst_place_setting
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_500.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_resource");
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_1"));
			assertTrue(rs.getString("object_category").equals("junit"));
			assertTrue(rs.getString("resource_type").equals("facility"));
			assertTrue(rs.getString("resource_name").equals("facility_key"));
			assertTrue(rs.getString("resource_name_key").equals("facility_key"));
			assertTrue(rs.getString("parent_id").equals("junit1000"));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_600() {
		// TO : mst_signal_acquisition
		// FROM : mst_data_item
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_600.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_signal_acquisition");
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_1_1_1_1_1_1"));
			assertTrue(rs.getString("connector").equals("junit"));
			assertTrue(rs.getString("connector_instance").equals("FERSV5"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("ー"));
			assertTrue(rs.getString("subject_instance").equals("EM29_STR"));
			assertTrue(rs.getString("value_instance").equals("EM29_STR"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator")); 
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-07 17:28:40"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_700() {
		// TO : mst_signal
		// FROM : mst_data_item
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_700.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_signal");
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_1_1_1_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("ー"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-07 17:29:28"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_800() {
		// TO : mst_value_conversion
		// FROM : mst_facility_condition
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_800.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_value_conversion");
			rs.next();
			assertTrue(rs.getString("signal_class").equals("アラーム"));
			assertTrue(rs.getString("signal_category").equals("アラーム情報"));
			assertTrue(rs.getString("signal_name").equals("QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ"));
			assertTrue(rs.getString("resource_name").equals("BH-1397"));
			assertTrue(rs.getString("value").equals("0000"));
			assertTrue(rs.getString("conversion_value").equals("QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ"));
			assertTrue(rs.getString("conversion_type").equals("Replacement"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_850() {
		// TO : mst_signal
		// FROM : mst_facility_condition
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_850.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_signal");
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_1_1_1_1"));
			assertTrue(rs.getString("signal_class").equals("アラーム"));
			assertTrue(rs.getString("signal_category").equals("アラーム情報"));
			assertTrue(rs.getString("signal_name").equals("QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-07 17:30:30"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test600_900() {
		// TO : mst_value_conversion
		// FROM : mst_threshold
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "600/ApplicationConf_600.conf";
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
			dao.runScript("600/mstUtV1_600.sql");
			dao.runScript("600/mstUtV2.sql");
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Configulation load error.");
		}

		try {
			InsertV2Command command = new InsertV2Command();
			String result = command.execute(new String[] {
					TestMstMigratorFunction.getResourceFileFullPath("MstMigrator/600/600_900.sql")
			});
			assertTrue(result == null);
			
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_value_conversion");
			rs.next();
			assertTrue(rs.getString("signal_class").equals("稼働状態"));
			assertTrue(rs.getString("signal_category").equals("稼働状態コード"));
			assertTrue(rs.getString("signal_name").equals("0005_aos"));
			assertTrue(rs.getString("resource_name").equals(""));
			assertTrue(rs.getString("value").equals("0005"));
			assertTrue(rs.getString("conversion_value").equals("junit_key"));
			assertTrue(rs.getString("conversion_type").equals("Replacement"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
		} catch (Exception e) {
			fail("Failed insert...");
		} finally {
			try {
				dao.dropDb("mstUtV1_600");
				dao.dropDb("mstUtV2");
				dao.close();
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
}
