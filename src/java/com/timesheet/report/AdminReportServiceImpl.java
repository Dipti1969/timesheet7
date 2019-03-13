package com.timesheet.report;

import com.timesheet.bean.AssignBy;
import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Project;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class AdminReportServiceImpl implements AdminReportService {

    private SimpleJdbcTemplate jdbcTemplate;

    public SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List getYears() {
        try {
            String query = "SELECT DISTINCT(YEAR(trans_date)) AS Y FROM employee_transaction " +
                    "ORDER BY trans_date";
            List yearlist = jdbcTemplate.getJdbcOperations().queryForList(query);

            return yearlist;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

public List<AssignBy> getAssignByList() {
        try {
            String query = "SELECT * FROM assign_by_master WHERE STATUS='T' ORDER BY assign_by_name";
            List<AssignBy> assign_by = jdbcTemplate.getJdbcOperations().query(query, new AssignByRowMapper());
            return assign_by;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }
  private static class AssignByRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AssignBy ab = new AssignBy();
            ab.setAssign_by_id(rs.getInt("assign_by_id"));
            ab.setAssign_by_name(rs.getString("assign_by_name"));
            return ab;
        }
    }
  public List<Project> getProjectList() {
        try {
            String query = "SELECT proj_id, proj_name FROM project_master WHERE STATUS='T' ORDER BY proj_name";
            List<Project> project = jdbcTemplate.getJdbcOperations().query(query, new ProjectRowMapper());
            return project;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }
   private static class ProjectRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project p = new Project();
            p.setProj_id(rs.getInt("proj_id"));
            p.setProj_name(rs.getString("proj_name"));
            return p;
        }
    }
    public List<EmpTransaction> getYearReport(String year) {
        try {
            String query = "SELECT * FROM employee_transaction " +
                    "WHERE trans_date=DATE_FORMAT(trans_date,'" + year + "-%m-%d')";
            List<EmpTransaction> et = jdbcTemplate.query(query, new YearReportRowMapper());
            return et;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public List<Employee> getEmployees() {
        try {
            String query = "SELECT emp_id, emp_fname,emp_lname,role_id FROM employee_master ORDER BY emp_fname,emp_lname";
            List<Employee> emplist = jdbcTemplate.getJdbcOperations().query(query, new GetEmpRowMapper());
            return emplist;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public boolean getData(String date) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE TRANS_DATE='" + date + " '";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            //System.out.println("size in getData(): " + size);
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("error in dae: " + dae);
            return false;
        } catch (Exception e) {
            //System.out.println("error in getData: " + e);
            return false;
        }
    }

    public boolean getCustomData(String startdate, String enddate) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE TRANS_DATE >='" + startdate + " ' AND TRANS_DATE <= '" + enddate + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean getMonthData(String month, String year) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date=DATE_FORMAT(TRANS_DATE,'" + year + "-" + month + "-%d')";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("Error in getMonthData in adminReportServiceImpl: " + dae);
            return false;
        }
    }


    
    public boolean getEmpData(String empid) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE EMP_ID='" + empid + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean getProjectCatData(int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date >='" + start_date + " ' AND trans_date <= '" + end_date + "' AND proj_id='" + proj_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getAssignByCatData(int assign_by_id, String start_date, String end_date) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date >'" + start_date + " ' AND trans_date < '" + end_date + "' AND assign_by='" + assign_by_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getAssignCatData: " + dae);
            return false;
        }
    }

    public String getProjectName(int proj_id) {
        try {
            String query = "SELECT proj_name FROM project_master WHERE proj_id='" + proj_id + "'";
            Project p = (Project) jdbcTemplate.getJdbcOperations().queryForObject(query, new ProjNameMapper());
            String proj_name = p.getProj_name();
            return proj_name;
        } catch (DataAccessException dae) {
            return null;
        }
    }

 

    public List getDatetotal1(String transdate) {

        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date='" + transdate + "'";

            List totalist = jdbcTemplate.getJdbcOperations().queryForList(query);

            return totalist;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }


    public String getDatetotal(String transdate) {

        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date='" + transdate + "'";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

  


    private class ProjNameMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            Project p = new Project();
            p.setProj_name(rs.getString("proj_name"));
            return p;
        }
    }

    public String getAssignByName(int assign_id) {
        try {
            String query = "SELECT assign_by_name FROM assign_by_master WHERE assign_by_id='" + assign_id + "'";
            AssignBy a = (AssignBy) jdbcTemplate.getJdbcOperations().queryForObject(query, new AssignNameMapper());
            String assign_name = a.getAssign_by_name();
            return assign_name;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    private class AssignNameMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            AssignBy a = new AssignBy();
            a.setAssign_by_name(rs.getString("assign_by_name"));
            return a;
        }
    }

    private static class GetEmpRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee e = new Employee();
            e.setEmp_id(rs.getString("emp_id"));
            e.setEmp_fname(rs.getString("emp_fname"));
            e.setEmp_lname(rs.getString("emp_lname"));
            e.setRole_id(rs.getInt("role_id"));
            return e;
        }
    }

    private static class YearReportRowMapper implements ParameterizedRowMapper<EmpTransaction> {

        public EmpTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setTrans_date(getDD_MM_YYYY(rs.getDate("trans_date")));
            et.setAssign_by_id(rs.getInt("assign_by"));
            et.setProxy_empid(rs.getString("proxy_empid"));
            et.setProj_id(rs.getInt("proj_id"));
            et.setWork_desc(rs.getString("work_desc"));
            return et;
        }
    }
 public String getAllTotalDuration(String empid) {
        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE emp_id='" + empid + "'";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));
        } catch (DataAccessException dae) {
            return null;
        }
    }

 public String getTotalDurationWithDate(String transdate) {
        try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date='" + transdate + "'";

            

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

 public String getTotalDurationWithDateEmp(String transdate,String emp_id){
     try {

            String query ="select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    "FROM employee_transaction WHERE trans_date='" + transdate + " ' and emp_id='" + emp_id + "'";



            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
 }


 
  public String getTotalDurationWithMonth(String month,String year){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date=DATE_FORMAT(TRANS_DATE,'" + year + "-" + month + "-%d')";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

   public String getTotalDurationWithEmpMonth(String month,String year,String emp_id){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND " +
                    "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND " +
                    "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " ;
                     
            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }



  public String getTotalDurationWithYear(String year){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date=DATE_FORMAT(trans_date,'" + year + "-%m-%d')";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }


   public String getTotalDurationWithEmpYear(String year,String emp_id){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date=DATE_FORMAT(trans_date,'" + year + "-%m-%d') AND emp_id='"+emp_id+"' ";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }



public String getTotalDurationWithCustomDate(String transdate1,String transdate2){
      try {

        //System.out.println("transdate1" +transdate1);
                //System.out.println("transdate2" +transdate2);


            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction et WHERE et.trans_date>='" + transdate1 + "' AND et.trans_date<='" + transdate2 + "'";

            //System.out.println("query==" +query);

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
             //System.out.println("durooo" +dur);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

public String getTotalDurationWithEmpCustomDate(String date1, String date2, String emp_id){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date>='" + date1 + "' AND trans_date<='" + date2 + "' AND et.emp_id='" + emp_id + "' " ;
                     


            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
             return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }


public String getTotalDurationWithCustomDateReport8(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date){
      try {
         
                //System.out.println("proj_id"+proj_id);

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND et.assign_by='" + assign_by_id + "' AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' ";





            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
             Object o = dur.get(0);
                        //System.out.println("durooo" +o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}")));

            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }


public String getTotalDurationWithCustomDateReport2(String emp_id, String start_date, String end_date){
      try {


            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' ";





            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
             Object o = dur.get(0);
                        //System.out.println("durooo" +o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}")));

            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }




public String getTotalDurationWithCustomDateReport7(int assign_by_id, int proj_id, String start_date, String end_date){
      try {

                //System.out.println("proj_id"+proj_id);

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.assign_by='" + assign_by_id + "' AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' ";





            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
             Object o = dur.get(0);
                        //System.out.println("durooo" +o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}")));

            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

 
public String getTotalDurationWithCustomDateDays(String date1){
      try {

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total " +
                    " from employee_transaction WHERE trans_date ='" + date1 + "'";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));

        } catch (Exception dae) {
            return null;
        }
    }

    private static String getDD_MM_YYYY(Date enteredDate) {
        String ansdate = null;
        try {
            //System.out.println("Into The getDD_MM_YYYY() method..." + enteredDate);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            ansdate = sdf1.format(enteredDate);
            return ansdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String Insert_fmt_date(String dat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdf.parse(dat);
            //System.out.println("Date in Insert_fmt_date: " + dt);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String fmt_date = sdf1.format(dt);
            //System.out.println("fmt_date: " + fmt_date);
            return fmt_date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;

        }
    }



    public String getDay_Date(String dat) {
        String ansdate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(dat);
            //System.out.println("Into The getDD_MM_YYYY() method..." + dat);
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE, d MMM yyyy");
            ansdate = sdf1.format(dt);
            //System.out.println("ansdate: " + ansdate);
            return ansdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<EmpTransaction> getDateWise(String date) {
        try {
            String query = "SELECT DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "em.emp_fname AS EmployeeName, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))), '%H:%i') AS Hours, " +
                    "TIME_FORMAT(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as Total,"+
                    "abm.assign_by_name AS AssignByName, concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, dm.dept_name AS Department, et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND " +
                    "em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND " +
                    "pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date='" + date + "' " +
                    "GROUP BY et.trans_id,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY EmployeeName";

            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new DateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getCustomDateWise(String date1, String date2) {
        try {
            String query = "SELECT DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "em.emp_fname AS EmployeeName, MONTH(et.trans_date) as MONTHNO,  " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date),'%H:%i') as Total, " +
                      "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date>='" + date1 + "' AND et.trans_date<='" + date2 + "' " +
                    "GROUP BY et.trans_id,Date,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY MONTHNO, Date asc";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getCustomDateWiseTotal(String date1, String date2) {
        try {
            String query = "SELECT TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date),'%H:%i') as Total, " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date>='" + date1 + "' AND et.trans_date<='" + date2 + "' " ;
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getMonthWise(String month, String year) {
        try {
            String query = "SELECT em.emp_fname AS EmployeeName, " +
                    "MONTHNAME(et.trans_date) AS Month, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date),'%H:%i') as Total, " +
                    "DAYNAME(et.trans_date) AS Days, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND " +
                    "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND " +
                    "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " +
                    "GROUP BY et.trans_id,Date,EmployeeName,Month,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY Date,EmployeeName";
            //System.out.println("in MonthWise()" + query);
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new MonthWiseMapper());
            //System.out.println("list: " + trans_list);
            return trans_list;

        } catch (DataAccessException dae) {
            //System.out.println("dae: " + dae);
            return null;
        }
    }

    public List<EmpTransaction> getYearWise(String year) {
        try {
            String query = "SELECT DISTINCT(DATE_FORMAT(et.trans_date,'%d/%m/%Y')) As Date , MONTH(et.trans_date) AS MONTHNO, MONTHNAME(et.trans_date) AS Month, " +
                    "em.emp_fname AS EmployeeName, " +
                    " YEAR(et.trans_date) AS Year, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "abm.assign_by_name AS AssignByName," +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-%m-%d') " +
                    "GROUP BY et.trans_id,MONTHNO,Date,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY MONTHNO,Date,EmployeeName";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new YearWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getEmployeeWise(String emp) {
        //System.out.println("Employe" + emp);
        String query = "SELECT YEAR(et.trans_date) AS Year, MONTH(et.trans_date) AS MONTHNO," +
                "MONTHNAME(et.trans_date) AS Month," +
                "concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                 "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE MONTH(el.trans_date)=MONTH(et.trans_date) AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                "abm.assign_by_name AS AssignByName, " +
                "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                "pm.proj_name AS Project, dm.dept_name AS Department, " +
                "et.work_desc AS WorkDescription " +
                "FROM employee_master em, employee_master em1, employee_transaction et, " +
                "department_master dm, project_master pm, assign_by_master abm " +
                "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                "dm.dept_id=em.dept_id AND et.emp_id='" + emp + "' " +
                "GROUP BY et.trans_id,Year,MONTHNO,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                "ORDER BY Year,MONTHNO,EmployeeName,Date";
        List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new EmpWiseMapper());
        return trans_list;
    }



    private static class DateWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_date(rs.getString("Date"));
            et.setEmp_fname(rs.getString("EmployeeName"));
            et.setHour(rs.getString("Hours").toString());
            et.setHour(rs.getString("Total").toString());
            et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

   


    private static class CustomDateWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_date(rs.getString("Date"));
            et.setMonth(rs.getString("MONTHNO"));
            et.setEmp_fname(rs.getString("EmployeeName"));
            
            et.setHour(rs.getString("Hours").toString());
            et.setTotal(rs.getString("Total"));
               et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    private static class MonthWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_date(rs.getString("Month"));
            et.setTrans_date(rs.getString("Date"));
            et.setEmp_fname(rs.getString("EmployeeName"));
            et.setHour(rs.getString("Hours").toString());
            et.setTotal(rs.getString("Total").toString());
             et.setDay(rs.getString("Days"));
            et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    private static class YearWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_date(rs.getString("Date"));
            et.setMonth(rs.getString("MONTH"));
            et.setEmp_fname(rs.getString("EmployeeName"));
            et.setHour(rs.getString("Hours").toString());
            et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    private static class EmpWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setYear(rs.getString("Year"));
            et.setMonth(rs.getString("Month"));
            et.setTrans_date(rs.getString("Date"));
            et.setEmp_fname(rs.getString("EmployeeName"));
            et.setHour(rs.getString("Hours").toString());
            et.setTotal(rs.getString("Total"));
            //System.out.println("TOTAL"+rs.getString("Total"));
             et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    public List<EmpTransaction> getProjectCatagory(int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT pm.proj_name AS Project," +
                    "concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) AS MONTHNO," +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                     "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.proj_id=et.proj_id AND el.assign_by=et.assign_by AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,Project,Date,EmployeeName,Hours,AssignByName,ProxyEmployee,Department,WorkDescription " +
                    "ORDER BY MONTHNO,Project,Date,EmployeeName";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in Project: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in proj: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getAssignByCatagory(int assign_by_id, String start_date, String end_date) {
        try {
            String query = "SELECT abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date,MONTH(et.trans_date) AS MONTHNO, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.proj_id=et.proj_id AND el.assign_by=et.assign_by),'%H:%i') as Total, " +
                    "pm.proj_name AS Project, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.assign_by='" + assign_by_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,AssignByName,Date,EmployeeName,Hours,ProxyEmployee,Project,Department,WorkDescription,Total " +
                    "ORDER BY MONTHNO,AssignByName,Date,EmployeeName";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in assign: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in assign: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getReport2(String emp_id, String start_date, String end_date) {
        try {
            String query = "SELECT concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) as MONTHNO, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                     "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "pm.proj_name AS Project, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,EmployeeName,MONTHNO,Date,Hours,ProxyEmployee,AssignByName,Project,Department,WorkDescription,TOtal " +
                    "ORDER BY EmployeeName,MONTHNO,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in report2: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in report2: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getReport5(String emp_id, int assign_by_id, String start_date, String end_date) {
        try {
            String query = "SELECT concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) AS MONTHNO, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "pm.proj_name AS Project, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND et.assign_by='" + assign_by_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,EmployeeName,AssignByName,Date,Hours,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY MONTHNO,EmployeeName,AssignByName,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in report5: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in report5: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getReport6(String emp_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "pm.proj_name AS Project, MONTH(et.trans_date) AS MONTHNO," +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,EmployeeName,Project,Date,Hours,ProxyEmployee,AssignByName,Department,WorkDescription " +
                    "ORDER BY MONTHNO,EmployeeName,Project,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in report6: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in report6: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getReport7(int assign_by_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT pm.proj_name AS Project, abm.assign_by_name AS AssignByName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) AS MONTHNO," +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.proj_id=et.proj_id AND el.assign_by=et.assign_by),'%H:%i') as Total, " +
                    "concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.assign_by='" + assign_by_id + "' AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,Project,AssignByName,Date,Hours,EmployeeName,ProxyEmployee,Department,WorkDescription,Total " +
                    "ORDER BY MONTHNO,Project,AssignByName,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in report7: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in report7: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getReport8(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT concat_ws(' ',em.emp_fname,em.emp_lname) AS EmployeeName, " +
                    "pm.proj_name AS Project, abm.assign_by_name AS AssignByName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) AS MONTHNO," +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date),'%H:%i') as Total, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND et.assign_by='" + assign_by_id + "' AND et.proj_id='" + proj_id + "' AND " +
                    "et.trans_date>='" + start_date + "' AND et.trans_date<='" + end_date + "' " +
                    "GROUP BY et.trans_id,MONTHNO,EmployeeName,Project,AssignByName,Date,Hours,ProxyEmployee,Department,WorkDescription " +
                    "ORDER BY MONTHNO,EmployeeName,Project,AssignByName,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            //System.out.println("Error in report8: " + dae);
            return null;
        } catch (Exception e) {
            //System.out.println("Error in report8: " + e);
            return null;
        }
    }

    public List<EmpTransaction> getEmpDateWise(String date, String emp_id) {
        try {
            String query = "SELECT em.emp_fname AS EmployeeName, " +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))), '%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                    "abm.assign_by_name AS AssignByName, concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, dm.dept_name AS Department, et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, " +
                    "department_master dm, project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND " +
                    "em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND " +
                    "pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date='" + date + "' AND et.emp_id='" + emp_id + "' " +
                    "GROUP BY et.trans_id,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY EmployeeName";

            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new DateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getEmpCustomDateWise(String date1, String date2, String emp_id) {
        try {
            String query = "SELECT DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                    "em.emp_fname AS EmployeeName, MONTH(et.trans_date) AS MONTHNO," +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE el.trans_date=et.trans_date AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND " +
                    "et.trans_date>='" + date1 + "' AND et.trans_date<='" + date2 + "' AND et.emp_id='" + emp_id + "' " +
                    "GROUP BY et.trans_id,MONTHNO,Date,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY MONTHNO,Date";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new CustomDateWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<EmpTransaction> getEmpMonthWise(String month, String year, String emp_id) {
        try {
            String query = "SELECT em.emp_fname AS EmployeeName, " +
                    "MONTHNAME(et.trans_date) AS Month, MONTH(et.trans_date) AS MONTHNO," +
                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                     "DAYNAME(et.trans_date) AS Days, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "TIME_FORMAT((select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') " +
                    "FROM employee_transaction el WHERE MONTH(el.trans_date)=MONTH(et.trans_date) AND el.emp_id=et.emp_id),'%H:%i') as Total, " +
                    "abm.assign_by_name AS AssignByName, " +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, " +
                    "employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND " +
                    "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND " +
                    "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " +
                    "GROUP BY et.trans_id,MONTHNO,Date,EmployeeName,Month,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription,Total " +
                    "ORDER BY MONTHNO,Date,EmployeeName";
            //System.out.println("in MonthWise()" + query);
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new MonthWiseMapper());
            //System.out.println("list: " + trans_list);
            return trans_list;

        } catch (DataAccessException dae) {
            //System.out.println("dae: " + dae);
            return null;
        }
    }

    public List<EmpTransaction> getEmpYearWise(String year, String emp_id) {
        try {
            String query = "SELECT DISTINCT(DATE_FORMAT(et.trans_date,'%d/%m/%Y')) As Date ,MONTH(et.trans_date) AS MONTHNO, MONTHNAME(et.trans_date) AS Month, " +
                    "em.emp_fname AS EmployeeName, " +
                    " YEAR(et.trans_date) AS Year, " +
                    "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, " +
                    "abm.assign_by_name AS AssignByName," +
                    "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                    "pm.proj_name AS Project, " +
                    "dm.dept_name AS Department, " +
                    "et.work_desc AS WorkDescription " +
                    "FROM employee_master em, employee_master em1, employee_transaction et, department_master dm, " +
                    "project_master pm, assign_by_master abm " +
                    "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND " +
                    "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND " +
                    "dm.dept_id=em.dept_id AND et.emp_id='" + emp_id + "' AND " +
                    "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-%m-%d') " +
                    "GROUP BY et.trans_id,MONTHNO,Date,EmployeeName,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                    "ORDER BY MONTHNO,Date,EmployeeName";
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new YearWiseMapper());
            return trans_list;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List<Employee> getTransEmpName(String date) {
        try {
            String query = "SELECT DISTINCT(EM.EMP_FNAME),EM.EMP_LNAME FROM employee_master EM,employee_transaction ET WHERE" +
                    " ET.EMP_ID=EM.EMP_ID AND TRANS_DATE='" + date + "' ORDER BY EM.EMP_FNAME";
            List<Employee> empname = jdbcTemplate.getJdbcOperations().query(query, new TransEmpName());
            return empname;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private static class TransEmpName implements RowMapper {

        public Object mapRow(ResultSet rs, int num) throws SQLException {
            Employee e = new Employee();
            e.setEmp_fname(rs.getString("emp_fname"));
            e.setEmp_lname(rs.getString("emp_lname"));
            return e;
        }
    }

    public String getEmpName(String emp_id) {
        try {
            String query = "SELECT emp_fname FROM employee_master WHERE emp_id='" + emp_id + "'";
            Employee emp = (Employee) jdbcTemplate.getJdbcOperations().queryForObject(query, new GetEmpNameMapper());
            String empname = emp.getEmp_fname();
            return empname;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    private static class GetEmpNameMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            Employee e = new Employee();
            e.setEmp_fname(rs.getString("emp_fname"));
            return e;
        }
    }

    public boolean getReport2Data(String emp_id, String start_date, String end_date) {
        try {
            String query = "SELECT * from employee_transaction " +
                    "Where emp_id='" + emp_id + "' and trans_date>='" + start_date + "'" +
                    "And trans_date<='" + end_date + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getReport5Data(String emp_id, int assign_by_id, String start_date, String end_date) {
        try {
            String query = "SELECT * from employee_transaction " +
                    "Where emp_id='" + emp_id + "' And trans_date>='" + start_date + "'" +
                    "And trans_date<='" + end_date + "' and assign_by='" + assign_by_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getReport6Data(String emp_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT * from employee_transaction " +
                    "Where emp_id='" + emp_id + "' And trans_date>='" + start_date + "'" +
                    "And trans_date<='" + end_date + "' and proj_id='" + proj_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getReport7Data(int assign_by_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT * from employee_transaction " +
                    "Where assign_by='" + assign_by_id + "' And trans_date>='" + start_date + "'" +
                    "And trans_date<='" + end_date + "' and proj_id='" + proj_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getReport8Data(String emp_id, int assign_by_id, int proj_id, String start_date, String end_date) {
        try {
            String query = "SELECT * from employee_transaction " +
                    "Where assign_by='" + assign_by_id + "' And trans_date>='" + start_date + "'" +
                    "And trans_date<='" + end_date + "' and proj_id='" + proj_id + "' And emp_id='" + emp_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("dae in getProjectCatData: " + dae);
            return false;
        }
    }

    public boolean getEmpDateData(String date, String emp_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date='" + date + " ' and emp_id='" + emp_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("error in getEmpDateData: " + dae);
            return false;
        } catch (Exception e) {
            //System.out.println("error in getEmpDateData: " + e);
            return false;
        }
    }

    public boolean getEmpCustomData(String startdate, String endDate, String emp_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date>='" + startdate + "' and trans_date<='" + endDate + "' and emp_id='" + emp_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("error in getEmpCustomDateData: " + dae);
            return false;
        } catch (Exception e) {
            //System.out.println("error in getEmpCustomDateData: " + e);
            return false;
        }
    }

    public boolean getEmpMonthData(String month, String year, String emp_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date=DATE_FORMAT(trans_date,'" + year + "-" + month + "-%d') and emp_id='" + emp_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("error in getEmpMonthData: " + dae);
            return false;
        } catch (Exception e) {
            //System.out.println("error in getEmpMonthData: " + e);
            return false;
        }
    }

    public boolean getEmpYearData(String year, String emp_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_date=DATE_FORMAT(trans_date,'" + year + "-%m-%d') and emp_id='" + emp_id + "'";
            List record = jdbcTemplate.getJdbcOperations().queryForList(query);
            int size = record.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
            //System.out.println("error in getEmpYearData: " + dae);
            return false;
        } catch (Exception e) {
            //System.out.println("error in getEmpYearData: " + e);
            return false;
        }
    }
}
