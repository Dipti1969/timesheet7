package com.timesheet.registration;

import com.timesheet.bean.Employee;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteUserController extends SimpleFormController {

    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public DeleteUserController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Employee.class);
        setCommandName("deleteuser");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String empid = request.getParameter("empid");

        Map<String, Object> myModel = new HashMap<String, Object>();
        registrationService.deleteUser(empid);
        myModel.put("employees", registrationService.select());
        String str = empid + " is successfully deleted";
        myModel.put("msg", str);
        return new ModelAndView("manageuser", "e", myModel);
    }
}


