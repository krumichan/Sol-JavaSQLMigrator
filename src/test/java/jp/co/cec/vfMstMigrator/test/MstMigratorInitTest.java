package jp.co.cec.vfMstMigrator.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.function.TestMstMigratorFunction;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorInitialization;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorRunner;
import jp.co.cec.vfMstMigrator.model.ApplicationConfigurationKeyModel;
import jp.co.cec.vfMstMigrator.util.TestPropertiesReadUtil;

public class MstMigratorInitTest {
	
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
	public void test10_0100() {
		//　全ての項目が正常
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0100.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
		} catch (Exception e) {
			fail("Can not run at setup.");
		}
	}
	
	@Test
	public void test10_0210() {
		//　v1ServerAddress項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0210.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0220() {
		//　v1ServerAddress項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0220.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0310() {
		// v1DBName項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0310.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0320() {
		// v1DBName項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0320.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0410() {
		//　v1UserName項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0410.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0420() {
		//　v1UserName項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0420.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0510() {
		//　v1Password項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0510.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0520() {
		//　v1Password項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0520.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V1_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0610() {
		//　v2ServerAddress項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0610.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0620() {
		//　v2ServerAddress項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0620.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_SERVER_ADDRESS_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0710() {
		// v2DBName項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0710.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0720() {
		// v2DBName項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0720.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_DATABASE_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0810() {
		//　v2UserName項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0810.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0820() {
		//　v2UserName項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0820.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_USER_NAME_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0910() {
		//　v2Password項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0910.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_0920() {
		//　v2Password項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_0920.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_V2_PASSWORD_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1010() {
		// characterSet項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1010.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_CHARACTER_SET_NULL));
		}
	}
	
	@Test
	public void test10_1210() {
		//　validityDate項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1210.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_VALIDITY_DATE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1220() {
		//　validityDate項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1220.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_VALIDITY_DATE_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1310() {
		//　DDLPath項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1310.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_DDL_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1320() {
		//　DDLPath項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1320.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_DDL_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1410() {
		//　resultLogPath項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1410.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_RESULT_LOG_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1420() {
		//　resultLogPath項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1420.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_RESULT_LOG_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1510() {
		//　errorLogPath項目がない
		
		TestPropertiesReadUtil tpr = null;
		try {
			String filename = "ApplicationConf_10_1510.conf";
			tpr = new TestPropertiesReadUtil();
			tpr.readCommonApp(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			String path = tpr.getCommonAppStr(ApplicationConfigurationKeyModel.ERROR_LOG_PATH);
			assertTrue(path == null);
		} catch (Exception e) {
			fail("Can not get result log path.");
		}
	}
	
	@Test
	public void test10_1520() {
		//　errorLogPath項目が空
		
		TestPropertiesReadUtil tpr = null;
		try {
			String filename = "ApplicationConf_10_1520.conf";
			tpr = new TestPropertiesReadUtil();
			tpr.readCommonApp(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			String path = tpr.getCommonAppStr(ApplicationConfigurationKeyModel.ERROR_LOG_PATH);
			assertTrue(path.isEmpty());
		} catch (Exception e) {
			fail("Can not get result log path.");
		}
	}
	
	@Test
	public void test10_1610() {
		//　backupPath項目がない
		
		TestPropertiesReadUtil tpr = null;
		try {
			String filename = "ApplicationConf_10_1610.conf";
			tpr = new TestPropertiesReadUtil();
			tpr.readCommonApp(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			String path = tpr.getCommonAppStr(ApplicationConfigurationKeyModel.BACKUP_PATH);
			assertTrue(path == null);
		} catch (Exception e) {
			fail("Can not get result log path.");
		}
	}
	
	@Test
	public void test10_1620() {
		//　backupPath項目が空
		
		TestPropertiesReadUtil tpr = null;
		try {
			String filename = "ApplicationConf_10_1620.conf";
			tpr = new TestPropertiesReadUtil();
			tpr.readCommonApp(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			String path = tpr.getCommonAppStr(ApplicationConfigurationKeyModel.BACKUP_PATH);
			assertTrue(path.isEmpty());
		} catch (Exception e) {
			fail("Can not get result log path.");
		}
	}
	
	@Test
	public void test10_1710() {
		//　dmlPath項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1710.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_DML_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1720() {
		//　dmlPath項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1720.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_DML_PATH_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1810() {
		// idmMaster項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1810.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_IDM_MASTER_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1811() {
		// idmMaster項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1811.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_IDM_MASTER_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1820() {
		// idmMaster/major項目がnull
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1820.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_MAJOR_NULL));
		}
	}
	
	@Test
	public void test10_1830() {
		// idmMaster/medium項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1830.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_MEDIUM_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1831() {
		// idmMaster/medium項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1831.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_MEDIUM_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1840() {
		// idmMaster/medium/value項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1840.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_MEDIUM_VALUE_NULL));
		}
	}
	
	@Test
	public void test10_1850() {
		// idmMaster/medium/small項目がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1850.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_SMALL_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1851() {
		// idmMaster/medium/small項目が空
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1851.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_SMALL_NULLOREMPTY));
		}
	}
	
	@Test
	public void test10_1860() {
		// idmMaster/medium/small/要素の中身がない
		
		JsonNode configNode = null;
		try {
			String filename = "ApplicationConf_10_1860.conf";
			configNode = TestMstMigratorFunction.getConfigNode(filename);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new VfMstMigratorRunner().testInit(configNode);
			fail("The program should raise an exception.");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(VfMstMigratorInitialization.ERROR_STATE_SMALL_VALUE_NULL));
		}
	}
}