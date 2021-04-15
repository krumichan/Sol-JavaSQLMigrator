package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.JsonCreator;
import jp.co.cec.vfMstMigrator.template.Command;

/**
 * mariaDBからV1マスタデータを取得したデータを画面設定用のJson形式に変換
 * V1：mst_threshold
 */
public class ViewPartConfigurationManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータを画面設定用のJson形式に変換 
	 * @return JSON形式データ
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	public Object[] get(ResultSet rs) throws Exception {
		String conditions = rs.getString("conditions");
		if (rs.wasNull()) {
			conditions = null;
		}
		boolean box = false;
		try {
			box = (conditions != null);
			Integer.parseInt(conditions, 16);
		} catch (Exception e) {
			box = false;
		}
		
		if (!box) {
			Integer priority = rs.getInt("priority");
			Float maxValue = rs.getFloat("limit_max");
			if (rs.wasNull()) {
				maxValue = null;
			}
			Float minValue = rs.getFloat("limit_min");
			if (rs.wasNull()) {
				minValue = null;
			}
			String color = rs.getString("color");
			return new Object[] { false, getBoxColor(priority, maxValue, minValue, conditions, color) };
		} else {
			Integer priority = rs.getInt("priority");
			String column = null;
			String color = rs.getString("color");
			return new Object[] { true, getColor(priority, column, conditions, color) };
		}
	}
	
	private String getBoxColor(Integer priority, Float maxValue, Float minValue, String conditions, String color) {
		return JsonCreator
				.get()
				.put("priority", priority)
				.put("maxValue", maxValue)
				.put("minValue", minValue)
				.put("targetString", conditions)
				.put("colorCode", color)
				.toString();
	}
	
	private String getColor(Integer priority, String column, String conditions, String color) {
		return JsonCreator
				.get()
				.put("priority", priority)
				.put("column", column)
				.put("targetString", conditions)
				.put("colorCode", color)
				.toString();
	}
}
