package jp.co.cec.vfMstMigrator.data.creator;

import java.util.ArrayList;
import java.util.List;

/**
 * Insertクエリを生成するクラス
 */
public class InsertQueryCreator {

	// metadata
	private String tableName;
	private List<String> keys;
	private List<String> cols;
	
	// data
	private List<Object> datas;
	
	public InsertQueryCreator() {
		datas = new ArrayList<Object>();
		keys = new ArrayList<String>();
		cols = new ArrayList<String>();
	}
	
	/**
	 * Set table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Set primary key
	 */
	public void setPrimaryKey(String... primaryKeys) {
		for (String key : primaryKeys) {
			keys.add(key);
		}
	}
	
	/**
	 * Set column name
	 */
	public void setColumnName(String... columnNames) {
		for (String col : columnNames) {
			cols.add(col);
		}
	}
	
	/**
	 * Insert data
	 * @param <T> Data format
	 * @param data Data
	 * @return this instance
	 */
	public <T> InsertQueryCreator put(T data) {
		datas.add(data);
		return this;
	}
	
	/**
	 * Convert wildcard character
	 */
	private String manufacturing(String target) {
		target = target.contains("\\") ? target.replace("\\", "\\\\") : target;
		target = target.contains("¥") ? target.replace("¥", "\\¥") : target;
		target = target.contains("'") ? target.replace("'", "\\'") : target;
		return target;
	}
	
	/**
	 * Return insert query
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " (");
		
		boolean fir = true;
		for (String col : cols) {
			if (fir) {
				fir = false;
			} else {
				sb.append(",");
			}
			sb.append(col);
		}
		sb.append(") VALUES (");
		
		fir = true;
		for (Object obj : datas) {
			if (fir) {
				fir = false;
			} else {
				sb.append(",");
			}
			
			if (obj instanceof String) {
				sb.append("'" + manufacturing((String)obj) + "'");
			} else {
				sb.append(obj);
			}
		}
		sb.append(") ON DUPLICATE KEY UPDATE ");

		fir = true;
		for (int index = 0; index < cols.size(); index++) {
			if (keys.contains(cols.get(index))) {
				continue;
			}
			
			if (fir) {
				fir = false;
			} else {
				sb.append(",");
			}
			
			sb.append(cols.get(index) + "=");
			
			Object obj = datas.get(index);
			if (obj instanceof String) {
				sb.append("'" + manufacturing((String)obj) + "'");
			} else {
				sb.append(obj);
			}
		}
		sb.append(";\n");
		
		return sb.toString();
	}
}
