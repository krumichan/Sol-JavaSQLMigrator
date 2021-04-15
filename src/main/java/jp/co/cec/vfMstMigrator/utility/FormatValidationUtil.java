package jp.co.cec.vfMstMigrator.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 共通で使用するユーティリティクラス
 */
public class FormatValidationUtil {

	/**
	 * 変換先の時刻のフォーマット
	 */
	private static DateTimeFormatter dtfAfter = null;
	
	/**
	 * ユーティリティの実行準備
	 */
	static{
		dtfAfter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
	}
	
	/**
	 * 半角・全角の空白とタブのトリムを行う
	 * @param target トリムを行う文字列
	 * @return トリムの結果
	 */
	public static String trimStr(String target){
	    if (target == null || target.length() == 0)
	        return target;
	    int st = 0;
	    int len = target.length();
	    char[] val = target.toCharArray();
	    while ((st < len) && ((val[st] <= ' ') || (val[st] == '　'))) {
	        st++;
	    }
	    while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == '　'))) {
	        len--;
	    }
	    return ((st > 0) || (len < target.length())) ? target.substring(st, len) : target;
	}
	
	/**
	 * LocalDateTime形式をストリングに変換
	 * @param ldt 対象LocalDateTime
	 * @param dir 利用先がディレクトリかどうかをチェック
	 * @return ストリングデート
	 */
	public static String dateToString(LocalDateTime ldt, boolean dir){
		String  str;
		if(ldt == null) {
			str = null;
		} else {
			if (dir) {
				str = ldt.getYear() 
					+ "_" + ldt.getMonthValue() 
					+ "_" + ldt.getDayOfMonth() 
					+ " " + ldt.getHour() 
					+ "_" + ldt.getMinute() 
					+ "_" + ldt.getSecond();
			} else {
				str = ldt.getYear() 
					+ "-" + ldt.getMonthValue() 
					+ "-" + ldt.getDayOfMonth() 
					+ " " + ldt.getHour() 
					+ ":" + ldt.getMinute() 
					+ ":" + ldt.getSecond();
			}
		}
		
		return str;
	}
	
	/**
	 * Date形式をストリングに変換
	 * @param date 対象Date
	 * @return ストリングDate
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		try {
			return convertDateFormat(dateFormat.format(date));
		} catch (Exception e) {
		}
		return date.toString();
	}
	
	/**
	 * String形式をDateに変換
	 * @param date 対象String
	 * @return デートString
	 */
	public static Date stringToDate(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return dateFormat.parse(date);
		} catch (Exception e) {
		}
		
		return null; 
	}
	
	/**
	 * 引数のパスの後ろにセパレートを挿入
	 * @param path 対象パス
	 * @return セパレートが入ったパス
	 */
	public static String setLastSeparator(String path) {
		if (path == null || path.isEmpty()) {
			return path;
		}
		try {
			String separator = null;
			if (path.contains("/")) {
				separator = "/";
			} else if (path.contains("\\")) {
				separator = "\\";
			} else {
				separator = "/";
			}
			if (!path.endsWith(separator)) {
				return path + separator;
			}
		} catch (Exception e) {
			return path;
		}
		return path;
	}
	
	/**
	 * LocalDateTimeを日時にして返す
	 * @param ldt LocalDateTime型の日時
	 * @return 文字列の日時
	 */
	public static String LocalDateTimeToDttmMilli(LocalDateTime ldt){
		return ldt.format(dtfAfter);
	}
	
	/**
	 *　日時文字列をISO8601からVFSignalスタイルに変換するメソッド
	 * @param date ISO8601スタイルの日時文字列
	 * @return　VFSignalスタイルの日時文字列
	 * @throws Exception　引数の日時の形式が不正
	 */
	public static String convertDateFormat(String date) throws Exception {
		String strReturnDate = null;
		TemporalAccessor tpa = null;
		LocalDateTime ldt = null;
		try {
			DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
			tpa = fmt.parseBest(date, ZonedDateTime::from, LocalDateTime::from);
			
			if (tpa instanceof ZonedDateTime) {
				ZonedDateTime zoneDefault = ((ZonedDateTime)tpa).withZoneSameInstant(ZoneId.systemDefault());
				ldt = zoneDefault.toLocalDateTime();
			} else if (tpa instanceof LocalDateTime) {
				ldt = ((LocalDateTime)tpa);
			}
			
			strReturnDate = FormatValidationUtil.LocalDateTimeToDttmMilli(ldt);
		} catch (Exception e) {
			throw new Exception("日時の形式が不正です");
		}
		
		return strReturnDate;
	}
	
	/**
	 * 対象文字列から特定な文字列の頻度を取得
	 * @param tar　対象文字列
	 * @param spec　特定な文字列
	 * @return　頻度数
	 */
	public static int specificStrCount(String tar, String spec) {
		int cnt = 0;
		Pattern p = null;
		try {
			p = Pattern.compile(spec);
		} catch (Exception e) {
			p = Pattern.compile("\\" + spec);
		}
		Matcher m = p.matcher(tar);
		while (m.find()) {
			cnt++;
		}
		return cnt;
	}
}