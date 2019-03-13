package com.timesheet.project;

import com.timesheet.bean.Project;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class ProjectServiceImpl implements ProjectService {

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

    public void addProject(Project proj) {
        try {
            String str1 = proj.getStart_date();
            String start_date = Insert_fmt_date(str1);

            String str3 = proj.getTarget_date();
            String target_date = Insert_fmt_date(str3);

            String query = "INSERT INTO project_master (proj_name,start_date,target_date,proj_desc) VALUES(?,?,?,?)";
            Object[] obj = new Object[]{proj.getProj_name(), start_date, target_date, proj.getProj_desc()};
            jdbcTemplate.getJdbcOperations().update(query, obj);
        } catch (DataAccessException dae) {
            dae.printStackTrace();
        }
    }

    public List<Project> getAllProject() {
        try {
            String query = "SELECT * FROM project_master";
            //System.out.println("query is created: " + query);
            List<Project> proj = jdbcTemplate.getJdbcOperations().query(query, new ProjectRowMapper());
            return proj;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public Project getProject(int proj_id) {
        try {
            String query = "SELECT * FROM project_master WHERE proj_id='" + proj_id + "'";
            //System.out.println("query is created in getProject: " + query);
            // employee = (Employee) jdbcTemplate.queryForObject(selectbypk, new EmployeeRowMapper1());
            Project proj = (Project) jdbcTemplate.getJdbcOperations().queryForObject(query, new ProjectRowMapper());
            return proj;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return null;
        }
    }

    public String Insert_fmt_date(String dat) {
        try {
            if (!dat.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                Date dt = sdf.parse(dat);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                String fmt_date = sdf1.format(dt);
                return fmt_date;
            }else{
                return null;
            }
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
            //System.out.println("Error in getDD_MM_YYYY: "+e);
            return null;
        }

    }

    private static class ProjectRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project p = new Project();
            p.setProj_id(rs.getInt("proj_id"));
            p.setProj_name(rs.getString("proj_name"));
            p.setStart_date(getDD_MM_YYYY(rs.getDate("start_date")));
            p.setEnd_date(getDD_MM_YYYY(rs.getDate("end_date")));
            p.setTarget_date(getDD_MM_YYYY(rs.getDate("target_date")));
            p.setProj_desc(rs.getString("proj_desc"));
            p.setStatus(rs.getString("status"));
            return p;
        }
    }

    public boolean updateProject(int proj_id, String proj_name, String start_date1, String end_date1, String target_date1, String proj_desc) {
        try {
            String update = "UPDATE project_master SET proj_name='" + proj_name + "',start_date='" + start_date1 + "', end_date='" + end_date1 + "', target_date='" + target_date1 + "', proj_desc='" + proj_desc + "' where proj_id=?";
            jdbcTemplate.update(update, new Object[]{proj_id});
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        } catch (Exception e) {
            //System.out.println("Error in update project: " + e);
            return false;
        }
    }
    
    public boolean updateProject1(int proj_id, String proj_name, String start_date1,String target_date1, String proj_desc) {
        try {
            String update = "UPDATE project_master SET proj_name='" + proj_name + "',start_date='" + start_date1 + "',target_date='" + target_date1 + "', proj_desc='" + proj_desc + "' where proj_id=?";
            jdbcTemplate.update(update, new Object[]{proj_id});
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        } catch (Exception e) {
            //System.out.println("Error in update project1: " + e);
            return false;
        }
    }
     public boolean deleteproject(int proj_id) {
        try {
            String query = "DELETE FROM project_master WHERE PROJ_ID='"+proj_id+"'";
            jdbcTemplate.getJdbcOperations().execute(query);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

     public boolean checkTrans(int proj_id){
        try{
            String query = "SELECT * FROM employee_transaction " +
                    "WHERE proj_id='" +proj_id+ "'";

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
     public boolean blockproject(int pro_id) {
        try {
            String statusF = "F";
            String block = "UPDATE project_master SET status='" + statusF + "' WHERE proj_id = '" + pro_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

    public boolean unblockproject(int pro_id) {
        try {
            String statusT = "T";
            String block = "UPDATE project_master SET status='" + statusT + "' WHERE proj_id = '" + pro_id + "'";
            jdbcTemplate.update(block);
            return true;
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return false;
        }
    }

}
