package com.timesheet.project;

import com.timesheet.bean.Project;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteProjectController extends SimpleFormController {

    private ProjectService projectService;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public DeleteProjectController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Project.class);
        setCommandName("deleteproject");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pid = request.getParameter("id");
        int id = Integer.parseInt(pid);
        Map<String, Object> proj_list = new HashMap<String, Object>();
        boolean chk = projectService.checkTrans(id);
        //System.out.println("chk: " + chk);
        if (chk == false) {
            projectService.deleteproject(id);
            String del_msg = "successfully deleted";
            proj_list.put("msg",del_msg);
            proj_list.put("projects", projectService.getAllProject());
            return new ModelAndView("manageproject", "proj_list", proj_list);
        } else {
            String msg = "Project related transaction found so you can not delete";
            proj_list.put("msg",msg);
            proj_list.put("projects", projectService.getAllProject());
            return new ModelAndView("manageproject", "proj_list", proj_list);
        }
    }
}
