DROP TABLE if exists mst_numbering;
CREATE TABLE mst_numbering (
column_name varchar(128) NOT NULL,
serial_number varchar(16) NOT NULL,
value varchar(128) NOT NULL,
created_by varchar(64) NOT NULL,
created_at datetime NOT NULL,
updated_by varchar(64) NOT NULL,
updated_at datetime NOT NULL,
PRIMARY KEY ( column_name, serial_number )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
