/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.project;

import com.timesheet.bean.Project;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class UpdateProjectController extends SimpleFormController {

    private ProjectService projectService;

    public UpdateProjectController() {
        setCommandClass(Project.class);
        setCommandName("updatepro");
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }


    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        LoggerUtils.info("In Class updateProjectController.java --> formBackingObject() Method :: Enter");
        try {
            Object object = super.formBackingObject(request);
            String proid = request.getParameter("proj_id");
            int pro_id = Integer.parseInt(proid);
            Project project = new Project();
            project = projectService.getProject(pro_id);
            HttpSession session = request.getSession();
            session.setAttribute("UPDATE_PROJECT", project);
            LoggerUtils.info("In Class updateProjectController.java --> formBackingObject() Method :: Exit");
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            
            String proid = request.getParameter("proj_id");
            int pro_id = Integer.parseInt(proid);
            String pro_name = request.getParameter("proj_name");
            String start_date = request.getParameter("start_date");
            String st_date = projectService.Insert_fmt_date(start_date);
            String end_date = request.getParameter("end_date");
            String ed_date = projectService.Insert_fmt_date(end_date);
            //System.out.println("ed_dt: "+ed_date);
            String target_date = request.getParameter("target_date");
            String tg_date = projectService.Insert_fmt_date(target_date);
            String pro_desc = request.getParameter("proj_desc");

            if(ed_date!=null){
                projectService.updateProject(pro_id, pro_name, st_date, ed_date, tg_date, pro_desc);
            }else{
                projectService.updateProject1(pro_id, pro_name, st_date,tg_date, pro_desc);
            }
            
            String msg = pro_name + " is updated successfully";
            Map<String, Object> proj_list = new HashMap<String, Object>();
            proj_list.put("msg", msg);
            proj_list.put("projects", projectService.getAllProject());
            return new ModelAndView("manageproject", "proj_list", proj_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> proj_list = new HashMap<String, Object>();
        proj_list.put("projects", projectService.getAllProject());
        return new ModelAndView("manageproject", "proj_list", proj_list);
    }
}
