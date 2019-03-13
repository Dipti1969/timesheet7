package com.timesheet.login;

import com.timesheet.bean.Employee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ResetPasswordController extends SimpleFormController {
    private LoginService loginService;

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    
    public ResetPasswordController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Employee.class);
        setCommandName("changepass");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pass = request.getParameter("emp_password");
        String empid = request.getParameter("emplist");
        //System.out.println("empid: "+empid);
        loginService.changepassword(empid, pass);
        String msg = "Password is reset for "+empid;
        return new ModelAndView("adminhome","msg",msg);
    }
}