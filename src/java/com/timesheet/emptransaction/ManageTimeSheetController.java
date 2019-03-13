/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.emptransaction;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginService;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/** 
 *
 * @author Administrator
 */
public class ManageTimeSheetController extends SimpleFormController {

    private EmpTransactionService empTransactionService;
    private LoginService loginService;

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public ManageTimeSheetController() {

        setCommandClass(EmpTransaction.class);
        setCommandName("managetimesheet");

    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            EmpTransaction et = new EmpTransaction();
            Employee e = (Employee) request.getSession().getAttribute("emp");
            request.getSession().setAttribute("trans_empid", e.getEmp_id());
            String emp_id = (String) request.getSession().getAttribute("trans_empid");
            Map<String, Object> emp_trans_list = new HashMap<String, Object>();
            emp_trans_list.put("curr_trans", empTransactionService.getTransaction(emp_id));
            String totalhrs = empTransactionService.getPrevDayTotalHours(emp_id);
            if (totalhrs == null) {
                String hrs = "00:00:00";
                emp_trans_list.put("totalhours", hrs);
            } else {
                String hrs = totalhrs;
                emp_trans_list.put("totalhours", hrs);
            }
            
            return new ModelAndView("managetimesheet", "trans_list", emp_trans_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("managetimesheet");
    }
}
