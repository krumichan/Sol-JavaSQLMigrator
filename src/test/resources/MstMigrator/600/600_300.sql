﻿INSERT INTO MST_PASSWORD (password_id,user_id,user_name,password,permission,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('junit','junit','テスト利用者','junit','user','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE user_id='junit',user_name='テスト利用者',password='junit',permission='user',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';
INSERT INTO MST_PASSWORD (password_id,user_id,user_name,password,permission,validity_start_on,validity_end_on,created_by,created_at,updated_by,updated_at) VALUES ('junit','junit','junit_name','junit_pass','tester','2019-12-31 00:00:00','2999-12-31 00:00:00','junit','2020-01-08 00:00:00','junit','2020-01-08 00:00:00') ON DUPLICATE KEY UPDATE user_id='junit',user_name='junit_name',password='junit_pass',permission='tester',created_by='junit',created_at='2020-01-08 00:00:00',updated_by='junit',updated_at='2020-01-08 00:00:00';