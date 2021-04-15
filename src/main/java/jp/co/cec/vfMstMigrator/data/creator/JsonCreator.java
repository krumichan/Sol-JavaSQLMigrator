package jp.co.cec.vfMstMigrator.data.creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * (Singleton class)
 * Json形式Stringを作るクラス 
 */
public class JsonCreator {
	
	/**
	 * 自身
	 */
	private static JsonCreator jc;
	
	/**
	 * Json生成インスタンス
	 */
	private static ObjectNode on;
	
	/**
	 * コンストラクタ
	 */
	private JsonCreator() { }
	
	/**
	 *　自分取得<br>
	 *　取得時に新たなJson生成インスタンス取得
	 */
	public static JsonCreator get() {
		if (jc == null) {
			jc = new JsonCreator();
		}
		on = new ObjectMapper().createObjectNode();
		return jc;
	}
	
	/**
	 * 連鎖パターン<br>
	 * String形式データをプット
	 * @param key キー
	 * @param str バリュー
	 * @return　自身
	 */
	public JsonCreator put(String key, String str) {
		on.put(key, str);
		return jc;
	}
	
	/**
	 * 連鎖パターン<br>
	 * Float形式データをプット
	 * @param key キー
	 * @param str バリュー
	 * @return　自身
	 */
	public JsonCreator put(String key, Float f) {
		on.put(key, f);
		return jc;
	}
	
	/**
	 * 連鎖パターン<br>
	 * Integer形式データをプット
	 * @param key キー
	 * @param str バリュー
	 * @return　自身
	 */
	public JsonCreator put(String key, Integer i) {
		on.put(key, i);
		return jc;
	}
	
	@Override
	public String toString() {
		return on.toString();
	}
}
