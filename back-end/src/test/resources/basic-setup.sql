drop table if exists recipe, chef;
create table chef (
	c_id int primary key,
	username varchar not null unique,
	passkey varchar not null,
	first_name varchar not null,
	last_name varchar not null,
	email text not null
);
create table recipe (
	r_id int primary key,
	title varchar not null,
	date_created timestamp not null,
	c_id int references chef(c_id),
	body varchar not null
);
