/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.registration;

import com.timesheet.bean.Employee;
import com.timesheet.util.LoggerUtils;
import com.timesheet.util.SecurityUtils;
import com.timesheet.util.TimeSheetConstants;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/** 
 *
 * @author Admin
 */
public class manageUserController extends SimpleFormController {

    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoggerUtils.info("In Class manageUserController.java --> handleRequest() Method :: Enter");
        String XPath = request.getServletPath();
        //System.out.println("XPath====" + XPath);
        try {
            Employee employee = (Employee) request.getSession().getAttribute("emp");
            if (!SecurityUtils.isAuthorizedUser(employee, TimeSheetConstants.ADMIN_USER_ROLEID)) {
                //System.out.println("Into The SecurityFailed Page.....");
                return new ModelAndView("securityfailed");
            } else {
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("employees", registrationService.select());
                LoggerUtils.info("In Class manageUserController.java --> handleRequest() Method :: Exit");
                return new ModelAndView("manageuser", "e", myModel);
            }
        }
        catch  (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    
}

}
   