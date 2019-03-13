package com.timesheet.emptransaction;

import com.timesheet.bean.AssignBy;
import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Project;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class EmpTransactionServiceImpl extends HttpServlet implements EmpTransactionService {

    HttpServletRequest request;
    protected SimpleJdbcTemplate jdbcTemplate = null;

    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTransaction(String emp_id) {
       // System.out.println("=== addTransaction is called....");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String trans_date = sdf.format(dt);

        String start_time = getTimeFormat(dt);
        try {
            String query = "INSERT INTO employee_transaction (emp_id, trans_date, start_time) VALUES(?,?,?)";
            Object[] obj = new Object[]{emp_id, trans_date, start_time};
            jdbcTemplate.getJdbcOperations().update(query, obj);
        } catch (DataAccessException dae) {
            dae.printStackTrace();
        }
    }

    public boolean updateTransaction(String emp_id, int assign_by, String proxy_empid, int proj_id, String work_desc) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String trans_date = sdf.format(dt);
        String end_time = getTimeFormat(dt);
        String statusT = "T";
        String statusF = "F";
        String query = "UPDATE employee_transaction SET "
                + "end_time='" + end_time + "', assign_by='" + assign_by + "', "
                + "proxy_empid='" + proxy_empid + "', proj_id='" + proj_id + "', "
                + "work_desc=?, status='" + statusT + "' "
                + "where emp_id='" + emp_id + "' and "
                + "trans_date='" + trans_date + "' and "
                + "status='" + statusF + "'";
        try {
            jdbcTemplate.getJdbcOperations().update(query, new Object[]{work_desc});
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public String checkRight(String username) {
        try {

            String query = "select time_right as t from employee_master where emp_id='" + username + "'";
            List time_right = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = time_right.get(0);

            return o.toString().substring(3, 4);

        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertTransaction(String emp_id, String start_time, String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc, String trans_date) {
        try {
           // System.out.println("Into controller==111 ::" + end_time);

            SimpleDateFormat cursdf = new SimpleDateFormat("dd/MM/yyyy");
            TimeZone istTime = TimeZone.getTimeZone("IST");
            cursdf.setTimeZone(istTime);
            String curdt = cursdf.format(new Date());

            Date cdt = null;
            try {
                cdt = cursdf.parse(curdt);
            } catch (ParseException ex) {
                Logger.getLogger(EmpTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = null;
            try {
                dt = sdf.parse(trans_date);
            } catch (ParseException ex) {
                Logger.getLogger(EmpTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (dt.compareTo(cdt) < 0) {
               // System.out.println("***********************************************************");
               // System.out.println("current date" + cdt);
               // System.out.println("timsheet date" + dt);
               // System.out.println("Late timesheet..........................");
                work_desc = "* " + work_desc;
            }
            // System.out.println("Date in Insert_fmt_date: " + dt);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String fmt_date = sdf1.format(dt);
           // System.out.println("fmt_date: " + fmt_date);


            String query = "insert into employee_transaction(emp_id,trans_date,start_time,end_time,assign_by,proxy_empid,proj_id,work_desc,status) values('" + emp_id + "','" + fmt_date + "',?,?,?,?,?,?,'T')";

            Object[] obj = new Object[]{start_time, end_time, assign_by, proxy_empid, proj_id, work_desc};
            jdbcTemplate.getJdbcOperations().update(query, obj);

        } catch (DataAccessException dae) {
            dae.printStackTrace();
        }
    }

    private String getTimeFormat(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String fmt_time = sdf.format(dt);
        return fmt_time;
    }

    private static String getHHMM(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date dt = sdf.parse(time);
            String fmt_time = sdf.format(dt);
            return fmt_time;
        } catch (ParseException pe) {
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

    public boolean updateTimesheetright(long trans_id, String start_time, String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc) {
        try {
            String query = "UPDATE employee_transaction SET start_time='" + start_time + "', end_time='" + end_time + "',assign_by='" + assign_by + "', "
                    + "proxy_empid='" + proxy_empid + "', proj_id='" + proj_id + "', work_desc=? "
                    + "where trans_id='" + trans_id + "'";

            jdbcTemplate.getJdbcOperations().update(query, new Object[]{work_desc});
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public List<Employee> getProxyEmpList() {
        try {
            String query = "SELECT emp_id, emp_fname, emp_lname, dept_id, desi_id, role_id FROM employee_master ORDER BY emp_fname,emp_lname";
            List<Employee> employee = jdbcTemplate.getJdbcOperations().query(query, new EmployeeRowMaster());
            return employee;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
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

    private static class AssignByRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AssignBy ab = new AssignBy();
            ab.setAssign_by_id(rs.getInt("assign_by_id"));
            ab.setAssign_by_name(rs.getString("assign_by_name"));
            return ab;
        }
    }

    private static class EmployeeRowMaster implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmp_id(rs.getString("emp_id"));
            String name = rs.getString("emp_fname");
            name += " " + rs.getString("emp_lname");
            employee.setEmp_fname(name);
            employee.setEmp_lname(rs.getString("emp_lname"));
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setDesi_id(rs.getInt("desi_id"));
            employee.setRole_id(rs.getInt("role_id"));
            return employee;
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

    public List<EmpTransaction> getCurrentTransaction(String emp_id) {
       // System.out.println("get current transaction is called....................");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("IST");
        sdf.setTimeZone(tz);
        String dt = sdf.format(new Date());

        String query = "select * from employee_master emp, employee_transaction et, "
                + "assign_by_master ab, project_master proj "
                + "where ab.assign_by_id=et.assign_by and "
                + "emp.emp_id=et.proxy_empid and "
                + "et.proj_id=proj.proj_id and "
                + "et.emp_id='" + emp_id + "' and "
                + "et.trans_date='" + dt + "'";
        try {
            List<EmpTransaction> emp_trans_list = jdbcTemplate.getJdbcOperations().query(query, new CurrTransRowMapper());
           // System.out.println("------------" + emp_trans_list.size() + emp_trans_list);
            return emp_trans_list;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public EmpTransaction getDetail(long trans_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_id='" + trans_id + "'";
          //  System.out.println("query is created in getProject: " + query);
            EmpTransaction trans = (EmpTransaction) jdbcTemplate.getJdbcOperations().queryForObject(query, new TransRowMapper());
          //  System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
          //  System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+trans.getTrans_date());
           // System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            return trans;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    private static class TransRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            et.setAssign_by_id(rs.getInt("assign_by"));
            et.setProxy_empid(rs.getString("proxy_empid"));
            et.setTrans_date(rs.getString("trans_date"));
            et.setProj_id(rs.getInt("proj_id"));
            et.setWork_desc(rs.getString("work_desc"));
            return et;
        }
    }

    public static String getDD_MM_YYYY(Date enteredDate) {
        String ansdate = null;
        try {
           // System.out.println("Into The getDD_MM_YYYY() method..." + enteredDate);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            ansdate = sdf1.format(enteredDate);
            return ansdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EmpTransaction> getTransaction(String emp_id) {
        try {

            String chkDateQuery = "SELECT DISTINCT(trans_date) FROM employee_transaction "
                    + "WHERE emp_id='" + emp_id + "' ORDER BY trans_date desc limit 1";
            EmpTransaction et = (EmpTransaction) jdbcTemplate.getJdbcOperations().queryForObject(chkDateQuery, new getChkDate());
            String chkDate = et.getTrans_date().toString();
           // System.out.println("The Check Date is===" + chkDate);

            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String cur_date = sdf.format(dt);
          //  System.out.println("cur_date: " + cur_date);

            if (chkDate.equals(cur_date)) {
                String trans_date_query = "SELECT DISTINCT(trans_date) FROM employee_transaction "
                        + "WHERE emp_id='" + emp_id + "' ORDER BY trans_date desc limit 1,1";
              //  System.out.println("query in if: " + trans_date_query);
                List<EmpTransaction> list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(trans_date_query, new getChkDate());

                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        EmpTransaction et1 = (EmpTransaction) list.get(i);
                        String trans_date = et1.getTrans_date().toString();
                        String query = "select * from employee_master emp, employee_transaction et, "
                                + "assign_by_master ab, project_master proj "
                                + "where ab.assign_by_id=et.assign_by and "
                                + "emp.emp_id=et.proxy_empid and "
                                + "et.proj_id=proj.proj_id and "
                                + "et.emp_id='" + emp_id + "' and "
                                + "et.trans_date IN('" + trans_date + "','" + cur_date + "')";
                      //  System.out.println("query is created....: " + query);
                        List<EmpTransaction> emp_trans_list = jdbcTemplate.getJdbcOperations().query(query, new CurrTransRowMapper());
                      //  System.out.println("List is done..." + emp_trans_list);
                        return emp_trans_list;
                    }
                } else {
                    String query = "select * from employee_master emp, employee_transaction et, "
                            + "assign_by_master ab, project_master proj "
                            + "where ab.assign_by_id=et.assign_by and "
                            + "emp.emp_id=et.proxy_empid and "
                            + "et.proj_id=proj.proj_id and "
                            + "et.emp_id='" + emp_id + "' and "
                            + "et.trans_date IN('" + cur_date + "')";
                   // System.out.println("query is created....: " + query);
                    List<EmpTransaction> emp_trans_list = jdbcTemplate.getJdbcOperations().query(query, new CurrTransRowMapper());
                   // System.out.println("List is done..." + emp_trans_list);
                    return emp_trans_list;
                }
                return null;
            } else {
                String query = "select * from employee_master emp, employee_transaction et, "
                        + "assign_by_master ab, project_master proj "
                        + "where ab.assign_by_id=et.assign_by and "
                        + "emp.emp_id=et.proxy_empid and "
                        + "et.proj_id=proj.proj_id and "
                        + "et.emp_id='" + emp_id + "' and "
                        + "et.trans_date='" + chkDate + "'";
               // System.out.println("query is created....: " + query);
                List<EmpTransaction> emp_trans_list = jdbcTemplate.getJdbcOperations().query(query, new CurrTransRowMapper());
               // System.out.println("List is done..." + emp_trans_list);
                return emp_trans_list;

            }

        } catch (DataAccessException dae) {
            return null;
        } catch (Exception e) {
           // System.out.println("Error: " + e);
            return null;
        }
    }

    private static class getChkDate implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
           // System.out.println("in row mapper");
            et.setTrans_date(rs.getString("trans_date"));
           // System.out.println("rs.getString(): " + rs.getString("trans_date"));
            return et;
        }
    }

    private static String getDD_MON_YYYY(
            Date dt) {
        String dt1 = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            dt1 =
                    sdf1.format(dt);
            return dt1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }





    }

    private static class CurrTransRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setTrans_date(getDD_MM_YYYY(rs.getDate("trans_date")));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            et.setAssign_by_id(rs.getInt("assign_by"));
            et.setAssign_by_name(rs.getString("assign_by_name"));
            String s = rs.getString("proxy_empid");
            if (s.equals("0")) {
                et.setProxy_empid("-");
            } else {
                et.setEmp_fname(rs.getString("emp_fname"));
                String name = et.getEmp_fname();
                name += " " + rs.getString("emp_lname");
                et.setProxy_empid(name);
            }

            et.setProj_id(rs.getInt("proj_id"));
            et.setProj_name(rs.getString("proj_name"));
            et.setWork_desc(rs.getString("work_desc"));
            String start1 = rs.getString("start_time");
            String end1 = rs.getString("end_time");

            try {
                et.setHour(getHHMM(timeDifference(start1, end1)));
            } catch (ParseException ex) {
                Logger.getLogger(EmpTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // System.out.println("Start Time" + start1 + "===" + end1 + "===" + duration);

            return et;
        }

        public String timeDifference(String time1, String time2) throws ParseException {
            DateFormat df = DateFormat.getTimeInstance();
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            DateFormat df1 = new SimpleDateFormat("HH:mm:ss");

            Date date1 = df1.parse(time1);
            Date date2 = df1.parse(time2);

            long remainder = date2.getTime() - date1.getTime();

            long min = remainder / 60000;
            long sec = (remainder % 60000) / 1000;
            String minute = "" + min;
            String second = "" + sec;
            if (min / 10 == 0) {
                minute = "0" + min;
            }
            if (sec / 10 == 0) {
                second = "0" + sec;
            }

            int hh = (int) min / 60;
            int mm = (int) min % 60;

            int phh = Math.abs(hh);
            int pmm = Math.abs(mm);

            String shh = getDoubleDigit(phh);
            String smm = getDoubleDigit(pmm);

            String sIsN = Integer.toString(hh);
            boolean isNegative = sIsN.matches("[-].*");

            String addNeg = "";
            if (isNegative == true) {
                addNeg = "-";
            } else {
                addNeg = "+";
            }

           // System.out.println("Hour:minutes:seconds " + addNeg + shh + ":" + smm + ":" + second);
            return shh + ":" + smm + ":" + second;
        }

        protected String getDoubleDigit(int i) {
            String newI = null;
            switch (i) {
                case 0:
                    newI = "00";
                    break;
                case 1:
                    newI = "01";
                    break;
                case 2:
                    newI = "02";
                    break;
                case 3:
                    newI = "03";
                    break;
                case 4:
                    newI = "04";
                    break;
                case 5:
                    newI = "05";
                    break;
                case 6:
                    newI = "06";
                    break;
                case 7:
                    newI = "07";
                    break;
                case 8:
                    newI = "08";
                    break;
                case 9:
                    newI = "09";
                    break;
                default:
                    newI = Integer.toString(i);
            }
            return newI;
        }
    }

    public boolean updateTimesheet(long trans_id, int assign_by, String proxy_empid, int proj_id, String work_desc) {
        try {
            String query = "UPDATE employee_transaction SET assign_by='" + assign_by + "', "
                    + "proxy_empid='" + proxy_empid + "', proj_id='" + proj_id + "', work_desc=? "
                    + "where trans_id='" + trans_id + "'";

            jdbcTemplate.getJdbcOperations().update(query, new Object[]{work_desc});
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }

    }

    public String getPrevDayTotalHours(String emp_id) {
        try {
            String chkDateQuery = "SELECT DISTINCT(trans_date) FROM employee_transaction "
                    + "WHERE emp_id='" + emp_id + "' ORDER BY trans_date desc limit 1";
            EmpTransaction et = (EmpTransaction) jdbcTemplate.getJdbcOperations().queryForObject(chkDateQuery, new getChkDate());
            String chkDate = et.getTrans_date().toString();
           // System.out.println("The Check Date is===" + chkDate);

            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String cur_date = sdf.format(dt);
           // System.out.println("cur_date: " + cur_date);

            if (chkDate.equals(cur_date)) {
                String trans_date_query = "SELECT DISTINCT(trans_date) FROM employee_transaction "
                        + "WHERE emp_id='" + emp_id + "' ORDER BY trans_date desc limit 1,1";
                List<EmpTransaction> list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(trans_date_query, new getChkDate());
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        EmpTransaction et1 = (EmpTransaction) list.get(i);
                        String trans_date = et1.getTrans_date().toString();
                        String query = "SELECT time_format(CAST(SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(end_time,start_time)))) AS CHAR),'%H:%i') "
                                + "FROM employee_transaction WHERE emp_id='" + emp_id + "' and trans_date IN('" + trans_date + "','" + cur_date + "')";
                       // System.out.println("query is created...:" + query);
                        List<EmpTransaction> tot_hrs = jdbcTemplate.getJdbcOperations().queryForList(query);
                        String totalhours = tot_hrs.toString().substring(tot_hrs.toString().indexOf("=") + 1, tot_hrs.toString().indexOf("}"));
                       // System.out.println("totalhours in getTotalHours: " + totalhours);
                        return totalhours;
                    }
                } else {
                    String query = "SELECT time_format(CAST(SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(end_time,start_time)))) AS CHAR),'%H:%i') "
                            + "FROM employee_transaction WHERE emp_id='" + emp_id + "' and trans_date IN('" + cur_date + "')";
                  //  System.out.println("query is created...:" + query);
                    List<EmpTransaction> tot_hrs = jdbcTemplate.getJdbcOperations().queryForList(query);
                    String totalhours = tot_hrs.toString().substring(tot_hrs.toString().indexOf("=") + 1, tot_hrs.toString().indexOf("}"));
                   // System.out.println("totalhours in getTotalHours: " + totalhours);
                    return totalhours;
                }
                return null;
            } else {
                String query = "SELECT time_format(CAST(SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(end_time,start_time)))) AS CHAR ),'%H:%i') "
                        + "FROM employee_transaction WHERE emp_id='" + emp_id + "' and trans_date='" + chkDate + "'";
               // System.out.println("query is created....: " + query);
                List<EmpTransaction> tot_hrs = jdbcTemplate.getJdbcOperations().queryForList(query);
                String totalhours = tot_hrs.toString().substring(tot_hrs.toString().indexOf("=") + 1, tot_hrs.toString().indexOf("}"));
               // System.out.println("totalhours in getTotalHours: " + totalhours);
                return totalhours;
            }

        } catch (StringIndexOutOfBoundsException se) {
            se.printStackTrace();
            return null;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }

    }

    public String getCurrentDayTotalHours(
            String empid) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            TimeZone tz = TimeZone.getTimeZone("IST");
            sdf.setTimeZone(tz);
            String dt = sdf.format(new Date());

            String query = "SELECT TIME_FORMAT(CAST(SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(end_time,start_time)))) AS CHAR),'%H:%i') AS hours "
                    + "FROM employee_transaction WHERE emp_id='" + empid + "' and trans_date='" + dt + "'";
            List<EmpTransaction> tot_hrs = jdbcTemplate.getJdbcOperations().queryForList(query);
            String totalhours = tot_hrs.toString().substring(tot_hrs.toString().indexOf("=") + 1, tot_hrs.toString().indexOf("}"));
           // System.out.println("tot_hrs in getCurrentDayTotalHours: " + tot_hrs.toString());
            return totalhours;
        } catch (StringIndexOutOfBoundsException se) {
            se.printStackTrace();
            return null;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        } catch (Exception e) {
           // System.out.println("Eror: " + e);
            return null;
        }

    }

    public List<EmpTransaction> getCheckTotalHours(String month, String year, String emp_id) {
        try {
            String query = "SELECT DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, "
                    + "start_time, end_time, MONTH(et.trans_date) AS MONTHNO, "
                    + "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))),'%H:%i') AS Hours, "
                    + "abm.assign_by_name AS AssignByName, "
                    + "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, "
                    + "pm.proj_name AS Project, "
                    + "dm.dept_name AS Department, "
                    + "et.work_desc AS WorkDescription "
                    + "FROM employee_master em, employee_master em1, "
                    + "employee_transaction et, department_master dm, "
                    + "project_master pm, assign_by_master abm "
                    + "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND "
                    + "abm.assign_by_id=et.assign_by AND "
                    + "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND "
                    + "et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') AND "
                    + "et.emp_id='" + emp_id + "' ORDER BY MONTHNO, Date";
           // System.out.println("in MonthWise()" + query);
            List<EmpTransaction> trans_list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new MonthWiseMapper());
           // System.out.println("list: " + trans_list);
            return trans_list;

        } catch (DataAccessException dae) {
           // System.out.println("dae: " + dae);
            return null;
        }





    }

    private static class MonthWiseMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_date(rs.getString("Date"));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            et.setHour(rs.getString("Hours").toString());
            et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    public List<EmpTransaction> getAllTimeSheet() {
        try {

           // System.out.println("==== getAllTimeSheet method is called =====");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            TimeZone tz = TimeZone.getTimeZone("IST");
            sdf.setTimeZone(tz);
            String dt = sdf.format(new Date());

            String query = "SELECT et.trans_id as trans_id, et.emp_id as emp_id,start_time, end_time, abm.assign_by_name AS AssignByName, "
                    + "concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, "
                    + "pm.proj_name AS Project, dm.dept_name AS Department, "
                    + "et.work_desc AS WorkDescription "
                    + "FROM employee_master em, employee_master em1, "
                    + "employee_transaction et, department_master dm, "
                    + "project_master pm, assign_by_master abm "
                    + "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND "
                    + "abm.assign_by_id=et.assign_by AND pm.proj_id=et.proj_id AND "
                    + "dm.dept_id=em.dept_id AND et.trans_date='" + dt + "' ";
            List<EmpTransaction> list = jdbcTemplate.getJdbcOperations().query(query, new AllTimeSheetMapper());
            int size = list.size();
            if (size > 0) {
                return list;
            } else {
                return null;
            }

        } catch (DataAccessException dae) {
            System.out.println("dae in getAllTimeSheet(): " + dae);
            return null;
        }


    }

    private static class AllTimeSheetMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            et.setAssign_by_name(rs.getString("AssignByName"));
            et.setProxy_empid(rs.getString("ProxyEmployee"));
            et.setProj_name(rs.getString("Project"));
            et.setDept_name(rs.getString("Department"));
            et.setWork_desc(rs.getString("WorkDescription"));
            return et;
        }
    }

    public List<EmpTransaction> getEnd_Time(String trans_date, String emp_id) {
        try {
            String query = "select start_time, end_time from employee_transaction where emp_id='" + emp_id + "' and trans_date='" + trans_date + "' order by end_time desc limit 2";
           // System.out.println("The Query is ===" + query);
            List<EmpTransaction> list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new EndTimeRowMapper());
          //  System.out.println("List in getEnd_Time MEthod======" + list.size());
            return list;
        } catch (DataAccessException dae) {
           // System.out.println("Error in getEnd_Time Method=======" + dae);
            return null;
        }

    }

    public List<EmpTransaction> getEnd_TimeForValidation(String trans_date, String emp_id) {
        try {
            String query = "select start_time, end_time from employee_transaction where emp_id='" + emp_id + "' and trans_date='" + trans_date + "' order by end_time desc";
           // System.out.println("The Query is ===" + query);
            List<EmpTransaction> list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new EndTimeRowMapper());
           // System.out.println("List in getEnd_Time MEthod======" + list.size());
            return list;
        } catch (DataAccessException dae) {
           // System.out.println("Error in getEnd_Time Method=======" + dae);
            return null;
        }

    }
    
     public List<EmpTransaction> getEnd_TimeForValidationUpdate(String trans_date, String emp_id, long trans_id) {
        try {
            String query = "select start_time, end_time from employee_transaction where emp_id='" + emp_id + "' and trans_date='" + trans_date + "' and  trans_id !='"+trans_id+"'  order by end_time desc";
          //  System.out.println("The Query is ===" + query);
            List<EmpTransaction> list = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new EndTimeRowMapper());
          //  System.out.println("List in getEnd_Time MEthod======" + list.size());
            return list;
        } catch (DataAccessException dae) {
           // System.out.println("Error in getEnd_Time Method=======" + dae);
            return null;
        }

    }

    private static class EndTimeRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int arg1) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setStart_time(rs.getString("start_time"));
            et.setEnd_time(rs.getString("end_time"));
            return et;
        }
    }
}
