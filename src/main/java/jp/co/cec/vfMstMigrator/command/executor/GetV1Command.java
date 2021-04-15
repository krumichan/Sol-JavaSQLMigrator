package jp.co.cec.vfMstMigrator.command.executor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import jp.co.cec.vfMstMigrator.command.CommandFactory;
import jp.co.cec.vfMstMigrator.data.SqlData;
import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * V1DBからデータを取得し、Insert文を作成
 */
public class GetV1Command extends Command {

	/**
	 * V1DBからデータを取得し、Insert文を作成<br>
	 * data[0]：移行元及び移行先の組込<br>
	 * data[1]：ファイルパース(保存先及びファイル名)<br>
	 * data[2]：有効期間文字列<br>
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
		try (FileOutputStream fos = new FileOutputStream(filePath, false);
			 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			 BufferedWriter writer = new BufferedWriter(osw);){
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
			
			Command command = CommandFactory.get().getCommand(key)[1];
			while (rs.next()) {
				errorLine++;
				writer.write(command.execute(rs));
			}
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
