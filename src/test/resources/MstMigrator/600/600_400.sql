﻿INSERT INTO MST_RESOURCE (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','設備','facility','BH-1322','BH-1322','70200005','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='設備',resource_type='facility',resource_name='BH-1322',resource_name_key='BH-1322',parent_id='70200005',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit､',updated_at='2020-01-08 00:00:00';
INSERT INTO MST_RESOURCE (resource_id,object_category,resource_type,resource_name,resource_name_key,parent_id,test_code,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('res_id_1_1','junit','facility','BH-1322','BH-1322','junit1000','01','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE object_category='junit',resource_type='facility',resource_name='BH-1322',resource_name_key='BH-1322',parent_id='junit1000',test_code='01',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';