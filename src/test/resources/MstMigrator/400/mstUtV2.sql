drop database if exists mstUtV2;
create database mstUtV2;
use mstUtV2;

CREATE TABLE mst_exchange_language (
name_id varchar(128) NOT NULL,
language_type varchar(16) NOT NULL,
name varchar(1000) NOT NULL,
validity_start_on datetime NOT NULL,
validity_end_on datetime NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
updated_by varchar(64) NOT NULL,
updated_at datetime NOT NULL,
PRIMARY KEY ( name_id, language_type, validity_start_on, validity_end_on )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE mst_map (
resource_id varchar(256) NOT NULL,
spec varchar(20000) NOT NULL,
validity_start_on datetime NOT NULL,
validity_end_on datetime NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
updated_by varchar(64) NOT NULL,
updated_at datetime NOT NULL,
PRIMARY KEY ( resource_id, validity_start_on, validity_end_on )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE mst_password (
password_id varchar(255) NOT NULL,
user_id varchar(128) ,
user_name varchar(128) ,
password varchar(16) ,
permission varchar(255) NOT NULL,
validity_start_on datetime NOT NULL,
validity_end_on datetime NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
updated_by varchar(64) NOT NULL,
updated_at datetime NOT NULL,
PRIMARY KEY ( password_id, validity_start_on, validity_end_on )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE mst_resource (
resource_id varchar(256) NOT NULL,
object_category varchar(128) NOT NULL,
resource_type varchar(64) NOT NULL,
resource_name varchar(64) NOT NULL,
resource_name_key varchar(128) ,
parent_id varchar(256) NOT NULL,
test_code varchar(16) NOT NULL,
validity_start_on datetime NOT NULL,
validity_end_on datetime NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
updated_by varchar(64) NOT NULL,
updated_at datetime NOT NULL,
PRIMARY KEY ( resource_id, validity_start_on, validity_end_on )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE mst_value_conversion (
signal_class varchar(128) NOT NULL,
signal_category varchar(128) ,
signal_name varchar(128) ,
resource_name varchar(128) NOT NULL ,
value varchar(128) ,
conversion_value varchar(128) ,
conversion_type varchar(128) ,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
PRIMARY KEY ( signal_class, signal_category, signal_name, value, resource_name )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;