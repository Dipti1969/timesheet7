package com.timesheet.login;

import com.timesheet.bean.Employee;
import com.timesheet.emptransaction.EmpTransactionService;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController {

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

    public LoginController() {
        setCommandClass(Employee.class);
        setCommandName("login");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        
        String XPath = request.getServletPath();
        try {
            Employee emp = (Employee) command;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String msg = "Invalid UserName/Password";
            Employee e = loginService.checkUser(username, password);
            if (e != null) {
                 System.out.println("***************************************");
                System.out.println("log in..........................");
                String emp_status = e.getEmp_status();
                if (emp_status.equals("T")) {
                    HttpSession session = request.getSession(false);
                    if (session == null) {
                        session = request.getSession();
                    }
                    session.setAttribute("emp", e);                   
                    if (loginService.getEmpRole(username) == 1) {
                        return new ModelAndView("adminhome");
                    } else if (loginService.getEmpRole(username) == 2) {
                        String stat = loginService.getStatus(username);
                        session.setAttribute("et", stat);
                        Map<String, Object> emp_trans_list = new HashMap<String, Object>();
                        emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(username));
                        emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(username));
                        emp_trans_list.put("time_right", loginService.checkRight(username));
                      
                        return new ModelAndView("empindex", "trans_list", emp_trans_list);

                    } else {
                       
                        return new ModelAndView("receptionisthome");
                    }
                    
                } else {
                    String blockmsg = "User Is Blocked";
                    return new ModelAndView("index", "blockmsg", blockmsg);
                }
            } else {
               
                return new ModelAndView("index", "msg", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            return super.formBackingObject(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    @Override
//    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
//       return showNewForm(request, response);
//    }
}
