package jp.co.cec.vfMstMigrator.data.constant;

import java.util.HashMap;
import java.util.Map;

import jp.co.cec.vfMstMigrator.utility.DialogUtil;

/**
 * 定数を置くクラス。
 */
public class Constants {

	/**
	 * アプリケーション名
	 */
	public static final String APPLICATION_NAME = "vfMstMigrator";
	
	/**
	 * ログファイル名
	 */
	public static final String LOG_FILE_NAME = "VFMstMigrator";
	
	/**
	 * Dialog Utility {@link DialogUtil} でダイアログ表示もするかどうか<br>
	 * false ならばコンソール出力のみ。
	 */
	public static final boolean IS_SHOW_MESSAGE_DIALOG = false;
	
	/**
	 * V2データベースに挿入する際のバッチサイズ
	 */
	public static final int BATCH_SIZE = 5000;
	
	/**
	 * 採番マスタ名
	 */
	public static final String MST_NUMBERING = "mst_numbering";
	
	/**
	 * V2テーブル生成ファイルの拡張子
	 */
	public static final String CREATE_EXTENSION = ".ddl";

	/**
	 * result set fetch size
	 */
	public static final int FETCH_SIZE = 1000;
	
	/**
	 * queryの実行時、最大待ち時間
	 */
	public static final int QUERY_WAIT_TIME_OUT = 60;
	
	/**
	 * mariaDBのタイプのデフォルト値
	 */
	@SuppressWarnings("serial")
	public static Map<String, String> DB_TYPE_DEFAULT_SIZE_MAP = new HashMap<String, String>() {{
	    put("tinyint", "4");
	    put("smallint", "6");
	    put("mediumint", "9");
	    put("int", "11");
	    put("bigint", "20");
	    put("bit", "1");
	    put("decimal", "10,1");
	    put("char", "4");
	    put("year", "4");
	    put("binary", "1");
	}};
}
