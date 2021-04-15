package jp.co.cec.vfMstMigrator.utility;

import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 設定ファイルの読み込みと読み込んだ設定ファイルの値を管理するクラス
 */
public class PropertyUtil {
	
	/**
	 * CommonAppricateionの設定を保持するメモリ
	 */
	private static JsonNode property = null;
	
	/**
	 * コンストラクタ
	 */
	private PropertyUtil() { }
	
	public static void readProperties(String[] args) throws Exception {
		property = readCommonApp(args[0]);
	}
	
	/**
	 * 取得した共通設定を返す
	 * @return 取得した共通設定
	 */
	public static JsonNode getProperty() {
		return property;
	}

	/**
	 * 指定されたキーの共通設定をトリムした値を返す
	 * @param str 共通設定のキー
	 * @return 取得結果
	 */
	public static String get(String key){
		return get(property, key);
	}
	
	/**
	 * 指定されたキーの共通設定をトリムした値を返す
	 * @param key 共通設定のキー
	 * @return 取得結果
	 */
	public static String get(JsonNode node, String key){
		JsonNode target = getNode(node, key);
		String res = null;
		if(Objects.nonNull(target)) {
			res = FormatValidationUtil.trimStr(target.asText());
		}
		return res;
	}
	
	/**
	 * JsonNodeからJsonNodeを取得
	 * @param node JsonNode
	 * @param key キー
	 * @return 取得結果のJsonNode
	 */
	public static JsonNode getNode(JsonNode node, String key) {
		if (node == null) {
			return null;
		}
		return node.get(key);
	}
	
	/**
	 * CommonAppricationConfを読み込む
	 * @param filePath
	 * @return 読み込んだCommonAppricationConf
	 */
	private static JsonNode readCommonApp(String filePath) {
		JsonUtil ju = new JsonUtil();
		JsonNode ret = null;
        try {
        	ret = ju.jsonRead(filePath);
		} catch (Exception e) {
			// ファイル読み込みに失敗
			e.printStackTrace();
			String msg = String.format("共通設定ファイルの読み込みに失敗しました。ファイル名:%s", filePath);
			JTextArea jta = new JTextArea(msg);
			jta.setEditable(false);
			jta.setLineWrap(true);
			jta.setOpaque(false);
			jta.setSize(480,10);
			jta.setPreferredSize(new Dimension(480, jta.getPreferredSize().height));
			System.out.println(msg);
			JOptionPane.showMessageDialog(null, jta);
			System.exit(1);
		}
        return ret;
	}
}