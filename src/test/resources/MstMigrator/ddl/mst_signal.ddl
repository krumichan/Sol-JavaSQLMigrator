DROP TABLE if exists mst_signal;
CREATE TABLE mst_signal (
log_id varchar(256) NOT NULL,
signal_class varchar(128) NOT NULL,
signal_category varchar(128) NOT NULL,
signal_name varchar(128) NOT NULL,
data_type varchar(128) NOT NULL,
value_type varchar(128) NOT NULL,
time_division varchar(128) NOT NULL,
value_key_format varchar(128) NOT NULL,
retention_type varchar(512) NOT NULL,
retention_period bigint NOT NULL,
test_code varchar(16) NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
PRIMARY KEY ( log_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
