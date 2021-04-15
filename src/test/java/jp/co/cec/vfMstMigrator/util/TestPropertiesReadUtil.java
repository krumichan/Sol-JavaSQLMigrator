package jp.co.cec.vfMstMigrator.util;

import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

public class TestPropertiesReadUtil {
	
	private JsonNode commonAppProp = null;
	
	private static final String RESOURCE_FOLDER = "MstMigrator";
	
	public JsonNode readCommonApp(String fileName) throws Exception {
		JsonNode ret = null;
        try {
        	String resourcePath = RESOURCE_FOLDER + "/" + fileName;
        	ret = TestJsonUtil.readTestJson(resourcePath);
        	commonAppProp = ret;
		} catch (Exception e) {
			throw new Exception();
		}
        
        return ret;
	}
	
	public String getCommonAppStr(String str){
		JsonNode target = commonAppProp.get(str);
		String res = null;
		if(Objects.nonNull(target)) {
			res =  FormatValidationUtil.trimStr(target.asText());
		}
		return res;
	}
}
