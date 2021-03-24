create sequence watches_seq;

create table if not exists watches
(
	id int default nextval ('watches_seq')
		primary key,
	title varchar(255) not null,
	description varchar(255),
	price decimal not null,
	image_base64 varchar(255)
)
;

ALTER SEQUENCE watches_seq OWNED BY watches.id;
