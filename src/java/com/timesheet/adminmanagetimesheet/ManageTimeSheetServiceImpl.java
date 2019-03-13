package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class ManageTimeSheetServiceImpl implements ManageTimeSheetService {

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

    public String Insert_fmt_date(String dat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdf.parse(dat);
           // System.out.println("Date in Insert_fmt_date: " + dt);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String fmt_date = sdf1.format(dt);
            //System.out.println("fmt_date: " + fmt_date);
            return fmt_date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public List<Employee> getEmployees() {
        try {
            String query = "SELECT emp_id,emp_fname,emp_lname,dept_id,role_id,emp_email FROM employee_master "
                    + "ORDER BY emp_fname, emp_lname";
            List<Employee> emplist = jdbcTemplate.getJdbcOperations().query(query, new GetEmpRowMapper());
            return emplist;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    private static class GetEmpRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmp_id(rs.getString("emp_id"));
            employee.setEmp_fname(rs.getString("emp_fname"));
            String name = employee.getEmp_fname();
            name += " " + rs.getString("emp_lname");
            employee.setEmp_fname(name);
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setRole_id(rs.getInt("role_id"));
            employee.setEmp_email(rs.getString("emp_email"));
            return employee;

        }
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

    public static String getDD_MM_YYYY(Date transDate) {
        String transdate = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            transdate = sdf1.format(transDate);
            return transdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertTimeSheet(EmpTransaction et) {
        try {
            String str = et.getTrans_date();
            String work_desc = et.getWork_desc();

            String fmt_date = Insert_fmt_date(str);
            String status = "T";

            //for taking current indian date......
            SimpleDateFormat cursdf = new SimpleDateFormat("dd/MM/yyyy");
            TimeZone istTime = TimeZone.getTimeZone("IST");
            cursdf.setTimeZone(istTime);
            String curdt = cursdf.format(new Date());

            Date cdt = null;
            try {
                cdt = cursdf.parse(curdt);
            } catch (ParseException ex) {
                Logger.getLogger(ManageTimeSheetServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            //for taking timesheet date...........
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = null;
            try {
                dt = sdf.parse(str);
            } catch (ParseException ex) {
                Logger.getLogger(ManageTimeSheetServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }


            if (dt.compareTo(cdt) < 0) {
               // System.out.println("***********************************************************");
               // System.out.println("current date" + cdt);
               // System.out.println("timsheet date" + dt);
               // System.out.println("Late timesheet..........................");
                work_desc = "* " + work_desc;
            }



            String query = "INSERT INTO employee_transaction (emp_id,trans_date,start_time,end_time,assign_by,proxy_empid,proj_id,work_desc,status) VALUES (?,?,?,?,?,?,?,?,?)";
            Object[] obj = new Object[]{et.getEmp_id(), fmt_date, et.getStart_time(), et.getEnd_time(), et.getAssign_by_id(), et.getProxy_empid(), et.getProj_id(), work_desc, status};
            jdbcTemplate.update(query, obj);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    public List<EmpTransaction> getStatus() {
        String query = "SELECT * FROM employee_transaction WHERE STATUS='F'";
        try {
            List<EmpTransaction> et = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new StatusRowMapper());
            return et;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    private static class StatusRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rownum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getInt("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setTrans_date(getDD_MM_YYYY(rs.getDate("trans_date")));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            return et;
        }
    }

    public boolean addTimeSheet(long trans_id, String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc) {
        String statusT = "T";
       // System.out.println("Into The AddTimeSheet Query=====");

        String query = "UPDATE employee_transaction SET end_time='" + end_time + "',assign_by='" + assign_by + "', "
                + "proxy_empid='" + proxy_empid + "', proj_id='" + proj_id + "', work_desc=?, status='" + statusT + "' "
                + "where trans_id='" + trans_id + "'";


      //  System.out.println("The Query is=====" + query);

        jdbcTemplate.getJdbcOperations().update(query, new Object[]{work_desc});
        return true;

    }

    public List<EmpTransaction> getMonthTransaction(String emp_id) {
        String query = "SELECT et.trans_id,time_format(timediff(et.end_time,et.start_time),'%H:%i') as hour, "
                + "et.emp_id,emp.emp_fname,emp_lname,ab.assign_by_name,"
                + "et.trans_date,et.start_time,et.end_time,proj.proj_name,et.work_desc,et.proxy_empid  FROM employee_transaction et,"
                + "employee_master emp,assign_by_master ab, project_master proj"
                + " WHERE et.emp_id='" + emp_id + "' and ab.assign_by_id=et.assign_by and "
                + "emp.emp_id=et.proxy_empid and "
                + "et.proj_id=proj.proj_id ORDER BY et.trans_date desc";

        try {

            List<EmpTransaction> et = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new MonthTransactionRowMapper());
           // System.out.println("et: " + et);
            String str = et.toString().substring(et.toString().indexOf("[") + 1, et.toString().indexOf("]"));
           // System.out.println("str: " + str);
            if (!str.equals("")) {
                return et;
            } else {
                return null;
            }
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }

    }

    private static class MonthTransactionRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setHour(getHHMM(rs.getString("hour").toString()));
            et.setEmp_id(rs.getString("emp_id"));
            et.setEmp_fname(rs.getString("emp_fname"));
            String name = et.getEmp_fname();
            name += " " + rs.getString("emp_lname");
            et.setAssign_by_name(rs.getString("assign_by_name"));
            et.setTrans_date(getDD_MM_YYYY(rs.getDate("trans_date")));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            String s = rs.getString("proxy_empid");
            if (s.equals("0")) {
                et.setProxy_empid("-");
            } else {
                et.setProxy_empid(name);
            }
            et.setProj_name(rs.getString("proj_name"));
            et.setWork_desc(rs.getString("work_desc"));

            return et;
        }
    }

    public String getTotalDuration(String empid) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            TimeZone tz = TimeZone.getTimeZone("IST");
            sdf.setTimeZone(tz);
            String dt = sdf.format(new Date());

            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total "
                    + " from employee_transaction WHERE emp_id='" + empid + "'"
                    + " and trans_date >= date_format('" + dt + "','%Y-%m-01')";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public String getEmpName(String emp_id) {
        try {
            String query = "SELECT emp_fname FROM employee_master WHERE emp_id='" + emp_id + "'";
            Employee emp =
                    (Employee) jdbcTemplate.getJdbcOperations().queryForObject(query, new GetEmpNameMapper());
            String empname = emp.getEmp_fname();
          //  System.out.println(empname);
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

    public String getMonthYearHours(String empid, String month, String year) {
        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total "
                    + " from employee_transaction WHERE emp_id='" + empid + "'"
                    + " and trans_date = date_format(trans_date,'" + year + "-" + month + "-%d')";
            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
           // System.out.println("Query=====" + query);
            Object o = dur.get(0);
           // System.out.println("o: " + o);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public EmpTransaction getEmpTransaction(long trans_id, String emp_id) {
        try {
            String query = "SELECT * FROM employee_transaction WHERE trans_id='" + trans_id + "'";
           // System.out.println("query is created in getProject: " + query);
            EmpTransaction trans = (EmpTransaction) jdbcTemplate.getJdbcOperations().queryForObject(query, new TransRowMapper());
            return trans;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    private static class TransRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmpTransaction et = new EmpTransaction();
            et.setTrans_id(rs.getLong("trans_id"));
            et.setEmp_id(rs.getString("emp_id"));
            et.setTrans_date(getDD_MM_YYYY(rs.getDate("trans_date")));
            et.setStart_time(getHHMM(rs.getString("start_time")));
            et.setEnd_time(getHHMM(rs.getString("end_time")));
            et.setAssign_by_id(rs.getInt("assign_by"));
            et.setProxy_empid(rs.getString("proxy_empid"));
            et.setProj_id(rs.getInt("proj_id"));
            et.setWork_desc(rs.getString("work_desc"));
            return et;
        }
    }

    public boolean updateEmpTransaction(long trans_id, String trans_date, String start_time, String end_time, int assign_by, String proxy_empid, int proj_id, String work_desc) {
        try {
           // System.out.println("=== updatetransaction method()====");
            String fmt_date = Insert_fmt_date(trans_date);
           // System.out.println("fmt_date: " + fmt_date);
            String query = "UPDATE employee_transaction SET trans_date='" + fmt_date + "' ,"
                    + "start_time='" + start_time + "', end_time='" + end_time + "' ,"
                    + "assign_by='" + assign_by + "', "
                    + "proxy_empid='" + proxy_empid + "', proj_id='" + proj_id + "' ,"
                    + " work_desc=? "
                    + "where trans_id='" + trans_id + "'";
           // System.out.println("query : " + query);
            jdbcTemplate.getJdbcOperations().update(query, new Object[]{work_desc});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAllTotalDuration(String empid) {
        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total "
                    + " from employee_transaction WHERE emp_id='" + empid + "'";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public boolean deleteTransaction(long trans_id) {
        try {
            String query = "DELETE FROM employee_transaction "
                    + "WHERE trans_id='" + trans_id + "'";

            jdbcTemplate.getJdbcOperations().execute(query);
            return true;

        } catch (Exception e) {
           // System.out.println("Error in deleteTransaction: " + e);
            return false;
        }
    }

    public List<EmpTransaction> getDateTransaction(String emp_id, String trans_date) {
        String query = "SELECT et.trans_id,time_format(timediff(et.end_time,et.start_time),'%H:%i') as hour, "
                + "et.emp_id,emp.emp_fname,emp_lname,ab.assign_by_name,"
                + "et.trans_date,et.start_time,et.end_time,proj.proj_name,et.work_desc,et.proxy_empid  FROM employee_transaction et,"
                + "employee_master emp,assign_by_master ab, project_master proj"
                + " WHERE et.emp_id='" + emp_id + "' and trans_date='" + trans_date + "' and ab.assign_by_id=et.assign_by and "
                + "emp.emp_id=et.proxy_empid and "
                + "et.proj_id=proj.proj_id ORDER BY et.trans_date desc";

        try {

            List<EmpTransaction> et = (List<EmpTransaction>) jdbcTemplate.getJdbcOperations().query(query, new MonthTransactionRowMapper());
           // System.out.println("et: " + et);
            String str = et.toString().substring(et.toString().indexOf("[") + 1, et.toString().indexOf("]"));
           // System.out.println("str: " + str);
            if (!str.equals("")) {
                return et;
            } else {
                return null;
            }
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public String getTotalDurationWithDate(String empid, String trans_date) {
        try {
            String query = "select time_format(cast(sec_to_time(sum(time_to_sec(subtime(end_time,start_time)))) as char),'%H:%i') as total "
                    + " from employee_transaction WHERE emp_id='" + empid + "' AND trans_date='" + trans_date + "'";

            List dur = jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = dur.get(0);
            return o.toString().substring(o.toString().indexOf("=") + 1, o.toString().indexOf("}"));
        } catch (DataAccessException dae) {
            return null;
        }
    }
}
