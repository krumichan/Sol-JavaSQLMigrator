drop database if exists mstUtV1_400;
create database mstUtV1_400;
use mstUtV1_400;

create table mst_exchange_language (
	name_id varchar(128) not null
	,language_type varchar(128) not null
	,name varchar(1000) not null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key ( name_id, language_type, validity_start_on, validity_end_on )
) engine=innodb default charset=utf8;

insert into mst_exchange_language (name_id, language_type, name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('before', 'String', 'before', '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('cec', 'String', 'cec', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_place_setting (
	place_id varchar(16) not null
	,place_type varchar(16) not null
	,place_name_key varchar(128) not null
	,parent_id varchar(16) not null
	,work_type varchar(16) default null
	,x float default null
	,y float default null
	,z float default null
	,width float default null
	,height float default null
	,depth float default null
	,polyhedral_element varchar(20000) default null
	,image varchar(256) default null
	,image_height int default null
	,image_width int default null
	,cycle_time float default null
	,oee_desired_value float default null
	,oee_min_limit float default null
	,teep_desired_value float default null
	,teep_min_limit float default null
	,work_item_judge_type varchar(16) default null
	,work_item_judge_place_id varchar(16) default null
	,quality_item_judge_flg tinyint not null
	,quality_item_judge_place_id varchar(16) default null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (place_id, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_place_setting (place_id, place_type, place_name_key, parent_id, work_type, x, y, z, width, height, depth, polyhedral_element, image, image_height, image_width, cycle_time, oee_desired_value, oee_min_limit, teep_desired_value, teep_min_limit, work_item_judge_type, work_item_judge_place_id, quality_item_judge_flg, quality_item_judge_place_id, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('70200025', 'country', 'country', '00000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'null', null, 0, null, '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('50100005', 'work_place', '電力PLC-02', '71100140', '設備', -74, 0, -207, 60, 47, 15, null, null, null, null, null, null, null, null, null, 'null', null, 0, null, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('71100140', 'area', '電力PLC-01', '40000001', null, 329, 0, -75, 220, 94, 15, null, 'HEAD.png', 800, 1200, null, null, null, null, null, 'null', null, 0, 70200027, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_human (
	human_id varchar(255) not null
	,human_type varchar(20000) not null
	,human_name varchar(128) default null
	,place_id varchar(16) default null
	,human_log_id varchar(16) default null
	,password varchar(16) default null
	,work_group_id varchar(16) default null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (human_id, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_human (human_id, human_type, human_name, place_id, human_log_id, password, work_group_id, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('a571317', 'type3', 'W28岡本', null, null, 'a571317', null, '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('junit', 'user', 'テスト利用者', null, null, 'junit', null, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_data_item (
	log_id varchar(64) not null
	,element_index varchar(128) not null
	,element_index_key varchar(128) default null
	,element varchar(128) not null
	,original_item_name varchar(128) not null
	,subject_type varchar(16) default null
	,acquisition varchar(128) not null
	,calculation_point double default null
	,generation_flag varchar(16) default null
	,input_data_type varchar(128) default null
	,output_data_format varchar(128) default null
	,unit varchar(16) default null
	,device varchar(128) not null
	,master_id varchar(32) default null
	,changing_point varchar(16) default null
	,host_name varchar(128) default null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (log_id, validity_start_on, validity_end_on)
	,key mas_ele_sub_index (master_id, element, subject_type)
) engine=innodb default charset=utf8;

insert into mst_data_item (log_id, element_index, element_index_key, element, original_item_name, subject_type, acquisition, calculation_point, generation_flag, input_data_type, output_data_format, unit, device, master_id, changing_point, host_name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('99999999-70100999-insp', 'none', 'none', '外観検査判定結果', '外観検査判定結果', 'object', 'ProductionControlApi', null, '__C_____________', 'string', null, null, 'VF', '99999999-70100999', 'first', 'ProductionControlApi', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('Head13-0792', 'SpindleTemp_3_path1', 'Head13', '主軸ﾓｰﾀ温度〔℃〕 4軸', '主軸ﾓｰﾀ温度〔℃〕 4軸', 'facility', 'Head13', null, '__C_____________', 'string', null, null, 'VF', '40101000-00132500', 'first', 'ProductionControlApi', '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('60090000-00171100-2-merc', 'EM29_STR', 'EM29_STR', '現品入力結果', '現品入力結果', 'facility', 'ASM1', null, '__C_____________', 'string', null, null, 'VF', '60090000-00171100-2', 'first', 'FERSV5', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('40101000-00132500-merc', 'EM29_STR', 'EM29_STR', '現品入力結果', '現品入力結果', 'object', 'ProductionControlApi', null, '__C_____________', 'string', null, null, 'VF', '40101000-00132500', 'first', 'FERSV5', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_facility_condition(
	facility_group_id varchar(64) not null
	,element_key varchar(128) not null
	,original_value varchar(128) not null
	,condition_id varchar(4000) not null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (facility_group_id, element_key, original_value, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_facility_condition (facility_group_id, element_key, original_value, condition_id, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('エンシュウ_11_134', 'アラーム情報', '0616', 'N1 ﾌﾞﾗｼﾍｯﾄﾞ上昇端LS異常\'（A76.7）', '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('ASUKA_41_84', 'アラーム情報', '0035', 'φ8回転動作ﾀｸﾄ異常', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('ホーコス_31_21', 'アラーム情報', '0000', 'QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'åå¥', '2020-01-08 00:00:00');

create table mst_threshold (
	view_parts_id varchar(32) not null
	,show_index int(4) not null
	,priority int(4) not null
	,show_type varchar(32) not null
	,limit_max float default null
	,limit_max_less_equal tinyint(1) not null
	,limit_min float default null
	,limit_min_greater_equal tinyint(1) not null
	,conditions varchar(128) default null
	,name_key varchar(128) default null
	,color varchar(16) not null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (view_parts_id, show_index, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_threshold (view_parts_id, show_index, priority, show_type, limit_max, limit_max_less_equal, limit_min, limit_min_greater_equal, conditions, name_key, color, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('facility_operating_status', 1, 8, 'string', null, 0, null, 0, '0000', '操作電源切', '#C0C0C0', '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('isz_inspection_plan', 1, 1, 'numeric', 100000000, 0, 10, 0, null, '計画差：10以上', '#FF0000', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('assembly_operating_status', 3, 1, 'string', null, 0, null, 0, '0005', '操作電源入', '#CC99FF', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_facility (
	facility_id varchar(32) not null
	,facility_type varchar(16) not null
	,facility_name_key varchar(128) default null
	,place_id varchar(16) default null
	,facility_log_id varchar(128) default null
	,facility_model varchar(128) default null
	,facility_group_id varchar(64) default null
	,work_group_id varchar(64) default null
	,can_move tinyint(1) not null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key(facility_id, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_facility (facility_id, facility_type, facility_name_key, place_id, facility_log_id, facility_model, facility_group_id, work_group_id, can_move, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('60001900-00159006', 'facility', 'ｺﾝﾍﾞｱ（ﾊｰﾄﾞ搬出）', '70300030', 'Crank42', '三菱', '河合メカニカル_31_121', '作業G192.168.31.121', 0, '2000-01-01 00:00:00', '2019-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('40101000-00139700', 'facility', 'BH-1397', '70300002', 'Crank2', 'ファナック', 'ホーコス_31_21', '作業G192.168.31.21', 0, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('CAM-POWER-01', 'facility', '電力PLC-01', '71100140', 'Cam998', null, '電力収集PLC_41_246', '作業G192.168.41.246', 0, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('40101000-00132200', 'facility', 'BH-1322', '70200005', 'Head6', 'ファナック', '設備G192.168.21.32', '設備G192.168.21.32', 0, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'ceceﾂ・ꀀeﾂ猟､', '2020-01-08 00:00:00');

create table mst_place (
	place_id varchar(16) not null
	,place_type varchar(16) not null
	,place_name_key varchar(128) not null
	,country_id varchar(16) not null
	,base_id varchar(16) not null
	,building_id varchar(16) not null
	,floor_id varchar(16) not null
	,area_id varchar(16) not null
	,sub_area_id varchar(16) not null
	,work_place_id varchar(16) not null
	,country_key varchar(128) not null
	,base_key varchar(128) not null
	,building_key varchar(128) not null
	,floor_key varchar(128) not null
	,area_key varchar(128) not null
	,sub_area_key varchar(128) not null
	,work_place_key varchar(128) not null
	,parent_id varchar(16) not null
	,work_type varchar(16) default null
	,x float default null
	,y float default null
	,z float default null
	,width float default null
	,height float default null
	,depth float default null
	,polyhedral_element varchar(20000) default null
	,image varchar(256) default null
	,image_height int(4) default null
	,image_width int(4) default null
	,cycle_time float default null
	,oee_desired_value float default null
	,oee_min_limit float default null
	,teep_desired_value float default null
	,teep_min_limit float default null
	,work_item_judge_type varchar(16) default null
	,work_item_judge_place_id varchar(16) default null
	,quality_item_judge_flg tinyint(1) not null
	,quality_item_judge_place_id varchar(16) default null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (place_id, validity_start_on, validity_end_on)
) engine=innodb default charset=utf8;

insert into mst_place (place_id, place_type, place_name_key, country_id, base_id, building_id, floor_id, area_id, sub_area_id, work_place_id, country_key, base_key, building_key, floor_key, area_key, sub_area_key, work_place_key, parent_id, work_type, x, y, z, width, height, depth, polyhedral_element, image, image_height, image_width, cycle_time, oee_desired_value, oee_min_limit, teep_desired_value, teep_min_limit, work_item_judge_type, work_item_judge_place_id, quality_item_judge_flg, quality_item_judge_place_id, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	('71100140', 'work_place', '電力PLC-01', '10000000', '20000000', '30000000', '40000001', '50100005', '', '71100140', 'country', 'base', 'building', '加工', 'CAM', '', '電力PLC-01', '50100005', 'ローダー／コンベア', -67, 0, 630, 50, 50, 15, null, null, null, null, null, null, null, null, null, 'null',  null, 0, null, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('70200005', 'work_place', 'BH-1322', '10000000', '20000000', '30000000', '40000001', '50100002', '', '70200005', 'country', 'base', 'building', '加工', 'HEAD', '', 'BH-1322', '50100002', '設備', 26, 0, 227, 47, 57, 15, null, null, null, null, null, null, null, null, null, 'null',  null, 0, null, '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('50100005', 'area', 'CAM', '10000000', '20000000', '30000000', '40000001', '50100005', '', '', 'country', 'base', 'building', '加工', 'CAM', '', '', '40000001', '', 330, 0, 351, 220, 94, 15, null, 'CAM.png', 800, 1200, null, null, null, null, null, 'null',  null, 1, '70500037', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');

create table mst_signal_conversion (
	view_parts_id varchar(32) default null
	,conditions varchar(32) default null
	,element_key varchar(128) not null
	,original_item_name varchar(128) not null
	,major_classification varchar(128) default null
	,medium_classification varchar(128) default null
	,small_classification varchar(128) default null
	,test_code varchar(16) not null
	,validity_start_on datetime not null
	,validity_end_on datetime not null
	,created_by varchar(64) not null
	,created_at datetime not null
	,updated_by varchar(64) not null
	,updated_at datetime not null
	,primary key (element_key, original_item_name, validity_start_on, validity_end_on)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into mst_signal_conversion (view_parts_id, conditions, element_key, original_item_name, major_classification, medium_classification, small_classification, test_code, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) values
	(null, null, '現品入力結果', '現品入力結果', 'トレサビ', '現品入力結果', 'ー', '01', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,(null, null, 'アラーム情報', 'QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ', 'アラーム', 'アラーム情報', 'QCST1丈決め戻ﾀｲﾑｵｰﾊﾞｰ', '01', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00')
	,('assembly_operating_status', '0005', 'assembly_operating_status', '0005', '稼働状態', '稼働状態コード', '0005_aos', '01', '2019-12-31 00:00:00', '2999-12-31 00:00:00', 'junit', '2020-01-08 00:00:00', 'junit', '2020-01-08 00:00:00');