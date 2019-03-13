/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.registration;

import com.timesheet.bean.Employee;
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
public class viewUserController extends SimpleFormController {

    private RegistrationService registrationService;

    public viewUserController() {
        LoggerUtils.info("In Class viewUserController.java --> viewUserController() Constructor :: Enter");
        setCommandClass(Employee.class);
        setCommandName("viewemployee");
        LoggerUtils.info("In Class viewUserController.java --> viewUserController() Constructor :: Exit");
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
            LoggerUtils.info("In Class viewUserController.java --> handleRequest() Method :: Enter");
            Map<String, Object> myModel = new HashMap<String, Object>();
            String emp_id = request.getParameter("emp_id");
            LoggerUtils.info("The Id is===" + emp_id);
            Employee employee = (Employee) registrationService.getByPk(emp_id);
            request.setAttribute("VIEW_EMPLOYEE", employee);
            LoggerUtils.info("In Class viewUserController.java  -->  handleRequest() MEthod :: Exit... " + myModel);
            return new ModelAndView("viewuser", "view", employee);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
