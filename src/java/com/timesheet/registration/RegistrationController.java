/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.registration;

import com.timesheet.bean.Employee;
import com.timesheet.login.EmailBean;
import com.timesheet.login.EmailService;
import com.timesheet.login.LoginService;
import com.timesheet.util.LoggerUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class RegistrationController extends SimpleFormController {

    private RegistrationService registrationService;
    private NextIDService nextIDService;
    private LoginService loginService;

    public NextIDService getNextIDService() {
        return nextIDService;
    }

    public void setNextIDService(NextIDService nextIDService) {
        this.nextIDService = nextIDService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public RegistrationController() {
        LoggerUtils.info("In Class RegistrationController.java --> RegistrationController() Constructor :: Enter");
        setCommandClass(Employee.class);
        setCommandName("addemployee");
        LoggerUtils.info("In Class RegistrationController.java --> RegistrationController() Constructor :: Exit");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        LoggerUtils.info("In Class RegistrationController.java --> formBackingObject() Method :: Enter");
        try {
            
            Object object = super.formBackingObject(request);
            HttpSession session = request.getSession();
            session.setAttribute("DEPARTMENT_LIST", registrationService.getByDepartment());
            session.setAttribute("ROLE_LIST", registrationService.getByRole());
            session.setAttribute("DESIGNATION_LIST", registrationService.getByDesignation());
            //session.setAttribute("EMP_ID", nextIDService.getNextID("EMPID"));
            LoggerUtils.info("In Class RegistrationController.java --> formBackingObject() Method :: Exit");
            return object;
         
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            LoggerUtils.info("In Class RegistrationController.java --> onSubmit() Method :: Enter");
            Employee employee = (Employee) command;
            String timeright=employee.getTime_right();
            if(timeright==null)
                employee.setTime_right("F");
            registrationService.add(employee);
            String recipient = employee.getEmp_email();
            String empid = employee.getEmp_id();
            String emp_password = loginService.getpassword(empid);
            
            
            LoggerUtils.info("Password is===" + emp_password);
          //  nextIDService.updateID("EmpID");
            EmailBean emailBean = new EmailBean();
            emailBean.setEmailSubject("Prodigy Infosoft TimeSheet Services");
            emailBean.setEmailDate(new Date());
            emailBean.setHTMLContents(true);
            emailBean.setFromAddress("aghera.renish@yahoo.in");
            emailBean.setToAddress(recipient);
            StringBuffer startMessage = new StringBuffer("");
            startMessage.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
            startMessage.append("<html><head><style type='text/css'>h2 {color:#520000}</style>");
            startMessage.append("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
            startMessage.append("</head><body>");
            startMessage.append("<h2>Welcome To ProdigyInfosoft TimeSheet Service</h2><br><br>");
            startMessage.append("<strong>Your login details are as follows:</strong><br><br>");
            startMessage.append("<table border='0' style='text-align: left; font-family: verdana;' cellpadding='2' cellspacing='2'>");
            startMessage.append("<tr>");
            startMessage.append("<td style='vertical-align:top;background:#F5F5F5;border:1px solid #520000;'>UserID  : " + empid + "</td></tr>");
            startMessage.append("<tr><td style='vertical-align:top;background:#F5F5F5;border:1px solid #520000;'>Password   : " + emp_password + "</td></tr></table>");
            startMessage.append("<br>");
            startMessage.append("<br><br>Thanks<br>TimeSheet Services<br>Prodigy Infosoft Pvt Ltd.</BODY></HTML>");
            emailBean.setEmailMesaage(startMessage.toString());
            EmailService emailService1 = new EmailService();
            emailService1.sendEmail(emailBean);
            LoggerUtils.info("In Class RegistrationController.java --> onSubmit() :: After Add User");
            String msg = "User Is Added Successfully";
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("msg", msg);
            myModel.put("employees", registrationService.select());
            LoggerUtils.info("In Class RegistrationController.java --> onSubmit() Method :: Exit");
            return new ModelAndView("manageuser", "e", myModel);
        } 
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            LoggerUtils.info("In Class RegistrationControler.java -->  handleInvalidsubmit() Method :: Enter");
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("employees", registrationService.select());
            LoggerUtils.info("In Class RegistrationController.java --> handleInvalidsubmit() Method :: Exit");
            return new ModelAndView("manageuser", "e", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
