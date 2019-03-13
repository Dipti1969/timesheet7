package com.timesheet.registration;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class NextIDServiceImpl implements NextIDService {

    private SimpleJdbcTemplate jdbcTemplate;

    public SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    

    public String getNextID(String column) {
        try {
            //System.out.println("===== getNextID called....");
            long id = 0;
            String init = "";
            int len = 0;

            long lastid = jdbcTemplate.queryForLong("SELECT " + column + " FROM nextid");
            //System.out.println("lastid: " + lastid);

            id = lastid;

            id++;

            if (column.equalsIgnoreCase("EmpID")) {
                init = "EMP";
                len = 6;
            }
            return generate(init, id, len);

        } catch (Exception e) {
            //System.out.println("Exception in NextID: " + e);
            return null;
        }
    }

    private String generate(String init, long id, int len) {
        String newID = null;
        String tmp = "";
        for (int i = 0; i < len - ((String.valueOf(id).length()) + init.length()); i++) {
            tmp = tmp + "0";
        }
        newID = init + tmp + id;
        //System.out.println("newid = " + newID);
        return newID;
    }

    public void updateID(String column) {
        try
        {
        long lastid1 = jdbcTemplate.queryForLong("SELECT EmpID from nextid");
        //System.out.println("lastid1 = "+lastid1);
        jdbcTemplate.update("UPDATE nextid SET " + column + " = " + column + " + 1");
        }
        catch(DataAccessException dae)
        {
            dae.printStackTrace();
        }
    }
}
