package jp.co.cec.vfMstMigrator.command.executor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import jp.co.cec.vfMstMigrator.command.manufacturing.ViewPartConfigurationManufacturing;
import jp.co.cec.vfMstMigrator.data.SqlData;
import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得し、画面設定ファイル(Json形式)を作成
 * V1：mst_threshold
 */
public class GetV1ViewPartConfCommand extends Command {

	/**
	 * mariaDBからV1マスタデータを取得し、画面設定ファイル(Json形式)を作成<br>
	 * data[0]　：　画面設定がboxの場合の保存する場所<br>
	 * data[1]　：　画面設定がboxではない場合の保存する場所<br>
	 * data[2]　：　有効期間(取得条件)
	 */
	@Override
	public String execute(String... data) {
		String result = null;
		
		String key = data[0];
		String filePath = data[1];
		String validityDate = data[2];
		
		String countSql = SqlData.get().map.get(key)[0];
		String sql = SqlData.get().map.get(key)[1];
		Date validity = FormatValidationUtil.stringToDate(validityDate);
		
		int errorLine = 0;
		int allLine = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		try {
			conn = DatabaseConnector.getConnection(DatabaseConnector.DB.V1);
			
			stmt = conn.prepareStatement(countSql);
			for (int index = 1; index <= FormatValidationUtil.specificStrCount(countSql, "?"); index++) {
				stmt.setObject(index, validity);
			}
			countRs = stmt.executeQuery();
			countRs.next();
			allLine = countRs.getInt(1);
			stmt.close();
			
			stmt = conn.prepareStatement(sql);
			for (int index = 1; index <= FormatValidationUtil.specificStrCount(sql, "?"); index++) {
				stmt.setObject(index, validity);
			}
			stmt.setFetchSize(Constants.FETCH_SIZE);
			rs = stmt.executeQuery();
			
			StringBuilder sb = new StringBuilder();
			StringBuilder boxColorSb = new StringBuilder();
			StringBuilder colorSb = new StringBuilder();
			boxColorSb.append("\"boxColor\":[");
			colorSb.append("\"color\":[");
			
			boolean boxColorFirstTime = false;
			boolean colorFirstTime = false;
			
			ViewPartConfigurationManufacturing mf = 
					new ViewPartConfigurationManufacturing();
			while (rs.next()) {
				errorLine++;
				Object[] objs = mf.get(rs);
				
				if (!(boolean)objs[0]) {
					if (!boxColorFirstTime) {
						boxColorFirstTime = true;
					} else {
						boxColorSb.append(",\n");
					}
					boxColorSb.append(objs[1]);
				} else {
					if (!colorFirstTime) {
						colorFirstTime = true;
						myLogger.info("リスト画面設定があるので、手修正が必要です。" + "\n" + "filePath:" + data[0]);
					} else {
						colorSb.append(",\n");
					}
					colorSb.append(objs[1]);
				}
			}
			boxColorSb.append("]");
			colorSb.append("]");
			sb.append("{" + boxColorSb + ",\n" + colorSb + "}");
			
			FileOutputStream fos = new FileOutputStream(filePath, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(sb.toString());
			writer.close();
			
			result = key + " -- SELECT SUCCESS:" + errorLine + "/" + allLine + "\n";
		} catch (Exception e) {
			result = "fail cause:" + e.getMessage() + "\n" + key + " -- SELECT FAIL:" + errorLine + "/" + allLine + "\n";
		} finally {
			DBUtils.close(countRs);
			DBUtils.close(rs);
			DBUtils.close(stmt);
		}
		
		return result;
	}
}
