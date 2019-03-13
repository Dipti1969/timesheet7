package com.timesheet.master;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteDesiController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }
    
    public DeleteDesiController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Designation.class);
        setCommandName("deletedesi");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String did = request.getParameter("id");
        int id = Integer.parseInt(did);
        Map<String, Object> myModel = new HashMap<String, Object>();
        boolean chk = masterService.checkDesi(id);
        //System.out.println("chk: "+chk);
        if (chk == false) {
            masterService.deleteDesi(id);
            String del_msg = "Designation successfully deleted";
            myModel.put("msg",del_msg);
            myModel.put("desi",masterService.selectdesi());
            return new ModelAndView("managedesi", "d", myModel);
        } else {
            String msg = "Designation related Data found so you can not delete";
            myModel.put("msg", msg);
            myModel.put("desi",masterService.selectdesi());
            return new ModelAndView("managedesi", "d", myModel);
        }
    }
}
