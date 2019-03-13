package com.timesheet.project;

import com.timesheet.bean.Project;
import java.util.List;

public interface ProjectService {

    public void addProject(Project proj);
    public List<Project> getAllProject();
    public Project getProject(int proj_id);
    public boolean updateProject(int proj_id, String proj_name, String start_date, String end_date, String target_date, String proj_desc);
    public String Insert_fmt_date(String dat);
    public boolean updateProject1(int proj_id, String proj_name, String start_date,String target_date, String proj_desc);
    public boolean deleteproject(int proj_id);
    public boolean checkTrans(int proj_id);
     public boolean blockproject(int proj_id);
    public boolean unblockproject(int proj_id);
}
