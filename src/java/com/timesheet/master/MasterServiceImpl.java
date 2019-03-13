package com.timesheet.master;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class MasterServiceImpl implements MasterService {

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

    public void add(Department dept) {
        try {
            String insert = "INSERT INTO department_master (dept_name) VALUES(?)";
            //System.out.println("ManageServiceImpl class into add() method is called :: The query is=" + insert);
            Object[] obj = new Object[]{dept.getDept_name()};
            jdbcTemplate.update(insert, obj);
        } catch (Exception dae) {
            //System.out.println("error in add dept: "+dae);
        }
    }

    public List<Department> select() {
         String select = "SELECT * FROM department_master";
        //System.out.println("ManageServiceImpl class into select() method is called :: The query is=" + select);
        //System.out.println("query is created......" + select);
        try {
            List<Department> department = jdbcTemplate.query(select, new DepartmentRowMapper());
            return department;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public Department getById(int deptid) {
        try {
            Department department = new Department();
            String selectbypk = "SELECT * FROM department_master  WHERE dept_id = " + deptid + "";
            department = (Department) jdbcTemplate.queryForObject(selectbypk, new DepartmentRowMapper());
            return department;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public boolean update(int deptid, String dept_name) {
         try {
            String update = "update department_master set dept_name='" + dept_name + "' where dept_id=" + deptid + "";
            jdbcTemplate.getJdbcOperations().update(update);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean delete(int deptid) {
         try {
            //System.out.println("id in seervice: "+deptid);
            String deletedept = "DELETE FROM department_master " +
                    "WHERE dept_id='" + deptid + "'";
            jdbcTemplate.getJdbcOperations().execute(deletedept);
            return true;
        } catch (Exception e) {
            //System.out.println("errror: "+e);
            return false;
        }
    }

    public boolean checkDept(int deptid) {
       try{
            String query = "SELECT * FROM employee_master " +
                    "WHERE dept_id='" +deptid+ "'";
            List list = jdbcTemplate.getJdbcOperations().queryForList(query);
            //System.out.println("list: "+list);
            int size = list.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        }catch(DataAccessException dae){
            //System.out.println("dae in checkTrans: "+dae);
            return false;
        }
    }

    public void adddesi(Designation desi) {
         try {
            String insert = "INSERT INTO designation_master (desi_name) VALUES(?)";
            Object[] obj = new Object[]{desi.getDesi_name()};
            jdbcTemplate.update(insert, obj);
        } catch (Exception dae) {
            //System.out.println("error in add dept: "+dae);
        }
    }

    public List<Designation> selectdesi() {
        String select = "SELECT * FROM designation_master";
        //System.out.println("query is created......" + select);
        try {
            List<Designation> designation = jdbcTemplate.query(select, new DesignationRowMapper());
            return designation;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public Designation getByDesi(int desiid) {
        try {
            Designation designation=new Designation();
            String selectbypk = "SELECT * FROM designation_master  WHERE desi_id = " + desiid + "";
            designation = (Designation) jdbcTemplate.queryForObject(selectbypk, new DesignationRowMapper());
            return designation;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public boolean updateDesi(int desiid, String desi_name) {
         try {
            String update = "update designation_master set desi_name='" + desi_name + "' where desi_id=" + desiid + "";
            jdbcTemplate.getJdbcOperations().update(update);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean deleteDesi(int desiid) {
        try {
            //System.out.println("id in seervice: "+desiid);
            String deletedesi = "DELETE FROM designation_master " +
                    "WHERE desi_id='" + desiid + "'";
            jdbcTemplate.getJdbcOperations().execute(deletedesi);
            return true;
        } catch (Exception e) {
            //System.out.println("errror: "+e);
            return false;
        }
    }

    public boolean checkDesi(int desiid) {
        try{
            String query = "SELECT * FROM employee_master " +
                    "WHERE desi_id='" +desiid+ "'";
            List list = jdbcTemplate.getJdbcOperations().queryForList(query);
            //System.out.println("list: "+list);
            int size = list.size();
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        }catch(DataAccessException dae){
            //System.out.println("dae in checkTrans: "+dae);
            return false;
        }
    }
     private static class DepartmentRowMapper implements ParameterizedRowMapper<Department> {

        public Department mapRow(ResultSet rs, int rownum) throws SQLException {
            Department dept=new Department();
            dept.setDept_id(rs.getInt("dept_id"));
            dept.setDept_name(rs.getString("dept_name"));
            dept.setStatus(rs.getString("status"));
            return dept;
        }
    }

      private static class DesignationRowMapper implements ParameterizedRowMapper<Designation> {

        public Designation mapRow(ResultSet rs, int rownum) throws SQLException {
            Designation ds=new Designation();
            ds.setDesi_id(rs.getInt("desi_id"));
            ds.setDesi_name(rs.getString("desi_name"));
            ds.setStatus(rs.getString("status"));
            return ds;
        }
    }
      public boolean blockdept(int dept_id) {
        try {
            String statusF = "F";
            String block = "UPDATE department_master SET status='" + statusF + "' WHERE dept_id = '" + dept_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean unblockdept(int dept_id) {
        try {
            String statusT = "T";
            String block = "UPDATE department_master SET status='" + statusT + "' WHERE dept_id = '" + dept_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }
     public boolean blockdesi(int desi_id) {
        try {
            String statusF = "F";
            String block = "UPDATE designation_master SET status='" + statusF + "' WHERE desi_id = '" + desi_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean unblockdesi(int desi_id) {
        try {
            String statusT = "T";
            String block = "UPDATE designation_master SET status='" + statusT + "' WHERE desi_id = '" + desi_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }
}
