/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.project;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/** 
 *
 * @author prodigy
 */
public class unblockProjectController extends SimpleFormController {

    private ProjectService projectService;

    public unblockProjectController() {
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            String pro_id = request.getParameter("proj_id");
            int id=Integer.parseInt(pro_id);
            projectService.unblockproject(id);
            String msg = "Project is unblocked";
            Map<String, Object> proj_list = new HashMap<String, Object>();
            proj_list.put("projects", projectService.getAllProject());
            proj_list.put("msg", msg);
            return new ModelAndView("manageproject", "proj_list", proj_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
