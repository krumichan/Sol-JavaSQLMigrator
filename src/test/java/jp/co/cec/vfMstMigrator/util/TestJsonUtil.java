package jp.co.cec.vfMstMigrator.util;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.utility.JsonUtil;

public class TestJsonUtil {

	public static JsonNode readTestJson(String resourcePath) throws Exception {
		String path = getResourceFileFullPath(resourcePath);
		JsonUtil jm = new JsonUtil();
		JsonNode rootNode = jm.jsonRead(path, JsonNode.class);
		return rootNode;
	}
	
	/**
	 * testフォルダのリソースファイルのフルパスを返す
	 */
	public static String getResourceFileFullPath(String resourcePath) throws Exception {
		String path = TestJsonUtil.class.getClassLoader().getResource(resourcePath).getPath();
		return path;
	}
}
