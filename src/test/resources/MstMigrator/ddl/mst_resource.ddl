DROP TABLE if exists mst_resource;
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
