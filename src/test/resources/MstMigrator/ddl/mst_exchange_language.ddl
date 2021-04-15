DROP TABLE if exists mst_exchange_language;
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
