package com.timesheet.common;

import com.timesheet.bean.AssignBy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteAssignByController extends SimpleFormController {

    private ManageService manageService;

    public ManageService getManageService() {
        return manageService;
    }

    public void setManageService(ManageService manageService) {
        this.manageService = manageService;
    }

    public DeleteAssignByController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(AssignBy.class);
        setCommandName("deleteassignby");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aid = request.getParameter("id");
        int id = Integer.parseInt(aid);
      //  System.out.println("assign_by: " + id);
        Map<String, Object> myModel = new HashMap<String, Object>();
        boolean chk = manageService.checkTrans(id);
      //  System.out.println("chk: "+chk);
        if (chk == false) {
            manageService.delete(id);
            String del_msg = "successfully deleted";
            myModel.put("msg",del_msg);
            myModel.put("assign", manageService.select());
            return new ModelAndView("manageassignby", "a", myModel);
        } else {
            String msg = "AssignBy related transaction found so you can not delete";
            myModel.put("msg", msg);
            myModel.put("assign", manageService.select());
            return new ModelAndView("manageassignby", "a", myModel);
        }
    }
}
