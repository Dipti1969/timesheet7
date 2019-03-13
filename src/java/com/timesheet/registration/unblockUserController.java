/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.registration;

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
public class unblockUserController extends SimpleFormController {

    private RegistrationService registrationService;

    public unblockUserController() {
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            String emp_id = request.getParameter("emp_id");
            registrationService.unblockuser(emp_id);
            String name=registrationService.getEmpName(emp_id);
            String msg = name+" is unblocked";
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("employees", registrationService.select());
            myModel.put("msg",msg);
            return new ModelAndView("manageuser", "e", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
