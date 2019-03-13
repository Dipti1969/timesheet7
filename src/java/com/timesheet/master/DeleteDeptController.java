package com.timesheet.master;

import com.timesheet.bean.Department;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteDeptController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }
    
    public DeleteDeptController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Department.class);
        setCommandName("deletedept");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String did = request.getParameter("id");
        int id = Integer.parseInt(did);
        Map<String, Object> myModel = new HashMap<String, Object>();
        boolean chk = masterService.checkDept(id);
        //System.out.println("chk: "+chk);
        if (chk == false) {
            masterService.delete(id);
            String del_msg = "Department successfully deleted";
            myModel.put("msg",del_msg);
            myModel.put("dept", masterService.select());
            return new ModelAndView("managedept", "d", myModel);
        } else {
            String msg = "Department related Data found so you can not delete";
            myModel.put("msg", msg);
            myModel.put("dept",masterService.select());
            return new ModelAndView("managedept", "d", myModel);
        }
    }
}
