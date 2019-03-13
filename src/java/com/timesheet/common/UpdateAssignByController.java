package com.timesheet.common;

import com.timesheet.bean.AssignBy;
import com.timesheet.bean.Employee;
import com.timesheet.util.LoggerUtils;
import com.timesheet.util.SecurityUtils;
import com.timesheet.util.TimeSheetConstants;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class UpdateAssignByController extends SimpleFormController {

    private ManageService manageService;

    public ManageService getManageService() {
        return manageService;
    }

    public void setManageService(ManageService manageService) {
        this.manageService = manageService;
    }

    public UpdateAssignByController() {
        setCommandClass(AssignBy.class);
        setCommandName("updateassign");
        setFormView("updateassignby");
        

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        ModelAndView mav=new ModelAndView("securityfailed");
        AssignBy emp=(AssignBy)super.formBackingObject(request);
          Object object = super.formBackingObject(request);
         try {
          
            AssignBy assignBy=new AssignBy();
            String id=request.getParameter("assign_by_id");
           // System.out.println("ID===="+id);
            int assignbyid=Integer.parseInt(id);
            assignBy=manageService.getById(assignbyid);
            HttpSession session = request.getSession();
            session.setAttribute("UPDATE_ASSIGNBY",assignBy);
            return object;
         }
         catch(Exception e)
         {
             
           return null;
         }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        LoggerUtils.info("In Class AddAssignByController.java --> onSubmit() Method :: Enter");
        String XPath = request.getServletPath();
        try {
            AssignBy assignBy = (AssignBy) command;
            String assign_byid=request.getParameter("assign_by_id");
            int id=Integer.parseInt(assign_byid);
            String assign_by = request.getParameter("assign_by_name");
            if (assign_by.equals("")) {
                String msg = "Please enter assign by name";
                return new ModelAndView("updateassignby", "msg", msg);
            } else {
                manageService.update(id,assign_by);
                String str = "AssignBy successfully updated";
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("assign", manageService.select());
                myModel.put("msg",str);
                return new ModelAndView("manageassignby", "a", myModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }
}
