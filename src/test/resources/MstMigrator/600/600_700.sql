﻿INSERT INTO MST_SIGNAL (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_1_1','トレサビ','現品入力結果','ー','Cassandra','','','','{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}',86400,'01','MstMigrator','2020-1-7 17:26:35') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='ー',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='2020-1-7 17:26:35';
INSERT INTO MST_SIGNAL (log_id,signal_class,signal_category,signal_name,data_type,value_type,time_division,value_key_format,retention_type,retention_period,test_code,created_by,created_at) VALUES ('log_id_1_1_1_1_1','トレサビ','現品入力結果','ー','Cassandra','','','','{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}',86400,'01','MstMigrator','2020-1-7 17:26:35') ON DUPLICATE KEY UPDATE signal_class='トレサビ',signal_category='現品入力結果',signal_name='ー',data_type='Cassandra',value_type='',time_division='',value_key_format='',retention_type='{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}',retention_period=86400,test_code='01',created_by='MstMigrator',created_at='2020-1-7 17:29:28';