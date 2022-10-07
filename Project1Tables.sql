drop table if exists reimbursements;
drop table if exists users;
drop table if exists roles;

create table roles( 
	role_id SERIAL primary key,
	role_name VARCHAR(30) not null
);

create table users( 
	user_id SERIAL primary key,
	user_name VARCHAR(30) not null unique,
	passphrase VARCHAR(30) not null,
	first_name VARCHAR(30) not null,
	last_name VARCHAR(30) not null,
	user_age integer not null,
	email VARCHAR(50) not null,
	role_id integer references roles(role_id) not null
	);

create table reimbursements( 
	reimburse_id SERIAL primary key,
	reimburse_type VARCHAR(30) not null,
	reimburse_desc VARCHAR(50) not null,
	amount numeric(10,2) not null,
	status VARCHAR(30) not null,
	employee_id integer references users(user_id) not null,
	manager_id integer references users(user_id)
	);
	
insert into roles(role_name) values
('Employee'),
('Manager');


