Table 1)

department_master:-

create table department_master
(
	dept_id int(2)  primary key auto_increment,
	dept_name varchar(30)
);

Table 2)

role_master:-

create table role_master
(
	role_id int(2)  primary key auto_increment,
	role_name varchar(30)
);

Table 3)

designation_master:-

create table designation_master
(
	desi_id int(2) primary key auto_increment,
	desi_name varchar(20)
);

Table 4)

employee_master:-

create table employee_master
(
	emp_id varchar(20),
	emp_password varchar(20),
	emp_fname varchar(20),
	emp_lname varchar(20),
	gender varchar(1),
	dept_id int(2),
	role_id int(2),
	desi_id int(2),
	emp_email varchar(50),
	emp_address varchar(500),
	emp_phone varchar(15),
	emp_mobile varchar(15),
	emp_birthdate date,
	emp_status varchar(1) default 'T',
        time_right varchar(1) default 'F';

	constraint pk_emp_id primary key(emp_id),
	constraint fk_dept_id foreign key(dept_id) references department_master(dept_id),
	constraint fk_role_id foreign key(role_id) references role_master(role_id),
	constraint fk_desi_id foreign key(desi_id) references designation_master(desi_id)
);

Table 5)
 
assign_by_master :-

create table assign_by_master
(
	assign_by_id int(2) auto_increment,
	assign_by_name varchar(20),
	constraint pk_assign_by_id primary key(assign_by_id)
);

Table 6)

project_master :-

create table project_master
(
	proj_id int(3) auto_increment,
	proj_name varchar(50),
	start_date date,
	end_date date,
	target_date date,
	proj_desc varchar(3000),
	constraint pk_proj_id primary key(proj_id)
);


Table 7)

create table employee_transaction
(
	trans_id bigint auto_increment,
	emp_id varchar(20),
	trans_date date,
	start_time time,
	end_time time,
	assign_by int(2),
	proxy_empid varchar(20),
	proj_id int(3),
	work_desc text,
	status varchar(1) default 'F',

	constraint pk_trans_id primary key(trans_id),

	constraint fk_assignby foreign key(assign_by) references assign_by_master(assign_by_id),

	constraint fk_projid foreign key(proj_id) references project_master(proj_id)
);


Create Trigger Syntax:--->

delimiter //

create trigger upd_empid before update on employee_master
 for each row
begin
    update employee_transaction et set et.emp_id = new.emp_id where et.emp_id=old.emp_id ;
    update employee_transaction etm set etm.proxy_empid=new.emp_id where etm.proxy_empid=old.emp_id;
end;
// delimiter ;

