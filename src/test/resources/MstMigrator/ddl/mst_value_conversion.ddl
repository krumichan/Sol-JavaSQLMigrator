DROP TABLE if exists mst_value_conversion;
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
