DROP TABLE if exists mst_password;
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
