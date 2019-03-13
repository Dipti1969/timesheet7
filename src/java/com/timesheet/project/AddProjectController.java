package com.timesheet.project;

import com.timesheet.bean.Project;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddProjectController extends SimpleFormController {

    private ProjectService projectService;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public AddProjectController() {
        setCommandClass(Project.class);
        setCommandName("addproject");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            Project proj = (Project) command;
            projectService.addProject(proj);
            String msg = "Project is Added Successfully";
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
        String XPath = request.getServletPath();

        try {
            Map<String, Object> proj_list = new HashMap<String, Object>();
            proj_list.put("projects", projectService.getAllProject());
            return new ModelAndView("manageproject", "proj_list", proj_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
