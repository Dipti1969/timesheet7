/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.timesheet.bean.EmpTransaction
 *  com.timesheet.bean.Employee
 *  com.timesheet.login.LoginService
 *  com.timesheet.login.LoginServiceImpl$CheckUserMapper
 *  com.timesheet.login.LoginServiceImpl$EmailRowMapper
 *  com.timesheet.login.LoginServiceImpl$EmailRowMapper1
 *  com.timesheet.login.LoginServiceImpl$GetStatusMapper
 *  com.timesheet.login.LoginServiceImpl$passRowMapper
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcTemplate
 */
package com.timesheet.login;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginService;
import com.timesheet.login.LoginServiceImpl;
import java.io.PrintStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import sun.misc.BASE64Encoder;

public class LoginServiceImpl
extends HttpServlet
implements LoginService {
    HttpServletRequest request;
    protected SimpleJdbcTemplate jdbcTemplate = null;
    private static final char[] PASSWORD = "checkpassword".toCharArray();
    private static final byte[] SALT = new byte[]{-34, 51, 16, 18, -34, 51, 16, 18};

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public SimpleJdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Employee checkUser(String username, String password) {
        try {
            Employee emp = new Employee();
            String encPass = null;
            try {
                encPass = LoginServiceImpl.encrypt(password);
            }
            catch (Exception ex) {
                System.out.println("Encryption of password got error......");
            }
            String query = "select emp_id,emp_password, emp_fname, emp_lname, dept_id, role_id, emp_email,emp_status,time_right from employee_master where emp_id='" + username + "' and emp_password= '" + encPass + "' ";
            System.out.println(">>>>>>>>>>>>" + query);
            emp = (Employee)this.jdbcTemplate.getJdbcOperations().queryForObject(query, (RowMapper)new CheckUserMapper(null));
            return emp;
        }
        catch (DataAccessException dae) {
            System.out.println("User checking exception generated....");
            return null;
        }
    }

    private static String encrypt(String property) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(1, (Key)key, new PBEParameterSpec(SALT, 20));
        return LoginServiceImpl.base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    public String checkRight(String username) {
        try {
            String query = "select time_right as t from employee_master where emp_id='" + username + "'";
            List time_right = this.jdbcTemplate.getJdbcOperations().queryForList(query);
            Object o = time_right.get(0);
            return o.toString().substring(3, 4);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getEmpRole(String username) {
        try {
            Employee emp = new Employee();
            String query = "select role_id from employee_master where emp_id='" + username + "'";
            int roleid = this.jdbcTemplate.getJdbcOperations().queryForInt(query);
            return roleid;
        }
        catch (DataAccessException dae) {
            dae.printStackTrace();
            return 0;
        }
    }

    public String getpassword(String emp_id) {
        try {
            String query = "SELECT EMP_PASSWORD FROM employee_master WHERE EMP_ID='" + emp_id + "'";
            Employee employee = (Employee)this.jdbcTemplate.getJdbcOperations().queryForObject(query, (RowMapper)new passRowMapper(null));
            String oldpass = employee.getEmp_password().toString();
            return oldpass;
        }
        catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public boolean changepassword(String emp_id, String newpassword) {
        try {
            String changepass = "update employee_master set emp_password='" + newpassword + "' where emp_id='" + emp_id + "'";
            this.jdbcTemplate.getJdbcOperations().update(changepass);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getStatus(String emp_id) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            TimeZone tz = TimeZone.getTimeZone("IST");
            sdf.setTimeZone(tz);
            String dt = sdf.format(new Date());
            EmpTransaction et = new EmpTransaction();
            String query = "select distinct(status) from employee_transaction where emp_id='" + emp_id + "' and trans_date='" + dt + "' order by status limit 1";
            et = (EmpTransaction)this.jdbcTemplate.queryForObject(query, (ParameterizedRowMapper)new GetStatusMapper(null), new Object[0]);
            String status = et.getStatus();
            return status;
        }
        catch (DataAccessException dae) {
            return null;
        }
    }

    public String checkEmailID(String emailID) {
        try {
            String query = "select emp_email from employee_master where emp_email='" + emailID + "'";
            Employee e = (Employee)this.jdbcTemplate.getJdbcOperations().queryForObject(query, (RowMapper)new EmailRowMapper(null));
            String email = e.getEmp_email();
            return email;
        }
        catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public Employee getUserPassword(String emp_email) {
        try {
            String query = "SELECT emp_id,emp_password FROM employee_master WHERE emp_email='" + emp_email + "'";
            Employee e = (Employee)this.jdbcTemplate.getJdbcOperations().queryForObject(query, (RowMapper)new EmailRowMapper1(null));
            return e;
        }
        catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    static class 1 {
    }

}