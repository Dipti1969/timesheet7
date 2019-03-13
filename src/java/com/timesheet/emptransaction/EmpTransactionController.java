package com.timesheet.emptransaction;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class EmpTransactionController extends SimpleFormController {

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

    public EmpTransactionController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("transaction");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            EmpTransaction et = new EmpTransaction();
            Employee e = (Employee) request.getSession().getAttribute("emp");
            request.getSession().setAttribute("trans_empid", e.getEmp_id());
            String emp_id = (String) request.getSession().getAttribute("trans_empid");
            String stat1 = loginService.getStatus(emp_id);
            if(stat1 == null || stat1.equals("T")){
                empTransactionService.addTransaction(emp_id);
            }
            String stat = loginService.getStatus(emp_id);
            request.getSession().setAttribute("et", stat);
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
        return new ModelAndView("emphome");
    }

}
