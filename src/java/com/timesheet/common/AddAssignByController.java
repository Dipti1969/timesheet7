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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class AddAssignByController extends SimpleFormController {

    private ManageService manageService;

    public ManageService getManageService() {
        return manageService;
    }

    public void setManageService(ManageService manageService) {
        this.manageService = manageService;
    }

    public AddAssignByController() {
        setCommandClass(AssignBy.class);
        setCommandName("addassign");
        setFormView("addassignby");

    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
       // System.out.println("Into The ShowDorm Method.....");
        Employee employee=(Employee)request.getSession().getAttribute("emp");
        if(!SecurityUtils.isAuthorizedUser(employee,TimeSheetConstants.ADMIN_USER_ROLEID))
        {
           // System.out.println("Into The SecurityFailed Page.....");
            return new ModelAndView("securityfailed");
        }
        else
        {
            return new ModelAndView("addassignby");
        }
    }

   /* @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        ModelAndView mav=new ModelAndView();
        AssignBy emp=(AssignBy)super.formBackingObject(request);
        Object object=super.formBackingObject(request);
        Employee employee=(Employee)request.getSession().getAttribute("emp");
        if(!SecurityUtils.isAuthorizedUser(employee,TimeSheetConstants.ADMIN_USER_ROLEID))
        {
            return mav.addObject("securityfailed",emp);
        }
        else
        {
            return object;
        }
    }*/


    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            AssignBy assignBy = (AssignBy) command;
            String assign_by = request.getParameter("assign_by_name");
            if (assign_by.equals("")) {
                String msg = "Please enter assign by name";
                return new ModelAndView("addassignby", "msg", msg);
            } else {
                String str = "AssignBy successfully added";
                manageService.add(assignBy);
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
