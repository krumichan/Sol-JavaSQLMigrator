package jp.co.cec.vfMstMigrator.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TestFileUtil {
	/**
	 * ファイルを読み込み、リストに変換
	 * @param filePath 対象のファイルパス(パス＋ファイル名)
	 * @return 読み込んだデータリスト
	 * @throws Exception 読み込み失敗
	 */
	public static ArrayList<String> readToStringArray(String filePath) throws Exception {
		ArrayList<String> array = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				array.add(line);
			}
		}
		return array;
	}
}
