DROP TABLE if exists mst_signal_acquisition;
CREATE TABLE mst_signal_acquisition (
signal_id varchar(512) NOT NULL,
connector varchar(128) NOT NULL,
connector_instance varchar(128) NOT NULL,
signal_class varchar(128) NOT NULL,
signal_category varchar(128) NOT NULL,
signal_name varchar(128) NOT NULL,
subject_instance varchar(1024) NOT NULL,
value_instance varchar(1024) NOT NULL,
conversion varchar(10000) NOT NULL,
test_code varchar(16) NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
PRIMARY KEY ( signal_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
