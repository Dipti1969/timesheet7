package com.timesheet.emptransaction;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ViewAllTimeSheetController extends SimpleFormController {

    private EmpTransactionService empTransactionService;
    private LoginService loginService;

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

    public ViewAllTimeSheetController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("viewalltimesheet");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Employee employee = (Employee) request.getSession().getAttribute("emp");
        String emp_id = employee.getEmp_id();
        List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
        Map<String, Object> model = new HashMap<String, Object>();       
        if (list == null) {
            String msg = "No Record Found";
            model.put("msg1", msg);
            model.put("time_right", loginService.checkRight(emp_id));
            return new ModelAndView("emphome", "trans_list", model);
        } else {            
            model.put("trans_list", list);
            return new ModelAndView("viewalltimesheet", "trans_list", model);
        }

    }
}
