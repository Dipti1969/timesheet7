/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.registration;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
import com.timesheet.bean.Employee;
import com.timesheet.bean.Role;
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
public class RegistrationServiceImpl extends HttpServlet implements RegistrationService {

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

    public void add(Employee employee) {
        try {
            String str = employee.getEmp_birthdate();
            String fmt_date = Insert_fmt_date(str);            
            //  String pass = RandomPassword.getRandomString(6);
            String insert = "INSERT INTO employee_master (emp_id,emp_password,emp_fname,emp_lname,gender,dept_id,role_id,desi_id,emp_email,emp_address,emp_phone,emp_mobile,emp_birthdate,time_right) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //System.out.println("RegistrationServiceImpl class into add() method is called :: The query is=" + insert);
            Object[] obj = new Object[]{employee.getEmp_id(), employee.getEmp_password(), employee.getEmp_fname(), employee.getEmp_lname(), employee.getGender(), employee.getDept_id(), employee.getRole_id(), employee.getDesi_id(), employee.getEmp_email(), employee.getEmp_address(), employee.getEmp_phone(), employee.getEmp_mobile(), fmt_date, employee.getTime_right()};
            jdbcTemplate.update(insert, obj);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> select() {
        try {
            String select = "SELECT * FROM employee_master E,role_master R,department_master DE,designation_master DS WHERE E.ROLE_ID=R.ROLE_ID AND E.DEPT_ID=DE.DEPT_ID AND E.DESI_ID=DS.DESI_ID ORDER BY emp_id";
            List<Employee> employee = jdbcTemplate.query(select, new EmployeeRowMapper());
            //System.out.println("After The end Of The Calling jdbcTemplate" + employee);
            return employee;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Role> getByRole() {
        try {
            String selectrole = "SELECT * FROM role_master order by role_name";
            //System.out.println("RegistrationServiceImpl class into getByRole() method is called :: The query is=" + selectrole);
            if (jdbcTemplate != null) {
                List<Role> role = jdbcTemplate.query(selectrole, new RoleRowMapper());
                //System.out.println("After The end Of The Calling jdbcTemplate" + role);
                return role;
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Department> getByDepartment() {
        try {
            String selectdept = "SELECT * FROM department_master WHERE status='T' order by dept_name ";
            //System.out.println("RegistrationServiceImpl class into getByDepartment() method is called :: The query is=" + selectdept);
            if (jdbcTemplate != null) {
                List<Department> department = jdbcTemplate.query(selectdept, new DepartmentRowMapper());
                //System.out.println("After The end Of The Calling jdbcTemplate" + department);
                return department;
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Designation> getByDesignation() {
        try {
            String selectdesi = "SELECT * FROM designation_master WHERE status='T' order by desi_name ";
            //System.out.println("RegistrationServiceImpl class into getByDesignation() method is called :: The query is=" + selectdesi);
            if (jdbcTemplate != null) {
                List<Designation> designations = jdbcTemplate.query(selectdesi, new DesignationRowMapper());
                //System.out.println("After The end Of The Calling jdbcTemplate" + designations);
                return designations;
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getByPk(String emp_id) {
        try {
            Employee employee = new Employee();
            String selectbypk = "SELECT * FROM employee_master E,role_master R,department_master DE,designation_master DS WHERE E.ROLE_ID=R.ROLE_ID AND E.DEPT_ID=DE.DEPT_ID AND E.DESI_ID=DS.DESI_ID AND EMP_ID = '" + emp_id + "'";
            //System.out.println("RegistrationServiceImpl class into getByPk() method is called :: The query is=" + selectbypk);
            employee = (Employee) jdbcTemplate.queryForObject(selectbypk, new EmployeeRowMapper());
            return employee;
        } catch (DataAccessException e) {
            e.printStackTrace();
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

    public boolean update(String emp_id, String emp_fname,String fname ,String emp_lname, String gender, int dept_id, int roleid, int desi_id, String time_right, String emp_email, String emp_address, String emp_phone, String emp_mobile, String emp_birthdate) {
        try {
            String update = "update employee_master set emp_fname='" + fname + "',emp_lname='" + emp_lname + "',gender='" + gender + "',dept_id='" + dept_id + "', role_id='" + roleid + "', desi_id='" + desi_id + "', time_right='"+time_right+"',emp_email='" + emp_email + "',emp_address=?,emp_phone='" + emp_phone + "',emp_mobile='" + emp_mobile + "',emp_birthdate='" + emp_birthdate + "'where emp_id=?";
            jdbcTemplate.update(update, new Object[]{emp_address, emp_id});
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean blockuser(String emp_id) {
        try {
            String statusF = "F";
            String block = "UPDATE employee_master SET emp_status='" + statusF + "' WHERE emp_id = '" + emp_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean unblockuser(String emp_id) {
        try {
            String statusT = "T";
            String block = "UPDATE employee_master SET emp_status='" + statusT + "' WHERE emp_id = '" + emp_id + "'";
            jdbcTemplate.update(block);
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
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setDept_name(rs.getString("dept_name"));
            employee.setRole_id(rs.getInt("role_id"));
            employee.setRole_name(rs.getString("role_name"));
            employee.setTime_right(rs.getString("time_right"));
            employee.setDesi_id(rs.getInt("desi_id"));
            employee.setDesi_name(rs.getString("desi_name"));
            employee.setEmp_email(rs.getString("emp_email"));
            employee.setEmp_address(rs.getString("emp_address"));
            employee.setEmp_phone(rs.getString("emp_phone"));
            employee.setEmp_mobile(rs.getString("emp_mobile"));
            employee.setEmp_birthdate(getDD_MM_YYYY(rs.getDate("emp_birthdate")));
            employee.setEmp_status(rs.getString("emp_status"));
            return employee;
        }
    }

    private static class EmployeeRowMapper1 implements ParameterizedRowMapper<Employee> {

        public Employee mapRow(ResultSet rs, int rownum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmp_id(rs.getString("emp_id"));
            employee.setEmp_password(rs.getString("emp_password"));
            employee.setEmp_fname(rs.getString("emp_fname"));
            employee.setEmp_lname(rs.getString("emp_lname"));
            employee.setGender(rs.getString("gender"));
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setDept_name(rs.getString("dept_name"));
            employee.setRole_id(rs.getInt("role_id"));
            employee.setRole_name(rs.getString("role_name"));
            employee.setDesi_id(rs.getInt("desi_id"));
            employee.setDesi_name(rs.getString("desi_name"));
            employee.setEmp_email(rs.getString("emp_email"));
            employee.setEmp_address(rs.getString("emp_address"));
            employee.setEmp_phone(rs.getString("emp_phone"));
            employee.setEmp_mobile(rs.getString("emp_mobile"));
            employee.setEmp_birthdate(getDD_MM_YYYY(rs.getDate("emp_birthdate")));
            return employee;
        }
    }

    private static class RoleRowMapper implements ParameterizedRowMapper<Role> {

        public Role mapRow(ResultSet rs, int rownum) throws SQLException {
            Role role = new Role();
            role.setRole_id(rs.getInt("role_id"));
            role.setRole_name(rs.getString("role_name"));
            return role;
        }
    }

    private static class DepartmentRowMapper implements ParameterizedRowMapper<Department> {

        public Department mapRow(ResultSet rs, int rownum) throws SQLException {
            Department department = new Department();
            department.setDept_id(rs.getInt("dept_id"));
            department.setDept_name(rs.getString("dept_name"));
            department.setStatus(rs.getString("status"));
            return department;
        }
    }

    private static class DesignationRowMapper implements ParameterizedRowMapper<Designation> {

        public Designation mapRow(ResultSet rs, int rownum) throws SQLException {
            Designation designation = new Designation();
            designation.setDesi_id(rs.getInt("desi_id"));
            designation.setDesi_name(rs.getString("desi_name"));
            designation.setStatus(rs.getString("status"));
            return designation;
        }
    }

    public String getEmpName(String emp_id) {
        try {
            String query = "SELECT EMP_FNAME FROM employee_master WHERE EMP_ID='" + emp_id + "'";
            Employee emp =
                    (Employee) jdbcTemplate.getJdbcOperations().queryForObject(query, new GetEmpNameMapper());
            String empname = emp.getEmp_fname();
            //System.out.println(empname);
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

    public boolean check(String emp_id){
        try{
            String emp = emp_id.trim();
            String query = "SELECT * FROM employee_transaction " +
                    "WHERE emp_id='" +emp+ "'";
            //System.out.println("query: "+query);
            List list = jdbcTemplate.getJdbcOperations().queryForList(query);
            //System.out.println("list: "+list);
            int size = list.size();
            //System.out.println("size: "+size);
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        }catch(DataAccessException dae){
            //System.out.println("dae in checkTrans: "+dae);
            return false;
        }catch(Exception e){
            //System.out.println("check() in exception: "+e);
            return false;
        }
    }

    public boolean deleteUser(String empid) {
        try {
            String zero = "0";
            String deleteTrans = "DELETE FROM employee_transaction " +
                    "WHERE emp_id ='" + empid + "'";
            String deleteUser = "DELETE FROM employee_master " +
                    "WHERE emp_id ='" + empid + "'";
            String updateUser = "UPDATE employee_transaction " +
                    "SET proxy_empid='" + zero + "' WHERE proxy_empid='" + empid + "'";

            jdbcTemplate.getJdbcOperations().execute(deleteTrans);
            jdbcTemplate.getJdbcOperations().execute(updateUser);
            jdbcTemplate.getJdbcOperations().execute(deleteUser);
            return true;
        } catch (Exception e) {
            //System.out.println("Error in delete user: " + e);
            return false;
        }
    }
}
