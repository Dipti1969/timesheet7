package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.emptransaction.EmpTransactionService;
import com.timesheet.login.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddAdminTimeSheetController extends SimpleFormController {

    private EmpTransactionService empTransactionService;
    private LoginService loginService;
    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

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

    public AddAdminTimeSheetController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("addadmintimesheet");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Object obj = super.formBackingObject(request);
        HttpSession session = request.getSession();
       // System.out.println("form backing object....................");
        session.setAttribute("emp_list", timeSheetService.getEmployees());
        session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
        session.setAttribute("proxy_emp_list", empTransactionService.getProxyEmpList());
        session.setAttribute("project_list", empTransactionService.getProjectList());
        
        return obj;
    }
   

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
             EmpTransaction et=(EmpTransaction)command;
            // System.out.println("on submit ....................");
            /*Employee e = (Employee) request.getSession().getAttribute("emp");
            request.getSession().setAttribute("trans_empid", e.getEmp_id());
            String emp_id = (String) request.getSession().getAttribute("trans_empid");*/
           /* String emp_id=request.getParameter("emp_id");
            String trans_date=request.getParameter("trans_date");
            String start_time=request.getParameter("start_time");
            String end_time=request.getParameter("end_time");
            String assign_by1 = request.getParameter("assign_by_id");
            int assign_by = Integer.parseInt(assign_by1);
            String proxy_empid = request.getParameter("proxy_empid");
            String proj_id1 = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(proj_id1);
            String work_desc = request.getParameter("work_desc");*/
            
            timeSheetService.insertTimeSheet(et);
            String msg="Transaction have been successfully added";
            return new ModelAndView("adminmanagetimesheet","msg",msg);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
           // System.out.println("invalid submit.......................");
            return new ModelAndView("adminmagaetimesheet");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
