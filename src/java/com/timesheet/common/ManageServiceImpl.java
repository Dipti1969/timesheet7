/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.common;

import com.timesheet.bean.AssignBy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author prodigy
 */
public class ManageServiceImpl extends HttpServlet implements ManageService {

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

    public void add(AssignBy assignBy) {
        try {
            String insert = "INSERT INTO assign_by_master (assign_by_name) VALUES(?)";
           // System.out.println("ManageServiceImpl class into add() method is called :: The query is=" + insert);
            Object[] obj = new Object[]{assignBy.getAssign_by_name()};
            jdbcTemplate.update(insert, obj);
        } catch (DataAccessException dae) {
            dae.printStackTrace();

        }

    }

    public boolean update(int assignbyid, String assignbyname) {
        try {
            String update = "update assign_by_master set assign_by_name='" + assignbyname + "' where assign_by_id=" + assignbyid + "";
            jdbcTemplate.getJdbcOperations().update(update);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public AssignBy getById(int id) {
        try {
            AssignBy assignBy = new AssignBy();
            String selectbypk = "SELECT * FROM assign_by_master  WHERE assign_by_id = " + id + "";
            assignBy = (AssignBy) jdbcTemplate.queryForObject(selectbypk, new AssignRowMapper());
            return assignBy;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public List<AssignBy> select() {
        String select = "SELECT * FROM assign_by_master";
      //  System.out.println("ManageServiceImpl class into select() method is called :: The query is=" + select);
      //  System.out.println("query is created......" + select);
        try {
            List<AssignBy> assignBy = jdbcTemplate.query(select, new AssignRowMapper());
           // System.out.println("After The end Of The Calling jdbcTemplate" + assignBy);
            return assignBy;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try {
           // System.out.println("id in seervice: " + id);
//            String deleteTrans = "DELETE FROM employee_transaction " +
//                    "WHERE assign_by='" + id + "'";
            String deleteAssign = "DELETE FROM assign_by_master " +
                    "WHERE assign_by_id='" + id + "'";
//            jdbcTemplate.getJdbcOperations().execute(deleteTrans);
//            System.out.println("delteTras");
            jdbcTemplate.getJdbcOperations().execute(deleteAssign);
           // System.out.println("delteAssign");
            return true;
        } catch (Exception e) {
           // System.out.println("errror: " + e);
            return false;
        }
    }

    private static class AssignRowMapper implements ParameterizedRowMapper<AssignBy> {

        public AssignBy mapRow(ResultSet rs, int rownum) throws SQLException {
            AssignBy assignBy = new AssignBy();
            assignBy.setAssign_by_id(rs.getInt("assign_by_id"));
            assignBy.setAssign_by_name(rs.getString("assign_by_name"));
            assignBy.setStatus(rs.getString("status"));
            return assignBy;
        }
    }

    public boolean checkTrans(int assignid) {
        try {
            String query = "SELECT * FROM employee_transaction " +
                    "WHERE assign_by='" + assignid + "'";

            List list = jdbcTemplate.getJdbcOperations().queryForList(query);
          //  System.out.println("list: " + list);
            int size = list.size();
           // System.out.println("size: " + size);
            if (size > 0) {
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException dae) {
          //  System.out.println("dae in checkTrans: " + dae);
            return false;
        }
    }

    public boolean blockAssign(int assignid) {
        try {
            String statusF = "F";
            String block = "UPDATE assign_by_master SET status='" + statusF + "' WHERE assign_by_id='" + assignid + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean unblockAssign(int assignid) {
        try {
            String statusT = "T";
            String block = "UPDATE assign_by_master SET status='" + statusT + "' WHERE assign_by_id='" + assignid + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }
}
