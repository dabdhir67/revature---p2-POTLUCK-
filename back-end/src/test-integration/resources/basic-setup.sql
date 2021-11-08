drop table if exists recipe, chef;
create table chef (
	c_id int primary key,
	username varchar not null unique,
	passkey text not null,
	first_name text not null,
	last_name text not null,
	email text not null
);
create table recipe (
	r_id int primary key,
	title text not null,
	date_created timestamp not null,
	c_id int references chef(c_id),
	body text not null
);
