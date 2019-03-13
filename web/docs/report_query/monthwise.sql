SELECT 
	DATE_FORMAT(et.trans_date,'%b %Y') As 'Month',	
	em.emp_fname AS EmployeeName,
	DATE_FORMAT(et.trans_date,'%d/%m/%Y') As 'Date',	
	SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS 'Hours',
	abm.assign_by_name AS AssignByName, 
	em1.emp_fname AS ProxyEmployee,
	pm.proj_name AS Project, 
	dm.dept_name AS Department,
	et.work_desc AS WorkDescription
FROM 
	employee_master em,
	employee_master em1,
	employee_transaction et,
	department_master dm,
	project_master pm,
	assign_by_master abm	
WHERE et.emp_id=em.emp_id AND 
	em1.emp_id=et.proxy_empid AND
	abm.assign_by_id=et.assign_by AND
	pm.proj_id=et.proj_id AND
	dm.dept_id=em.dept_id AND
	et.trans_date=DATE_FORMAT(et.trans_date,'" + $P{yearlist} + "-"+$P{monthlist}+"-%d')
GROUP BY Month,EmployeeName,Date,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription
ORDER BY Month