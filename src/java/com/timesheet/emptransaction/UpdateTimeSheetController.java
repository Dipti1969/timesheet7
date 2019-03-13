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

public class UpdateTimeSheetController extends SimpleFormController {

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

    public UpdateTimeSheetController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("updatetimesheet");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Object obj = super.formBackingObject(request);
        try {
            HttpSession session = request.getSession();
            session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
            session.setAttribute("proxy_emp_list", empTransactionService.getProxyEmpList());
            session.setAttribute("project_list", empTransactionService.getProjectList());
            Employee emp = (Employee) request.getSession().getAttribute("emp");
            String emp_id = emp.getEmp_id();

            session.setAttribute("time_right", empTransactionService.checkRight(emp_id));
            String id = request.getParameter("trans_id");
            long trans_id = Integer.parseInt(id);
           // System.out.println("trans_id: " + trans_id);
            EmpTransaction et = empTransactionService.getDetail(trans_id);
            session.setAttribute("startTimeEdit", et.getStart_time());
            session.setAttribute("endTimeEdit", et.getEnd_time());

            if (et.getWork_desc().startsWith("* ")) {
               // System.out.println("Late timesheetttttttttttttttttttttttttttttttttttttttt");
                et.setIsLate("yes");
                String s = et.getWork_desc();
                et.setWork_desc(s.replace("* ", ""));
            }

            request.getSession().setAttribute("trans_list", et);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        String msg = "Transaction is successfully updated";
        try {
           // System.out.println("Into OnSubmit().......");
            HttpSession session = request.getSession();
            Employee e = (Employee) session.getAttribute("emp");
            session.setAttribute("trans_empid", e.getEmp_id());
            String emp_id = (String) request.getSession().getAttribute("trans_empid");
            EmpTransaction et = (EmpTransaction) session.getAttribute("trans_list");
            session.setAttribute("trans_id", et.getTrans_id());
            Long trans_id1 = (Long) session.getAttribute("trans_id");
            long trans_id = (long) trans_id1;
           // System.out.println("trans_id in controller: " + trans_id);
            String trans_date = request.getParameter("trans_date");
            String assign_by1 = request.getParameter("assign_by_id");
            int assign_by = Integer.parseInt(assign_by1);
            String proxy_empid = request.getParameter("proxy_empid");
            String proj_id1 = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(proj_id1);
            String work_desc = request.getParameter("work_desc");
            String end_time = request.getParameter("end_time");
            String start_time = request.getParameter("start_time");
            String isLate = request.getParameter("isLate");

            if (isLate.equals("yes")) {
                work_desc = "* " + work_desc;
            }

            String time_right = empTransactionService.checkRight(emp_id);
            if (time_right.equals("F")) {
                empTransactionService.updateTimesheet(trans_id, assign_by, proxy_empid, proj_id, work_desc);
            } else {
                empTransactionService.updateTimesheetright(trans_id, start_time, end_time, assign_by, proxy_empid, proj_id, work_desc);
            }
            String chk = request.getParameter("chk");
          //  System.out.println("chk: " + chk);

            Map<String, Object> emp_trans_list = new HashMap<String, Object>();

            if (chk.equals("addrighttimesheet")) {



                List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
                session.setAttribute("trans_list", list);
                emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
                emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
                emp_trans_list.put("msg", msg);
                emp_trans_list.put("time_right", loginService.checkRight(emp_id));
                response.sendRedirect("/timesheet/addrighttimesheet.htm");
                return new ModelAndView("addrighttimesheet", "trans_list", emp_trans_list);
            } else if (chk.equals("emphome")) {

                List<EmpTransaction> list = empTransactionService.getAllTimeSheet();
                session.setAttribute("trans_list", list);
                emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
                emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
                emp_trans_list.put("msg", msg);
                emp_trans_list.put("time_right", loginService.checkRight(emp_id));
                //  response.sendRedirect("/timesheet/addrighttimesheet.htm");
                return new ModelAndView("emphome", "trans_list", emp_trans_list);
            } else {

                emp_trans_list.put("curr_trans", empTransactionService.getTransaction(emp_id));
                emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));

                String totalhrs = empTransactionService.getPrevDayTotalHours(emp_id);
                if (totalhrs == null) {
                    String hrs = "00:00:00";
                    emp_trans_list.put("totalhours", hrs);
                } else {
                    String hrs = totalhrs;
                    emp_trans_list.put("totalhours", hrs);
                }
                emp_trans_list.put("msg", msg);
                emp_trans_list.put("time_right", loginService.checkRight(emp_id));
                return new ModelAndView("managetimesheet", "trans_list", emp_trans_list);
            }
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
            HttpSession session = request.getSession();
            Employee e = (Employee) session.getAttribute("emp");
            session.setAttribute("trans_empid", e.getEmp_id());
            String emp_id = (String) request.getSession().getAttribute("trans_empid");
            String chk = request.getParameter("chk");
           // System.out.println("chk: " + chk);
            Map<String, Object> emp_trans_list = new HashMap<String, Object>();
            if (chk != null) {
                emp_trans_list.put("curr_trans", empTransactionService.getCurrentTransaction(emp_id));
                emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
//                String totalhrs = empTransactionService.getPrevDayTotalHours(emp_id);
//                if (totalhrs == null) {
//                    String hrs = "00:00:00";
//                    emp_trans_list.put("totalhours", hrs);
//                } else {
//                    String hrs = totalhrs;
//                    emp_trans_list.put("totalhours", hrs);
//                }
                return new ModelAndView("emphome", "trans_list", emp_trans_list);
            } else {
                emp_trans_list.put("curr_trans", empTransactionService.getTransaction(emp_id));
                emp_trans_list.put("totalhours", empTransactionService.getCurrentDayTotalHours(emp_id));
                String totalhrs = empTransactionService.getPrevDayTotalHours(emp_id);
                if (totalhrs == null) {
                    String hrs = "00:00:00";
                    emp_trans_list.put("totalhours", hrs);
                } else {
                    String hrs = totalhrs;
                    emp_trans_list.put("totalhours", hrs);
                }
                emp_trans_list.put("time_right", loginService.checkRight(emp_id));
                return new ModelAndView("managetimesheet", "trans_list", emp_trans_list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
