/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.changeprofile;

import com.timesheet.bean.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author prodigy
 */
public class ProfileServiceImpl extends HttpServlet implements ProfileService {

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

    public Employee getByPk(String emp_id) {
        try {
            Employee employee = new Employee();
            String selectbypk = "SELECT * FROM employee_master  WHERE EMP_ID = '" + emp_id + "'";
           // System.out.println("RegistrationServiceImpl class into getByPk() method is called :: The query is=" + selectbypk);
            employee = (Employee) jdbcTemplate.queryForObject(selectbypk, new EmployeeRowMapper());
            return employee;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public String Insert_fmt_date(String dat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date dt = sdf.parse(dat);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            String fmt_date = sdf1.format(dt);
            return fmt_date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public static String getDD_MM_YYYY(Date enteredDate) {
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

    public List<Employee> checkUserId(String userid) {

        try {
            String query = "select emp_id from employee_master where emp_id='" + userid + "'";
            List<Employee> e = jdbcTemplate.getJdbcOperations().query(query, new UserRowMapper());
            return e;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    private static class UserRowMapper implements ParameterizedRowMapper<Employee> {

        public Employee mapRow(ResultSet rs, int rownum) throws SQLException {
            Employee employee = new Employee();
            employee.setUserid(rs.getString("emp_id"));
            employee.setEmp_id(rs.getString("emp_id"));
            return employee;
        }
    }

    public boolean changeprofile(String emp_id, String userid, String emp_fname, String fname, String emp_lname, String gender, String emp_email, String emp_address, String emp_phone, String emp_mobile, String emp_birthdate) {
        try {
            String update = "update employee_master set emp_id='" + userid + "', emp_fname='" + fname + "',emp_lname='" + emp_lname + "',gender='" + gender + "',emp_email='" + emp_email + "',emp_address=?,emp_phone='" + emp_phone + "',emp_mobile='" + emp_mobile + "',emp_birthdate='" + emp_birthdate + "' where emp_id='" + emp_id + "'";
            jdbcTemplate.update(update, new Object[]{emp_address});
            //jdbcTemplate.getJdbcOperations().update(update);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    private static class EmployeeRowMapper implements ParameterizedRowMapper<Employee> {

        public Employee mapRow(ResultSet rs, int rownum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmp_id(rs.getString("emp_id"));
            employee.setEmp_password(rs.getString("emp_password"));
            employee.setEmp_fname(rs.getString("emp_fname"));
            employee.setEmp_lname(rs.getString("emp_lname"));
            employee.setGender(rs.getString("gender"));
            employee.setEmp_email(rs.getString("emp_email"));
            employee.setEmp_address(rs.getString("emp_address"));
            employee.setEmp_phone(rs.getString("emp_phone"));
            employee.setEmp_mobile(rs.getString("emp_mobile"));
            employee.setEmp_birthdate(getDD_MM_YYYY(rs.getDate("emp_birthdate")));
            return employee;
        }
    }

    public List<Employee> checkFName(String empfname){
        try{
            String query = "SELECT emp_fname FROM employee_master WHERE emp_fname='"+empfname+"'";
            List<Employee> e = jdbcTemplate.getJdbcOperations().query(query, new FNameRowMapper());
            return e;
        }catch(DataAccessException dae ){
           // System.out.println("dae error in checkFName: "+dae);
            return null;
        }
    }

     private static class FNameRowMapper implements RowMapper{

        public Object mapRow(ResultSet rs, int rownum) throws SQLException {
            Employee employee = new Employee();            
            employee.setEmp_fname(rs.getString("emp_fname"));
            employee.setFname(rs.getString("emp_fname"));
            return employee;
        }
    }
}
