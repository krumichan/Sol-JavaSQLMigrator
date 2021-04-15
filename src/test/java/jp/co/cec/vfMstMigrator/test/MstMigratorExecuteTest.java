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

import jp.co.cec.vfMstMigrator.dao.TestMstMigratorDao;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.executor.FunctionEx;
import jp.co.cec.vfMstMigrator.function.TestMstMigratorFunction;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorRunner;
import jp.co.cec.vfMstMigrator.main.function.ConnectDatabaseFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateDmlFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateInputDatMigratorFunction;
import jp.co.cec.vfMstMigrator.main.function.CreateTableFunction;
import jp.co.cec.vfMstMigrator.main.function.InsertDmlFunction;
import jp.co.cec.vfMstMigrator.main.function.InsertMstNumberingFunction;
import jp.co.cec.vfMstMigrator.main.function.LockMstNumberingFunction;
import jp.co.cec.vfMstMigrator.main.function.UnlockMstNumberingFunction;
import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;
import jp.co.cec.vfMstMigrator.utility.FileUtil;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

public class MstMigratorExecuteTest {
	
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
	public void test700_100() {
		//　一括処理
		
		JsonNode conf = null;
		MstMigratorConfiguration config = null;
		VfMstMigratorRunner dts = null;
		TestMstMigratorDao dao = null;
		try {
			String fileName = "700/ApplicationConf_700.conf";
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
			dao.runScript("700/mstUtV1_700.sql");
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			FunctionEx.executeFunction(
					ConnectDatabaseFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			dts.setTargetKey();
			FunctionEx.executeFunction(
					CreateTableFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			FunctionEx.executeFunction(
					LockMstNumberingFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			FunctionEx.executeFunction(
					CreateDmlFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			FunctionEx.executeFunction(
					CreateInputDatMigratorFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath
					);
			FunctionEx.executeFunction(
					InsertMstNumberingFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			FunctionEx.executeFunction(
					UnlockMstNumberingFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
			FunctionEx.executeFunction(
					InsertDmlFunction.class
					,dts.mapDmlPath
					,dts.errorFileBackupPath);
		} catch (Exception e) {
			fail("Failed exceute...");
		} finally {
			try {
				FileUtil.delete(FileUtil.absolute(FormatValidationUtil.setLastSeparator(config.dmlPath)));
				dts.doEndEvent();
			} catch (Exception e) {
			}
		}
		
		try {
			dao = new TestMstMigratorDao(
					config.v2ServerAddress
					,config.v2UserName
					,config.v2Password
					,config.v2DBName);
			
			ResultSet rs = dao.selectAndGetResultSet("mst_exchange_language");
			rs.next();
			assertTrue(rs.getString("name_id").equals("cec"));
			assertTrue(rs.getString("language_type").equals("String"));
			assertTrue(rs.getString("name").equals("cec"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_map");
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_1"));
			assertTrue(rs.getString("spec").equals("[{ \"backGround\": { \"rect\":[],\"image\":null},\"area\":{ \"polygon\":{\"outerLoop\":[-74.0,-207.0,-14.0,-207.0,-14.0,-160.0,-74.0,-147.0],\"innerLoops\":[]}}}]"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_2"));
			assertTrue(rs.getString("spec").equals("[{ \"backGround\": { \"rect\":[-600.0,-400.0,600.0,400.0],\"image\":\"HEAD.png\"},\"area\":{ \"polygon\":{\"outerLoop\":[329.0,-75.0,549.0,-75.0,549.0,19.0,329.0,145.0],\"innerLoops\":[]}}}]"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_password");
			rs.next();
			assertTrue(rs.getString("password_id").equals("junit"));
			assertTrue(rs.getString("user_id").equals("junit"));
			assertTrue(rs.getString("user_name").equals("テスト利用者"));
			assertTrue(rs.getString("password").equals("junit"));
			assertTrue(rs.getString("permission").equals("user"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_resource");
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_1"));
			assertTrue(rs.getString("object_category").equals("設備"));
			assertTrue(rs.getString("resource_type").equals("work_place"));
			assertTrue(rs.getString("resource_name").equals("電力PLC-02"));
			assertTrue(rs.getString("resource_name_key").equals("電力PLC-02"));
			assertTrue(rs.getString("parent_id").equals("res_id_1_2"));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			
			rs.next();
			assertTrue(rs.getString("resource_id").equals("res_id_1_2"));
			assertTrue(rs.getString("object_category").equals("ローダー／コンベア"));
			assertTrue(rs.getString("resource_type").equals("facility"));
			assertTrue(rs.getString("resource_name").equals("電力PLC-01"));
			assertTrue(rs.getString("resource_name_key").equals("電力PLC-01"));
			assertTrue(rs.getString("parent_id").equals("res_id_1_3"));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")).equals("2019-12-31 00:00:00"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")).equals("2999-12-31 00:00:00"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.getString("updated_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("updated_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_signal");
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
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_2_2_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("ー"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_2_5_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("NO_RECLOSING"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_3_3_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("ワーク搬出入"));
			assertTrue(rs.getString("signal_name").equals("EJECTION"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_3_4_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("ワーク搬出入"));
			assertTrue(rs.getString("signal_name").equals("GET_BACK"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_4_6_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("外観検査NG"));
			assertTrue(rs.getString("signal_name").equals("STAY"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("log_id").equals("log_id_1_2_5_7_1"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("外観検査OK"));
			assertTrue(rs.getString("signal_name").equals("CHK_OK"));
			assertTrue(rs.getString("data_type").equals("Cassandra"));
			assertTrue(rs.getString("value_type").equals(""));
			assertTrue(rs.getString("time_division").equals(""));
			assertTrue(rs.getString("value_key_format").equals(""));
			assertTrue(rs.getString("retention_type").equals("{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}"));
			assertTrue(rs.getLong("retention_period") == 86400L);
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_signal_acquisition");
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_2_2_1_1_1"));
			assertTrue(rs.getString("connector").equals("VF"));
			assertTrue(rs.getString("connector_instance").equals("FERSV5"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("ー"));
			assertTrue(rs.getString("subject_instance").equals("EM29_STR"));
			assertTrue(rs.getString("value_instance").equals("EM29_STR"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_2_5_2_2_2"));
			assertTrue(rs.getString("connector").equals("facteye"));
			assertTrue(rs.getString("connector_instance").equals("MstMigrator"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("現品入力結果"));
			assertTrue(rs.getString("signal_name").equals("NO_RECLOSING"));
			assertTrue(rs.getString("subject_instance").equals("none"));
			assertTrue(rs.getString("value_instance").equals("none"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_3_3_2_2_2"));
			assertTrue(rs.getString("connector").equals("facteye"));
			assertTrue(rs.getString("connector_instance").equals("MstMigrator"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("ワーク搬出入"));
			assertTrue(rs.getString("signal_name").equals("EJECTION"));
			assertTrue(rs.getString("subject_instance").equals("none"));
			assertTrue(rs.getString("value_instance").equals("none"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_3_4_2_2_2"));
			assertTrue(rs.getString("connector").equals("facteye"));
			assertTrue(rs.getString("connector_instance").equals("MstMigrator"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("ワーク搬出入"));
			assertTrue(rs.getString("signal_name").equals("GET_BACK"));
			assertTrue(rs.getString("subject_instance").equals("none"));
			assertTrue(rs.getString("value_instance").equals("none"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_4_6_2_2_2"));
			assertTrue(rs.getString("connector").equals("facteye"));
			assertTrue(rs.getString("connector_instance").equals("MstMigrator"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("外観検査NG"));
			assertTrue(rs.getString("signal_name").equals("STAY"));
			assertTrue(rs.getString("subject_instance").equals("none"));
			assertTrue(rs.getString("value_instance").equals("none"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			
			rs.next();
			assertTrue(rs.getString("signal_id").equals("signal_id_1_2_5_7_2_2_2"));
			assertTrue(rs.getString("connector").equals("facteye"));
			assertTrue(rs.getString("connector_instance").equals("MstMigrator"));
			assertTrue(rs.getString("signal_class").equals("トレサビ"));
			assertTrue(rs.getString("signal_category").equals("外観検査OK"));
			assertTrue(rs.getString("signal_name").equals("CHK_OK"));
			assertTrue(rs.getString("subject_instance").equals("none"));
			assertTrue(rs.getString("value_instance").equals("none"));
			assertTrue(rs.getString("conversion").equals(""));
			assertTrue(rs.getString("test_code").equals("01"));
			assertTrue(rs.getString("created_by").equals("MstMigrator"));
			assertTrue(rs.next() == false);
			
			
			rs = dao.selectAndGetResultSet("mst_value_conversion");
			rs.next();
			assertTrue(rs.getString("signal_class").equals("アラーム"));
			assertTrue(rs.getString("signal_category").equals("アラーム情報"));
			assertTrue(rs.getString("signal_name").equals("QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ"));
			assertTrue(rs.getString("resource_name").equals("BH-1397"));
			assertTrue(rs.getString("value").equals("0000"));
			assertTrue(rs.getString("conversion_value").equals("QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ"));
			assertTrue(rs.getString("conversion_type").equals("Replacement"));
			assertTrue(rs.getString("created_by").equals("åå¥"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			
			rs.next();
			assertTrue(rs.getString("signal_class").equals("稼働状態"));
			assertTrue(rs.getString("signal_category").equals("稼働状態コード"));
			assertTrue(rs.getString("signal_name").equals("0005_aos"));
			assertTrue(rs.getString("resource_name").equals(""));
			assertTrue(rs.getString("value").equals("0005"));
			assertTrue(rs.getString("conversion_value").equals("操作電源入"));
			assertTrue(rs.getString("conversion_type").equals("Replacement"));
			assertTrue(rs.getString("created_by").equals("junit"));
			assertTrue(FormatValidationUtil.dateToString(rs.getDate("created_at")).equals("2020-01-08 00:00:00"));
			assertTrue(rs.next() == false);
		} catch (Exception e) {
			fail("Get v2 table data failed...");
		} finally {
			try {
				dao.dropDb("mstUtV1_700");
				dao.dropDb("mstUtV2");
				dao.close();
			} catch (Exception e) {
			}
		}
	}
}
