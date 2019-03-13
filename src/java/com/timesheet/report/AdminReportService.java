package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Project;
import com.timesheet.bean.AssignBy;

import java.util.List;

    public interface AdminReportService {
    public List getYears();
    public List<EmpTransaction> getYearReport(String year);
    public String Insert_fmt_date(String dat);
    public String getDay_Date(String dat);
    public List<Employee> getEmployees();
    public boolean getData(String date);
    public boolean getCustomData(String startdate,String endDate);
    public boolean getMonthData(String month,String year);
    public boolean getEmpData(String empid);
    public List<EmpTransaction> getDateWise(String date);
    public List<EmpTransaction> getCustomDateWise(String date1, String date2);
    public List<EmpTransaction> getMonthWise(String month, String year);
    public List<EmpTransaction> getYearWise(String year);
    public List<EmpTransaction> getEmployeeWise(String emp);
    public List<EmpTransaction> getProjectCatagory(int proj_id,String start_date, String end_date);
    public List<EmpTransaction> getAssignByCatagory(int assign_by_id,String start_date, String end_date);
    public boolean getProjectCatData(int proj_id,String start_date, String end_date);
    public boolean getAssignByCatData(int assign_by_id,String start_date, String end_date);
    public String getProjectName(int proj_id);
    public String getAssignByName(int assign_id);
    public String getAllTotalDuration(String empid);
    public String getTotalDurationWithDate(String transdate);
    public String getTotalDurationWithMonth(String month,String year);
    public String getTotalDurationWithEmpMonth(String month,String year,String emp_id);
    public String getTotalDurationWithCustomDate(String transdate1,String transdate2);
    public String getTotalDurationWithEmpCustomDate(String date1, String date2, String emp_id);
    public String getTotalDurationWithCustomDateReport8(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date);
    public String getTotalDurationWithCustomDateReport7(int assign_by_id, int proj_id, String start_date, String end_date);
public String getTotalDurationWithCustomDateReport2(String emp_id, String start_date, String end_date);
    public String getTotalDurationWithYear(String year);
     public String getTotalDurationWithEmpYear(String year,String emp_id);
    public String getTotalDurationWithCustomDateDays(String date1);
    public String getDatetotal(String transdate);
public List getDatetotal1(String transdate);
 public List<Project> getProjectList();
     public List<AssignBy> getAssignByList();




    public List<EmpTransaction> getReport2(String emp_id, String start_date, String end_date);
    public List<EmpTransaction> getReport5(String emp_id, int assign_by_id,String start_date, String end_date);
    public List<EmpTransaction> getReport6(String emp_id, int proj_id, String start_date, String end_date);
    public List<EmpTransaction> getReport7(int assign_by_id, int proj_id, String start_date, String end_date);
    public List<EmpTransaction> getReport8(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date);

    public boolean  getReport2Data(String emp_id, String start_date, String end_date);
    public boolean  getReport5Data(String emp_id, int assign_by_id,String start_date, String end_date);
    public boolean  getReport6Data(String emp_id, int proj_id, String start_date, String end_date);
    public boolean  getReport7Data(int assign_by_id, int proj_id, String start_date, String end_date);
    public boolean  getReport8Data(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date);


    public List<EmpTransaction> getEmpDateWise(String date, String emp_id);
    public List<EmpTransaction> getEmpCustomDateWise(String date1, String date2, String emp_id);
    public List<EmpTransaction> getEmpMonthWise(String month, String year, String emp_id);
    public List<EmpTransaction> getEmpYearWise(String year, String emp_id);

    public String getEmpName(String emp_id);
    public List<Employee> getTransEmpName(String date);

    public boolean getEmpDateData(String date, String emp_id);
    public boolean getEmpCustomData(String startdate,String endDate, String emp_id);
    public boolean getEmpMonthData(String month,String year, String emp_id);
    public boolean getEmpYearData(String year, String emp_id);

     public String getTotalDurationWithDateEmp(String transdate,String emp_id);

    
}
