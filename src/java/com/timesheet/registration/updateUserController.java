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
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class updateUserController extends SimpleFormController {

    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public updateUserController() {
        LoggerUtils.info("In Class updateUserController.java --> updateUserController() Constructor :: Enter");
        setCommandClass(Employee.class);
        setCommandName("editemployee");
        LoggerUtils.info("In Class updateUserController.java --> updateUserController() Constructor :: Exit");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        LoggerUtils.info("In Class updateUserController.java --> formBackingObject() Method :: Enter");
        try {
            Object object = super.formBackingObject(request);
            Employee employee = new Employee();
            HttpSession session = request.getSession();
            session.setAttribute("DEPARTMENT_LIST", registrationService.getByDepartment());
            session.setAttribute("ROLE_LIST", registrationService.getByRole());
            session.setAttribute("DESIGNATION_LIST", registrationService.getByDesignation());
            String emp_id = request.getParameter("emp_id");
            //System.out.println("The Employee Id from formBackingObject() is==" + emp_id);
            employee = registrationService.getByPk(emp_id);
            session.setAttribute("UPDATE_EMPLOYEE", employee);
            LoggerUtils.info("In Class updateUserController.java --> formBackingObject() Method :: Exit");
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
            Employee employee = (Employee) command;
            LoggerUtils.info("In Class updateUserController.java --> onSubmit() Method :: Enter");
            String emp_id = request.getParameter("emp_id");
            //System.out.println("The Id is=========" + emp_id);
            String emp_fname = request.getParameter("emp_fname");
             String fname = request.getParameter("fname");
            String emp_lname = request.getParameter("emp_lname");
            String gender = request.getParameter("gender");
            String dept_id = request.getParameter("dept_id");
            int dept_id1 = Integer.parseInt(dept_id);
            String role = request.getParameter("role_id");
            int roleid = Integer.parseInt(role);
            String desi_id = request.getParameter("desi_id");
            int desi_id1 = Integer.parseInt(desi_id);
            String time_right = request.getParameter("time_right");
            if (time_right==null) {
                employee.setTime_right("F");
                time_right="F";
            }
            String emp_email = request.getParameter("emp_email");
            String emp_address = request.getParameter("emp_address");
            String emp_phone = request.getParameter("emp_phone");
            String emp_mobile = request.getParameter("emp_mobile");
            String emp_birthdate = request.getParameter("emp_birthdate");
            String emp_bdate = registrationService.Insert_fmt_date(emp_birthdate);
            registrationService.update(emp_id, emp_fname,fname,emp_lname, gender, dept_id1,roleid, desi_id1,time_right, emp_email, emp_address, emp_phone, emp_mobile, emp_bdate);
            LoggerUtils.info("In Class updateUserController.java --> onSubmit() :: After Update User");
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("employees", registrationService.select());
            String msg = emp_id + " is updated successfully";
            myModel.put("msg", msg);
            LoggerUtils.info("In Class updateUserController.java --> onSubmit() Method :: Exit");
            return new ModelAndView("manageuser", "e", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            LoggerUtils.info("In Class updateUserControler.java -->  handleInvalidsubmit() Method :: Enter");
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("employees", registrationService.select());
            LoggerUtils.info("In Class updateUserControler.java --> handleInvalidsubmit() Method :: Exit");
            return new ModelAndView("manageuser", "e", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
