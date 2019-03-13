package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.emptransaction.EmpTransactionService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class UpdateTransactionController extends SimpleFormController {

    private ManageTimeSheetService timeSheetService;
    private EmpTransactionService empTransactionService;

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public UpdateTransactionController() {
        //Initialize controller properties here or
        //in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("updatetransaction");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Object obj = super.formBackingObject(request);
        try {
            HttpSession session = request.getSession();
            session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
            session.setAttribute("proxy_emp_list", empTransactionService.getProxyEmpList());
            session.setAttribute("project_list", empTransactionService.getProjectList());
            String transid = request.getParameter("trans_id");
            long trans_id = Long.parseLong(transid);
            String id = request.getParameter("emp_id");
            EmpTransaction et = timeSheetService.getEmpTransaction(trans_id, id);

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
//          Employee e = (Employee) session.getAttribute("emp");
//          session.setAttribute("trans_empid", e.getEmp_id());
//          String emp_id = (String) request.getSession().getAttribute("trans_empid");
            EmpTransaction et = (EmpTransaction) session.getAttribute("trans_list");
           // System.out.println("et: " + et);
            String empid = et.getEmp_id();
            String empname = timeSheetService.getEmpName(empid);
           // System.out.println("et111: " + et.getEmp_id());

            String transid = request.getParameter("trans_id");
           // System.out.println("transid: " + transid);
            long trans_id = Integer.parseInt(transid);
           // System.out.println("trans_id: " + transid + " empid: " + empid);
            String emp_id = request.getParameter("emp_id");
            String trans_date = request.getParameter("trans_date");
            String start_time = request.getParameter("start_time");
            String end_time = request.getParameter("end_time");
            String assign_by1 = request.getParameter("assign_by_id");
            int assign_by = Integer.parseInt(assign_by1);
            String proxy_empid = request.getParameter("proxy_empid");
            String proj_id1 = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(proj_id1);
            String work_desc = request.getParameter("work_desc");
            String isLate = request.getParameter("isLate");
            if (isLate.equals("yes")) {
                work_desc = "* " + work_desc;
            }

            Map<String, Object> trans_list = new HashMap<String, Object>();
            timeSheetService.updateEmpTransaction(trans_id, trans_date, start_time, end_time, assign_by, proxy_empid, proj_id, work_desc);
            trans_list.put("msg", msg);
            trans_list.put("monthtransaction", timeSheetService.getMonthTransaction(emp_id));
            trans_list.put("dur", timeSheetService.getAllTotalDuration(emp_id));
            trans_list.put("empname", empname);
            return new ModelAndView("viewtimesheet", "monthlist", trans_list);

        } catch (Exception e) {
            return new ModelAndView("error");
        }
    }
}
