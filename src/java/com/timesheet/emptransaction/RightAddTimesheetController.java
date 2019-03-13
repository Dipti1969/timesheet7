/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.emptransaction;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginController;
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

/** 
 *
 * @author prodigy
 */
public class RightAddTimesheetController extends SimpleFormController {

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

    public RightAddTimesheetController() {

        setCommandClass(EmpTransaction.class);
        setCommandName("addrighttimesheet");

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Object obj = super.formBackingObject(request);
        HttpSession session = request.getSession();
        session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
        session.setAttribute("proxy_emp_list", empTransactionService.getProxyEmpList());
        session.setAttribute("project_list", empTransactionService.getProjectList());
        List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
        session.setAttribute("trans_list",list);
        return obj;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            HttpSession session = request.getSession();
            Employee e = (Employee) request.getSession().getAttribute("emp");
            String trans_date = request.getParameter("trans_date");
           // System.out.println("------------>>>>"+trans_date);
            String emp_id=request.getParameter("emp_id");
            String start_time = request.getParameter("start_time");
            String end_time = request.getParameter("end_time");            
            String assign_by1 = request.getParameter("assign_by_id");
            int assign_by = Integer.parseInt(assign_by1);
            String proxy_empid = request.getParameter("proxy_empid");
            String proj_id1 = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(proj_id1);
            String work_desc = request.getParameter("work_desc");
           // System.out.println("hi" +work_desc);
            empTransactionService.insertTransaction(emp_id, start_time, end_time, assign_by, proxy_empid, proj_id, work_desc,trans_date);
            String msg = "Transaction have been successfully added";
            String stat = loginService.getStatus(emp_id);
            request.getSession().setAttribute("et", stat);
            List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
            Map<String, Object> emp_trans_list = new HashMap<String, Object>();
            emp_trans_list.put("msg", msg);
            emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
            emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
            emp_trans_list.put("time_right", loginService.checkRight(emp_id));
            emp_trans_list.put("trans_list", list);
            session.setAttribute("trans_list",list);
            response.sendRedirect("/timesheet/addrighttimesheet.htm");
            return new ModelAndView("addrighttimesheet", "trans_list", emp_trans_list);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String emp_id = (String) request.getSession().getAttribute("trans_empid");
            String stat = loginService.getStatus(emp_id);
            request.getSession().setAttribute("et", stat);
            Map<String, Object> emp_trans_list = new HashMap<String, Object>();
            emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
            emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
            List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
            emp_trans_list.put("trans_list", list);
            
            return new ModelAndView("addrighttimesheet", "trans_list", emp_trans_list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
