DROP TABLE if exists mst_map;
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
