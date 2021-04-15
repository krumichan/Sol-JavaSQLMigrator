package jp.co.cec.vfMstMigrator.command.manufacturing;

import java.sql.ResultSet;

import jp.co.cec.vfMstMigrator.data.creator.InsertQueryCreator;
import jp.co.cec.vfMstMigrator.executor.NumberingEx;
import jp.co.cec.vfMstMigrator.model.Position2D;
import jp.co.cec.vfMstMigrator.template.Command;
import jp.co.cec.vfMstMigrator.utility.FormatValidationUtil;

/**
 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
 * V1：mst_place_setting
 * V2：mst_map
 */
public class MstMapManufacturing extends Command {
	
	/**
	 * mariaDBからV1マスタデータを取得したデータをV2マスタデータに変換
	 * @return　Insert文
	 * @throws Exception 変換失敗(V1マスタからデータ取得失敗)
	 */
	@Override
	public String execute(ResultSet rs) throws Exception {
		InsertQueryCreator oiq = new InsertQueryCreator();
		oiq.setTableName("mst_map");
		oiq.setColumnName(new String[] { "resource_id", "spec", "validity_start_on", "validity_end_on", "created_by", "created_at", "updated_by", "updated_at" });
		oiq.setPrimaryKey(new String[] { "resource_id", "validity_start_on", "validity_end_on" });
		
		Float x = rs.getFloat("x"); x = rs.wasNull() ? null : x;
		Float y = rs.getFloat("y"); y = rs.wasNull() ? null : y;
		Float z = rs.getFloat("z"); z = rs.wasNull() ? null : z;
		Float width = rs.getFloat("width"); width = rs.wasNull() ? null : width;
		Float height = rs.getFloat("height"); height = rs.wasNull() ? null : height;
		Float depth = rs.getFloat("depth"); depth = rs.wasNull() ? null : depth;
		String polyhedralElement = rs.getString("polyhedral_element"); polyhedralElement = rs.wasNull() ? null : polyhedralElement;
		String image = rs.getString("image"); image = rs.wasNull() ? null : image;
		Integer imageWidth = rs.getInt("image_width"); imageWidth = rs.wasNull() ? null : imageWidth;
		Integer imageHeight = rs.getInt("image_height"); imageHeight = rs.wasNull() ? null : imageHeight;
		
		oiq.put(NumberingEx.getResourceId("01", rs.getString("place_name_key")))
		.put(calculationSpec(x, y, z, width, height, depth, polyhedralElement, image, imageWidth, imageHeight))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_start_on")))
		.put(FormatValidationUtil.dateToString(rs.getDate("validity_end_on")))
		.put(rs.getString("created_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("created_at")))
		.put(rs.getString("updated_by"))
		.put(FormatValidationUtil.dateToString(rs.getDate("updated_at")));

		return oiq.toString();
	}
	
	/**
	 * mst_mapテーブルのspecカラムのデータに変換
	 */
	private String calculationSpec(Float x, Float y, Float z, Float width, Float height, Float depth, String polyhedral_element, String image, Integer imageWidth, Integer imageHeight) {
		StringBuffer sb = new StringBuffer();
		
		String backGround = null;
		if (imageWidth != null &&
			imageHeight != null &&
			image != null) {
			float x1 = -imageWidth * 0.5f;
			float y1 = -imageHeight * 0.5f;
			float x2 = imageWidth * 0.5f;
			float y2 = imageHeight * 0.5f; 
			
			backGround = " \"backGround\": "
					+ "{ \"rect\":[" + x1 + "," + y1 + "," + x2 + "," + y2 + "]"
					+ ",\"image\":\"" + image + "\"}";
		} else {
			backGround = " \"backGround\": "
					+ "{ \"rect\":[]"
					+ ",\"image\":null}";
		}
		
		String area = null;
		if (x != null &&
			y != null &&
			z != null &&
			width != null &&
			height != null) {
			Position2D leftDown = new Position2D(x, z);
			Position2D rightDown = new Position2D(x + width, z);
			Position2D rightUp = new Position2D(x + width, z + height);
			Position2D leftUp = new Position2D(x, z + width);
			area = "\"area\":"
					+ "{ \"polygon\":{\"outerLoop\":[" 
					+ leftDown.x + "," + leftDown.y + "," 
					+ rightDown.x + "," + rightDown.y + ","
					+ rightUp.x + "," + rightUp.y + "," 
					+ leftUp.x + "," + leftUp.y + "],\"innerLoops\":[]}}";
		} else {
			area = "\"area\":"
					+ "{ \"polygon\":{\"outerLoop\":[],\"innerLoops\":[]}}";
		}

		sb.append("[{");
		sb.append(backGround);
		sb.append(",");
		sb.append(area);
		sb.append("}]");
				
		return sb.toString();
	}
}
