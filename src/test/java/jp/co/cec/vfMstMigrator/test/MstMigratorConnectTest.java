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

import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.function.TestMstMigratorFunction;
import jp.co.cec.vfMstMigrator.main.VfMstMigratorRunner;
import jp.co.cec.vfMstMigrator.main.function.ConnectDatabaseFunction;

public class MstMigratorConnectTest {

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
	public void test20_0050() {
		//　全てが正常
		
		VfMstMigratorRunner runner = null;
		try {
			String fileName = "ApplicationConf_20_0050.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			fail("Failed connect...");
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_0100() {
		//　v1ServerAddressが不正
		
		VfMstMigratorRunner runner = null;
		String v1DBName = null;
		try {
			String fileName = "ApplicationConf_20_0100.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v1DBName = configNode.get("v1DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v1DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_0200() {
		//　v1DBNameが不正
		
		VfMstMigratorRunner runner = null;
		String v1DBName = null;
		try {
			String fileName = "ApplicationConf_20_0200.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v1DBName = configNode.get("v1DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v1DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_0300() {
		//　v1UserNameが不正
		
		VfMstMigratorRunner runner = null;
		String v1DBName = null;
		try {
			String fileName = "ApplicationConf_20_0300.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v1DBName = configNode.get("v1DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v1DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_0400() {
		//　v1Passwordが不正
		
		VfMstMigratorRunner runner = null;
		String v1DBName = null;
		try {
			String fileName = "ApplicationConf_20_0400.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v1DBName = configNode.get("v1DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v1DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_1000() {
		//　v2ServerAddressが不正
		
		VfMstMigratorRunner runner = null;
		String v2DBName = null;
		try {
			String fileName = "ApplicationConf_20_1000.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v2DBName = configNode.get("v2DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v2DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_1500() {
		//　v2DBNameが不正
		//　ddlのファイルの生成するDB名と設定のDB名が違う場合
		
		VfMstMigratorRunner runner = null;
		String v2DBName = null;
		try {
			String fileName = "ApplicationConf_20_1500.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v2DBName = configNode.get("v2DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v2DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void test20_2000() {
		//　v2UserNameが不正
		
		VfMstMigratorRunner runner = null;
		String v2DBName = null;
		try {
			String fileName = "ApplicationConf_20_2000.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v2DBName = configNode.get("v2DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v2DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void test20_3000() {
		//　v1Passwordが不正
		
		VfMstMigratorRunner runner = null;
		String v2DBName = null;
		try {
			String fileName = "ApplicationConf_20_3000.conf";
			JsonNode configNode = TestMstMigratorFunction.getConfigNode(fileName);
			v2DBName = configNode.get("v2DBName").asText();
			runner = new VfMstMigratorRunner();
			runner.init();
			runner.testInit(configNode);
		} catch (Exception e) {
			fail("Configulation load error.");
		}
		
		try {
			new ConnectDatabaseFunction();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(v2DBName + "dbの接続に失敗しました。"));
		} finally {
			try {
				DatabaseConnector.disconnect();
			} catch (Exception e) {
			}
		}
	}
}
