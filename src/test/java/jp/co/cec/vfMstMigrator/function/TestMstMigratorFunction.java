package jp.co.cec.vfMstMigrator.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;

import jp.co.cec.vfMstMigrator.util.TestJsonUtil;

public class TestMstMigratorFunction {
	
	private static final String RESOURCE_FOLDER = "MstMigrator";
	
	public static JsonNode getConfigNode(final String fileName) throws Exception {
		String resourcePath = RESOURCE_FOLDER + "/" + fileName;
		JsonNode configNode = TestJsonUtil.readTestJson(resourcePath);
		return configNode;
	}
	
	/**
	 * testフォルダのリソースファイルのフルパスを返す
	 */
	public static String getResourceFileFullPath(String resourcePath) throws Exception {
		String path = TestJsonUtil.class.getClassLoader().getResource(resourcePath).getPath();
		return path;
	}
	
	public static boolean initSqlFile(final String from, final String to) throws Exception {
		for (final File fileEntry : new File(to).listFiles()) {
			if (!fileEntry.delete()) {
				throw new Exception();
			}
		}
		
		for (final File fileEntry : new File(from).listFiles()) {
			try {
				Files.copy(fileEntry, new File(to + "/" + fileEntry.getName()));
			} catch (Exception e) {
				throw new Exception();
			}
		}
		return true;
	}
	
	public static String getResultLog(final String testNumber) throws Exception {
		String resultPath = new File(getFullPath(testNumber + "/" + "resultLog.txt")).getAbsolutePath();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(resultPath))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		}
		return sb.toString();
	}
	
	public static String getDdlPath(String testNumber) throws Exception {
		return new File(getFullPath(testNumber + "/ddl/")).getAbsolutePath();
	}
	
	public static String getDmlPath(String testNumber) throws Exception {
		return new File(getFullPath(testNumber + "/dml/")).getAbsolutePath();
	}
	
	public static String getInitDmlPath(String testNumber) throws Exception {
		return new File(getFullPath(testNumber + "/dml_Init/")).getAbsolutePath();
	}
	
	private static String getFullPath(String str) {
		return "src/test/resources/DatabaseTransition/" + str;
	}
}
