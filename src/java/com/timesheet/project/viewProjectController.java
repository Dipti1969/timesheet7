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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class viewProjectController extends SimpleFormController {

    private ProjectService projectService;

    public viewProjectController() {
        LoggerUtils.info("In Class viewProjectController.java --> viewProjectController() Constructor :: Enter");
        setCommandClass(Project.class);
        setCommandName("viewproject");
        LoggerUtils.info("In Class viewProjectController.java --> viewProjectController() Constructor :: Exit");
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
            LoggerUtils.info("In Class viewProjectController.java --> handleRequest() Method :: Enter");
            Map<String, Object> myModel = new HashMap<String, Object>();
            String id = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(id);
            Project project = (Project) projectService.getProject(proj_id);
            request.setAttribute("VIEW_PROJECT", project);
            return new ModelAndView("viewproject", "view", project);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
