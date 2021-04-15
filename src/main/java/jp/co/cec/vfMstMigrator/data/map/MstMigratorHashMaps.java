package jp.co.cec.vfMstMigrator.data.map;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.co.cec.vfMstMigrator.model.MstMigratorConfiguration;

public class MstMigratorHashMaps {
	private MstMigratorHashMaps() { }
	
	/**
	 * データ移行ツールの設定
	 */
	public static MstMigratorConfiguration config = null;
	
	/**
	 * ナンバリングマップ<br>
	 * 構成　⇒　<column_name, <[ value, serial_number ]>>
	 */
	private static ConcurrentMap<String, CopyOnWriteArrayList<String[]>> numberingMap =
			new ConcurrentHashMap<String, CopyOnWriteArrayList<String[]>>();
	
	/**
	 * ナンバリングマスタに挿入するマップ
	 */
	public static ConcurrentMap<String, CopyOnWriteArrayList<String[]>> newNumberingMap =
			new ConcurrentHashMap<String, CopyOnWriteArrayList<String[]>>();
	
	/**
	 * ナンバリングマップの初期化処理
	 * @param columnName　マップキー
	 * @param value　リストバリュー　(シリアルナンバーとセット)
	 * @param serialNumber　リストシリアルナンバー　(シリアルナンバーとセット)
	 */
	public static void buildNumberingMap(String columnName, String value, String serialNumber) {
		CopyOnWriteArrayList<String[]> numberingList = numberingMap.get(columnName);
		if (Objects.isNull(numberingList)) {
			numberingList = new CopyOnWriteArrayList<String[]>();
		}
		numberingList.add(new String[] { value, serialNumber });
		numberingMap.put(columnName, numberingList);
	}
	
	/**
	 * ハッシュマップの中に引数に該当するデータがあるかを確認し、あれば該当するデータのシリアルナンバーを返す
	 * @param columnName　マップキー(カラム名)
	 * @param value　リスト値(バリュー)
	 * @return　シリアルナンバー(無ければnull)
	 */
	public static String containsAndGetCurSeiralNumber(String columnName, String value) {
		String currentSerialNumber = null;
		final List<String[]> numberingList = numberingMap.get(columnName);
		if (!Objects.isNull(numberingList) && !numberingList.isEmpty()) {
			for (int index = 0; index < numberingList.size(); index++) {
				String[] numberingArray = numberingList.get(index);
				if (numberingArray[0].equals(value)) {
					currentSerialNumber = numberingArray[1];
					break;
				}
			}
		}
		return currentSerialNumber;
	}
	
	/**
	 * 新しいシリアルナンバーを取得
	 * @param columnName　対象カラム名
	 * @param value　対象値
	 * @return　新しいシリアルナンバー
	 */
	public static String getNewSerialNumber(String columnName, String value) {
		String newSerialNumber = null;
		
		//　既存のナンバリングマップ用のリスト
		CopyOnWriteArrayList<String[]> numberingList = numberingMap.get(columnName);
		
		//　新しいナンバリングマップ用のリスト
		CopyOnWriteArrayList<String[]> newNumberingList = newNumberingMap.get(columnName);
		if (Objects.isNull(newNumberingList) || newNumberingList.isEmpty()) {
			newNumberingList = new CopyOnWriteArrayList<String[]>();
		}
		
		if (Objects.isNull(numberingList) || numberingList.isEmpty()) {
			numberingList = new CopyOnWriteArrayList<String[]>();
			newSerialNumber = "1";
			
			//　既存のナンバリングマップ用のリストにデータ追加
			numberingList.add(new String[] { value, newSerialNumber });
			
			//　新しいナンバリングマップ用のリストにデータ追加
			newNumberingList.add(new String[] { value, newSerialNumber });
		} else {
			int lastIndex = numberingList.size() - 1;
			int lastSerialNumberToInt = Integer.parseInt(numberingList.get(lastIndex)[1]);
			newSerialNumber = String.valueOf(lastSerialNumberToInt + 1);
			
			//　既存のナンバリングマップにデータ追加
			numberingList.add(new String[] { value, newSerialNumber });
			
			//　新しいナンバリングマップにデータ追加
			newNumberingList.add(new String[] { value, newSerialNumber });
		}
		numberingMap.put(columnName, numberingList);
		newNumberingMap.put(columnName, newNumberingList);
		return newSerialNumber;
	}
	
	/////////////////////////////////////////////////////////
	//					TEST用 Area						   //
	/////////////////////////////////////////////////////////
	public static void refresh() {
		numberingMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<String[]>>();
		newNumberingMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<String[]>>();
	}
	
	public static void check() {
		if (numberingMap.isEmpty()) { }
	}
	/////////////////////////////////////////////////////////
}
