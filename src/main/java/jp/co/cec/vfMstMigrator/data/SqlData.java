package jp.co.cec.vfMstMigrator.data;

import java.util.HashMap;
import java.util.Map;

import jp.co.cec.vfMstMigrator.data.enums.KeyEnum;

public class SqlData {

	// metadata
	private static SqlData instance = null;
	public Map<String, String[]> map = null;
	
	private SqlData() { init(); }
	
	
	public static SqlData get() {
		return ((instance == null) ? (instance = new SqlData()) : instance);
	}

	private void init() {
		map = new HashMap<String, String[]>();
		set();
	}
	
	private void set() {
		map.put(
				KeyEnum.ExLangToExLang.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(name_id) "
						+ " FROM 	mst_exchange_language "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT 		* "
						+ " FROM 		mst_exchange_language "
						+ " WHERE 		validity_start_on <= ? "
						+ " AND 		validity_end_on > ? "
						+ " ORDER BY 	name_id "
				});
		
		map.put(
				KeyEnum.PlacSetToMap.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(place_id) "
						+ " FROM 	 mst_place_setting "
						+ " WHERE 	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "		place_name_key "
						+ " ,	work_type "
						+ " ,	place_type "
						+ " ,	parent_id "
						+ " ,	x "
						+ " ,	y "
						+ " ,	z "
						+ " ,	width "
						+ " ,	height "
						+ " ,	depth "
						+ " ,	polyhedral_element "
						+ " ,	image "
						+ " ,	image_width "
						+ " ,	image_height "
						+ " ,	validity_start_on "
						+ " ,	validity_end_on "
						+ " ,	created_at "
						+ " ,	created_by "
						+ " ,	updated_at "
						+ " ,	updated_by "
						+ " FROM 	 mst_place_setting "
						+ " WHERE 	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						+ " GROUP BY place_name_key "
						+ " ORDER BY place_id "
				});
		
		map.put(
				KeyEnum.HumanToPass.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(human_id) "
						+ " FROM 	mst_human "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ " 		human_id					AS password_id "
						+ " ,		SUBSTRING(human_id, 1, 128)	AS user_id "
						+ " ,		human_name					AS user_name "
						+ " ,		password					AS password "
						+ " ,		human_type					AS permission "
						+ " ,		validity_start_on			AS validity_start_on "
						+ " ,		validity_end_on				AS validity_end_on "
						+ " ,		created_by					AS created_by "
						+ " ,		created_at					AS created_at "
						+ " ,		updated_by					AS updated_by "
						+ " ,		updated_at					AS updated_at "
						+ " FROM 		mst_human "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 		validity_end_on 	> ? "
						+ " ORDER BY 	human_id "
				});
		
		map.put(
				KeyEnum.FaciToRes.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(facility_id) "  
						+ " FROM 	mst_facility "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND		validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ " COALESCE(plac.work_type, '') AS object_category "
						+ " ,  faci.facility_type        AS resource_type "
						+ " ,  faci.facility_name_key    AS resource_name "
						+ " ,  faci.facility_name_key    AS resource_name_key "
						+ " ,  '01'                      AS test_code "
						+ " ,  faci.validity_start_on    AS validity_start_on "
						+ " ,  faci.validity_end_on      AS validity_end_on "
						+ " ,  faci.created_by           AS created_by "
						+ " ,  faci.created_at           AS created_at "
						+ " ,  faci.updated_by           AS updated_by "
						+ " ,  faci.updated_at           AS updated_at "
						+ " ,  IF(plac.area_id = '', "
						+ "       NULL "
						+ "       ,placB.place_name_key) AS parent_resource_name_key "
						+ " FROM        mst_facility  faci "
						+ " INNER JOIN  mst_place     plac "
						+ " ON  faci.place_id             =  plac.place_id "
						+ " AND plac.validity_start_on   <= ? "
						+ " AND plac.validity_end_on      > ? "
						+ " INNER JOIN  mst_place     placB "
						+ " ON  (placB.place_id           = plac.parent_id "
						+ " OR   plac.parent_id           = '') "
						+ " AND  placB.validity_start_on <= ? "
						+ " AND  placB.validity_end_on    > ? "
						+ " WHERE faci.validity_start_on <= ? "
						+ " AND   faci.validity_end_on    > ? "
						+ " GROUP BY faci.facility_name_key "
						+ " ORDER BY faci.facility_id "
				});
		
		map.put(
				KeyEnum.PlacSetToRes.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(place_id) "
						+ " FROM 	 mst_place_setting "
						+ " WHERE 	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						
						,
								
						""
						+ " SELECT "
						+ " COALESCE(plse.work_type, '') AS object_category "
						+ " ,  plse.place_type           AS resource_type "
						+ " ,  plse.place_name_key       AS resource_name "
						+ " ,  plse.place_name_key       AS resource_name_key "
						+ " ,  '01'                      AS test_code "
						+ " ,  plse.validity_start_on    AS validity_start_on "
						+ " ,  plse.validity_end_on      AS validity_end_on "
						+ " ,  plse.created_by           AS created_by "
						+ " ,  plse.created_at           AS created_at "
						+ " ,  plse.updated_by           AS updated_by "
						+ " ,  plse.updated_at           AS updated_at "
						+ " ,  plseB.place_name_key      AS parent_resource_name_key "
						+ " FROM      mst_place_setting  plse "
						+ " LEFT JOIN mst_facility       faci "
						+ " ON  plse.place_id = faci.place_id "
						+ " AND faci.validity_start_on   <= ? "
						+ " AND faci.validity_end_on      > ? "
						+ " LEFT JOIN mst_place_setting  plseB "
						+ " ON  plseB.place_id = plse.parent_id "
						+ " AND plseB.validity_start_on  <= ? "
						+ " AND plseB.validity_end_on     > ? "
						+ " WHERE plse.validity_start_on <= ? "
						+ " AND   plse.validity_end_on    > ? "
						+ " AND   faci.place_id IS NULL "
						+ " GROUP BY plse.place_id "
						+ " ORDER BY plse.place_id "
				});
		
		map.put(
				KeyEnum.DatToSigAcq.toString()
				,new String[] {
						""
						+ " SELECT "
						+ " 	COUNT(log_id) "
						+ " FROM 	 mst_data_item "
						+ " WHERE 	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "     dait.device					AS connector  "
						+ " ,   dait.host_name				AS connector_instance "
						+ " ,   conve.major_classification  AS signal_class  "
						+ " ,   conve.medium_classification AS signal_category "
						+ " ,   conve.small_classification 	AS signal_name  "
						+ " ,   dait.element_index_key		AS subject_instance "
						+ " ,   dait.element_index			AS value_instance "
						+ " ,   ''							AS conversion "
						+ " ,   '01'						AS test_code "
						+ " ,   'MstMigrator'			    AS created_by "
						+ " ,   now()						AS created_at "
						+ " ,   dait.host_name				AS host_name "
						+ " ,   dait.element_index_key		AS element_index_key "
						+ " ,   dait.element_index			AS element_index "
						+ " FROM mst_data_item dait "
						+ " INNER JOIN mst_signal_conversion conve  "
						+ " ON  conve.element_key         	 = dait.element "
						+ " AND conve.original_item_name 	 = dait.original_item_name "
						+ " AND conve.test_code          	 = '01' "
						+ " AND conve.validity_start_on 	<= ? "
						+ " AND conve.validity_end_on   	 > ? "
						+ " WHERE dait.validity_start_on	<= ? "
						+ " AND   dait.validity_end_on		 > ? "
						+ " GROUP BY conve.major_classification, conve.medium_classification, conve.small_classification, dait.host_name, dait.element_index, dait.element_index_key "
						+ " ORDER BY dait.log_id "
				});
		
		map.put(
				KeyEnum.FaciConToSig.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(facility_group_id) "
						+ " FROM 	mst_facility_condition "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "    conve.major_classification		AS signal_class "
						+ ",   conve.medium_classification		AS signal_category "
						+ ",   conve.small_classification		AS signal_name "
						+ ",   'Cassandra'						AS data_type "
						+ ",   ''								AS value_type "
						+ ",   ''								AS time_division "
						+ ",   ''								AS value_key_format "
						+ ",   '{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}' AS retention_type "
						+ ",   86400							AS retention_period "
						+ ",   '01'              			 	AS test_code "
						+ ",   'MstMigrator'			      	AS created_by "
						+ ",   now()            				AS created_at "
						+ "FROM mst_facility_condition fcon "
						+ "INNER JOIN mst_signal_conversion conve "
						+ "ON  conve.element_key			= fcon.element_key "
						+ "AND conve.original_item_name 	= fcon.condition_id "
						+ "AND conve.test_code          	= '01' "
						+ "AND conve.validity_start_on 	<= ? "
						+ "AND conve.validity_end_on   	>  ? "
						+ "WHERE fcon.validity_start_on	<= ? "
						+ "AND   fcon.validity_end_on	>  ? "
						+ "GROUP BY signal_class, signal_category, signal_name "
						+ "ORDER BY fcon.facility_group_id "
				});
		
		map.put(
				KeyEnum.DatToSig.toString()
				,new String[] {
						""
						+ " SELECT COUNT(log_id) "
						+ " FROM 	 mst_data_item "
						+ " WHERE 	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT  "
						+ "    conve.major_classification		AS signal_class "
						+ ",   conve.medium_classification		AS signal_category "
						+ ",   conve.small_classification		AS signal_name "
						+ ",   'Cassandra'						AS data_type "
						+ ",   ''								AS value_type "
						+ ",   ''								AS time_division "
						+ ",   ''								AS value_key_format "
						+ ",   '{\"latestStore\": {\"store\": false,\"emptyValueKey\": false},\"historyStore\": {\"store\": true}}' AS retention_type "
						+ ",   86400							AS retention_period "
						+ ",   '01'              			 	AS test_code "
						+ ",   'MstMigrator'			      	AS created_by "
						+ ",   now()            				AS created_at "
						+ "FROM mst_data_item dait "
						+ "INNER JOIN mst_signal_conversion conve "
						+ "ON  conve.element_key			= dait.element "
						+ "AND conve.original_item_name 	= dait.original_item_name "
						+ "AND conve.test_code          	= '01' "
						+ "AND conve.validity_start_on 	<= ? "
						+ "AND conve.validity_end_on   	>  ? "
						+ "WHERE dait.validity_start_on	<= ? "
						+ "AND   dait.validity_end_on	>  ? "
						+ "GROUP BY signal_class, signal_category, signal_name " 
						+ "ORDER BY dait.log_id "
				});
		
		map.put(
				KeyEnum.FaciConToValCon.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(facility_group_id) "
						+ " FROM 	mst_facility_condition "
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "     conve.major_classification  AS signal_class "
						+ " ,   conve.medium_classification AS signal_category "
						+ " ,   conve.small_classification  AS signal_name "
						+ " ,   faci.facility_name_key      AS resource_name "
						+ " ,   fcon.original_value         AS value "
						+ " ,   fcon.condition_id           AS conversion_value "
						+ " ,   'Replacement'               AS conversion_type "
						+ " ,   fcon.updated_by             AS created_by "
						+ " ,   fcon.updated_at             AS created_at "
						+ " FROM mst_facility_condition fcon "
						+ " INNER JOIN mst_facility faci "
						+ " ON  fcon.facility_group_id    =  faci.facility_group_id "
						+ " AND faci.validity_start_on    <= ? "
						+ " AND faci.validity_end_on      >  ? "
						+ " INNER JOIN mst_signal_conversion conve "
						+ " ON  conve.element_key        	= fcon.element_key "
						+ " AND conve.original_item_name 	= fcon.condition_id "
						+ " AND conve.test_code          	= '01' "
						+ " AND conve.validity_start_on 	<= ? "
						+ " AND conve.validity_end_on   	>  ? "
						+ " WHERE fcon.validity_start_on	<= ? "
						+ " AND   fcon.validity_end_on		>  ? "
						+ " ORDER BY fcon.facility_group_id "
				});
		
		map.put(
				KeyEnum.ThreToValCon.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(view_parts_id) " 
						+ " FROM 	mst_threshold " 
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "     conve.major_classification  	AS signal_class "
						+ " ,   conve.medium_classification 	AS signal_category "
						+ " ,   conve.small_classification 	AS signal_name "
						+ " ,   ''							AS resource_name "
						+ " ,   thre.conditions        		AS value "
						+ " ,   thre.name_key          		AS conversion_value "
						+ " ,   'Replacement'              	AS conversion_type "
						+ " ,   thre.created_by            	AS created_by "
						+ " ,   thre.created_at            	AS created_at "
						+ " FROM mst_threshold thre "
						+ " INNER JOIN mst_signal_conversion conve  "
						+ " ON  conve.view_parts_id       = thre.view_parts_id "
						+ " AND conve.conditions 			= thre.conditions "
						+ " AND conve.test_code          	= '01' "
						+ " AND conve.validity_start_on 	<= ? "
						+ " AND conve.validity_end_on   	>  ? "
						+ " WHERE thre.validity_start_on	<= ? "
						+ " AND   thre.validity_end_on	>  ? "
						+ " ORDER BY thre.view_parts_id "
				});
		
		map.put(
				KeyEnum.ThreToViePaConf.toString()
				,new String[] {
						""
						+ " SELECT "
						+ "		COUNT(view_parts_id) " 
						+ " FROM 	mst_threshold " 
						+ " WHERE 	validity_start_on  <= ? "
						+ " AND 	validity_end_on 	> ? "
						
						,
						
						""
						+ " SELECT "
						+ "		conditions "
						+ " ,	priority "
						+ " ,	limit_max "
						+ " ,	limit_min "
						+ " ,	color "
						+ " FROM 	 mst_threshold "
						+ " WHERE  	 validity_start_on <= ? "
						+ " AND 	 validity_end_on 	> ? "
						+ " ORDER BY view_parts_id "
				});
	}
	
	public String[] getSql(String key) {
		return map.get(key);
	}
}
