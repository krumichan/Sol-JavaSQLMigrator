package jp.co.cec.vfMstMigrator.main.function;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jp.co.cec.vfMstMigrator.data.constant.Constants;
import jp.co.cec.vfMstMigrator.data.map.MstMigratorHashMaps;
import jp.co.cec.vfMstMigrator.db.DBUtils;
import jp.co.cec.vfMstMigrator.db.DatabaseConnector;
import jp.co.cec.vfMstMigrator.template.AbstractFunction;
import jp.co.cec.vfMstMigrator.utility.CollectionUtil;

/**
 * ナンバリングマスタのデータをハッシュマップにセット
 */
public class BuildMstNumberingFunction extends AbstractFunction {

	/**
	 * コンストラクタ
	 */
	public BuildMstNumberingFunction() {
		startMsg = "ナンバリングマスタをハッシュマップにセットします。";
		endMsg = "ナンバリングマスタをハッシュマップにセットしました。";
	}
	
	/**
	 *  ナンバリングマスタのデータをハッシュマップにセット
	 */
	@Override
	protected void execute(Map<String, String[]> target, String errorFilePath) throws Exception {
		String sql = ""
				+ " SELECT "
				+ "		column_name "
				+ " ,	serial_number "
				+ " ,	value "
				+ "	FROM "
				+ " 	mst_numbering "
				+ "	ORDER BY "
				+ "		column_name "
				+ ",	CAST(serial_number AS SIGNED) ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DatabaseConnector.getConnection(DatabaseConnector.DB.V2).prepareStatement(sql);
			stmt.setFetchSize(Constants.FETCH_SIZE);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				MstMigratorHashMaps.buildNumberingMap(rs.getString("column_name"), rs.getString("value"), rs.getString("serial_number"));
			}
			/////////// test ///////////
			MstMigratorHashMaps.check();
			////////////////////////////
		} catch (SQLException e) {
			StringBuilder sb = new StringBuilder(Constants.MST_NUMBERING + "テーブルからデータ取得に失敗しました。" + "\n");
			sb.append(Constants.MST_NUMBERING + "テーブルに関する移行対象を除外します。理由：" + e.getMessage());
			myLogger.error(sb.toString());
			CollectionUtil.remove(target, "mst_signal");
			CollectionUtil.remove(target, "mst_signal_acquisition");
			CollectionUtil.remove(target, "mst_resource");
			CollectionUtil.remove(target, "mst_map");
		} finally {
			DBUtils.close(rs);
			DBUtils.close(stmt);
		}
	}
}
