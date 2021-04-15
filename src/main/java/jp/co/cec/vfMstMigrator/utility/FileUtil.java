package jp.co.cec.vfMstMigrator.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.common.io.Files;

/**
 * File utility
 */
public class FileUtil {
	//　オブジェクトの作成防止
	private FileUtil() { }
	
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
	
	/**
	 * ファイルを作成する
	 * @param filename 作成するファイル名
	 * @param filePath 作成先
	 * @param data 内容
	 * @param extension 拡張子
	 * @throws Exception 書き込み失敗
	 */
	public static void write(String filename, String filePath, String data, String extension, boolean append) throws Exception {
		extension = extension.contains(".") ? extension : "." + extension;
		File file = new File(filePath + filename + extension);
		FileWriter writer = new FileWriter(file, append);
		writer.write(data);
		writer.close();
	}
	
	/**
	 * 指定されたパスにでぃれくとりが存在しない場合ディレクトリを作成する
	 * @param path ディレクトリ作成先のパス
	 * @return ディレクトリが作成出来た場合もしくは既にディレクトリが存在する場合はtrueを返す
	 */
	public static boolean makeDir(String path) {
		boolean success = false;
		try {
			File dir = new File(path);
		    if(!dir.exists()){
		        dir.mkdirs();
		    }
		    success = true;
		}catch(Exception e) {
		}
		return success;
	}
	
	/**
	 * 指定されたパスにファイルを複製する
	 * @param source 複製元
	 * @param destination 複製先
	 * @throws Exception 複製に失敗
	 */
	public static void copy(String source, String destination) throws Exception {
		try {
			File from = new File(source);
			File to = new File(destination);
			
			Files.copy(from, to);
		} catch (Exception e) {
			throw new Exception("ファイルのコピーに失敗しました。 \n from:" + source + ", to:" + destination);
		}
	}
	
	/**
	 * 指定されたパスのファイルを削除する
	 * @param filePath　削除対象
	 */
	public static void delete(String filePath) {
		try {
			File tar = new File(filePath);
			tar.delete();
		} catch (Exception e) {
		}
	}
	
	/**
	 * 指定されたパスのファイルの存在を確認
	 * @param filePath　確認対象
	 * @return　確認結果
	 */
	public static boolean exists(String filePath) {
		return new File(filePath).exists();
	}
	
	/**
	 * 指定されたパスのファイルが絶対経路かを確認
	 * @param filePath　確認対象
	 * @return　確認結果
	 */
	public static boolean isAbsolute(String filePath) {
		return Paths.get(filePath).isAbsolute();
	}
	
	/**
	 * 指定されたパスの絶対経路に変換
	 * @param filePath　変換対象
	 * @return　変換結果
	 */
	public static String absolute(String filePath) {
		if (isAbsolute(filePath)) {
			return filePath;
		}
		Path cwdPath = Paths.get(System.getProperty("user.dir", ""));
		return cwdPath.resolve(filePath).normalize().toString();
	}
}
