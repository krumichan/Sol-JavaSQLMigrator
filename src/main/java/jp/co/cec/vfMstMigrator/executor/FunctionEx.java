package jp.co.cec.vfMstMigrator.executor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 任意のクラスのメソッドを実行 
 */
public class FunctionEx {

	/**
	 * タイプ変換マップ
	 */
	@SuppressWarnings("serial")
	private static Map<Class<?>, Class<?>> classMap = new HashMap<Class<?>, Class<?>>() {{
		put(HashMap.class, Map.class);
	}};
	
	/**
	 * メソッドを実行
	 * @param funcName　実行するメソッドを持っているクラス名
	 * @throws Exception　実行際、エラー発生
	 */
	@SuppressWarnings("unchecked")
	public static <T> T executeFunction(Class<?> functionClass, Object... params) throws Exception {
		try {
			//　クラスからインスタンス生成
			Object obj = functionClass.getConstructor().newInstance();
			
			//　引数のタイプ取得
			Class<?>[] classes = new Class<?>[params.length];
			for (int index = 0; index < params.length; index++) {
				Class<?> clazz = classMap.get(params[index].getClass());
				if (clazz == null) {
					clazz = params[index].getClass();
				}
				classes[index] = clazz;
			}
			
			//　メソッドを取得
			Method m = obj.getClass().getMethod("run", classes);
			
			//　メソッドを実行
			return (T)m.invoke(obj, params);
		} catch (Exception e) {
			throw e;
		}
	}
}
