package com.controller;

import com.timesheet.bean.Employee;
import com.timesheet.emptransaction.EmpTransactionService;
import com.timesheet.login.LoginService;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class empHomeController extends SimpleFormController {

    private LoginService loginService;
    private EmpTransactionService empTransactionService;

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String XPath = request.getServletPath();
        try {
            HttpSession session = request.getSession();
            Employee employee = (Employee) request.getSession().getAttribute("emp");
            String emp_id = employee.getEmp_id();
            String stat = loginService.getStatus(emp_id);
            session.setAttribute("et", stat);            
            Map<String, Object> emp_trans_list = new HashMap<String, Object>();
            emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
            emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
            emp_trans_list.put("time_right", loginService.checkRight(emp_id));
            return new ModelAndView("emphome", "trans_list", emp_trans_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = handleRequest(request, response);
        return mav;
    }
}
