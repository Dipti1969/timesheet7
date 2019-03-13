
role_master Insert

insert into role_master(role_name) values('Admin');
insert into role_master(role_name) values('Employee');
insert into role_master(role_name) values('Reception');


department_master:

insert into department_master(dept_name) values('Java');
insert into department_master(dept_name) values('DotNet');
insert into department_master(dept_name) values('Medical');

designation_master:

insert into designation_master(desi_name) values('Doctor');
insert into designation_master(desi_name) values('Developer');
insert into designation_master(desi_name) values('Designer');
insert into designation_master(desi_name) values('Analyst');
insert into designation_master(desi_name) values('Tester');


employee_master:

insert into employee_master(emp_id,password,emp_fname,gender,role_id,email_id,emp_birthdate,emp_status) values('root123','pro8228','Administrator','F',1,'admin@prodigy.com','1980-03-21','T');
insert into employee_master(emp_id,emp_fname,role_id) values('0','-',3);


To Find The Total Employee working Hour:--

select sec_to_time(sum(time_to_sec(timediff(end_time,start_time)))) as to
tal from employee_transaction where emp_id='EMP002';