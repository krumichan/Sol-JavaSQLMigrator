package jp.co.cec.vfMstMigrator.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection utility
 */
public class CollectionUtil {
	private CollectionUtil() { }
	
	/**
	 * マップから特定名を含めているデータ削除
	 * @param <T>　要らない
	 * @param map　削除対象マップ
	 * @param key　特定名
	 * @return　削除されたキーリスト
	 */
	public static <T> List<String> remove(Map<String, T> map, String key) {
		List<String> keys = new ArrayList<String>();
		map.forEach((k, t) -> {
			if (k.contains(key)) {
				keys.add(k);
			}
		});
		keys.forEach(k -> {
			map.remove(k);
		});
		return keys;
	}
	
	/**
	 * マップをクローン
	 * @param <K>　要らない
	 * @param <V>　要らない
	 * @param original　クローン対象
	 * @return　クローンマップ
	 */
	public static <K, V> Map<K, V> clone(Map<K, V> original) {
		return new HashMap<>(original);
	}
}
