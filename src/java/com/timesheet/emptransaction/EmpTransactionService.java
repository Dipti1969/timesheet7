package com.timesheet.emptransaction;

import com.timesheet.bean.AssignBy;
import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Project;
import java.util.List;

public interface EmpTransactionService {

    public void addTransaction(String emp_id);

    public boolean updateTransaction(String emp_id, int assign_by, String proxy_empid, int proj_id, String work_desc);

    public List<AssignBy> getAssignByList();

    public List<Employee> getProxyEmpList();

    public List<Project> getProjectList();

    public List<EmpTransaction> getCurrentTransaction(String emp_id);

    public List<EmpTransaction> getTransaction(String emp_id);

    public EmpTransaction getDetail(long trans_id);

    public boolean updateTimesheet(long trans_id, int assign_by, String proxy_empid, int proj_id, String work_desc);

    public String getPrevDayTotalHours(String emp_id);

    public String getCurrentDayTotalHours(String emp_id);

    public String checkRight(String username);

    public void insertTransaction(String emp_id, String start_time, String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc, String trans_date);
    public boolean updateTimesheetright(long trans_id,String start_time,String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc);

    public List<EmpTransaction> getCheckTotalHours(String month, String year, String emp_id);

    public List<EmpTransaction> getAllTimeSheet();
    public List<EmpTransaction> getEnd_Time(String trans_date,String emp_id);
    public List<EmpTransaction> getEnd_TimeForValidation(String trans_date,String emp_id);
    public List<EmpTransaction> getEnd_TimeForValidationUpdate(String trans_date,String emp_id,long trans_id);
    
}
