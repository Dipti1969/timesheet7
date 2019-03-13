package com.timesheet.adminmanagetimesheet;

import com.timesheet.emptransaction.*;
import com.timesheet.bean.EmpTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AdminEditTimeSheetController extends SimpleFormController {

    private EmpTransactionService empTransactionService;
  
    private ManageTimeSheetService manageTimeSheetService;

    public ManageTimeSheetService getManageTimeSheetService() {
        return manageTimeSheetService;
    }

    public void setManageTimeSheetService(ManageTimeSheetService manageTimeSheetService) {
        this.manageTimeSheetService = manageTimeSheetService;
    }

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

   
    public AdminEditTimeSheetController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("adminedittimesheet");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Object obj = super.formBackingObject(request);
        try {
           // System.out.println("FormBackingObject method called..........");
            HttpSession session = request.getSession();
            session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
            session.setAttribute("proxy_emp_list", empTransactionService.getProxyEmpList());
            session.setAttribute("project_list", empTransactionService.getProjectList());
            String id = request.getParameter("trans_id");
            int id1 = Integer.parseInt(id);
            session.setAttribute("transid", id1);
           // System.out.println("start_time"+request.getParameter("start_time"));
            session.setAttribute("start_time",request.getParameter("start_time"));
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
       // System.out.println("onSubmit() before try......");
        String XPath = request.getServletPath();
        try {
           // System.out.println("Into The Controller....");
            String id = request.getParameter("trans_id");
            int id1 = Integer.parseInt(id);
            String end_time = request.getParameter("end_time");
           // System.out.println("EndTime is==="+end_time);
            String assign_by1 = request.getParameter("assign_by_id");
            int assign_by = Integer.parseInt(assign_by1);
            String proxy_empid = request.getParameter("proxy_empid");
            String proj_id1 = request.getParameter("proj_id");
            int proj_id = Integer.parseInt(proj_id1);
            String work_desc = request.getParameter("work_desc");
            manageTimeSheetService.addTimeSheet(id1, end_time, assign_by, proxy_empid, proj_id, work_desc);
            String msg = "Transaction have been successfully Updated";
            return new ModelAndView("adminmanagetimesheet","msg",msg);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
