﻿INSERT INTO MST_RESOURCE (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','設備','work_place','電力PLC-01','電力PLC-01','50100002','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='設備',resource_type='work_place',resource_name='電力PLC-01',resource_name_key='電力PLC-01',parent_id='50100002',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';
INSERT INTO MST_RESOURCE (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','junit','facility','facility_key','facility_key','junit1000','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='junit',resource_type='facility',resource_name='facility_key',resource_name_key='facility_key',parent_id='junit1000',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';