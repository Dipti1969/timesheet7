package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DeleteTransactionController extends SimpleFormController {

    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public DeleteTransactionController() {
        //Initialize controller properties here or
        //in the Web Application Context
        //setCommandClass(EmpTransaction.class);
        //setCommandName("deletetrans");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("trans_id");
        long trans_id = Integer.parseInt(id);
        String emp_id = request.getParameter("empid");
       // System.out.println("emp_id from req: "+emp_id);
        String empname = timeSheetService.getEmpName(emp_id);
      //  System.out.println("empname: "+empname);
        timeSheetService.deleteTransaction(trans_id);
        String msg = "Transaction is successfully deleted";
        Map<String, Object> trans_list = new HashMap<String, Object>();
        trans_list.put("monthtransaction", timeSheetService.getMonthTransaction(emp_id));
        trans_list.put("dur", timeSheetService.getAllTotalDuration(emp_id));
        trans_list.put("empname", empname);
        trans_list.put("msg", msg);
        return new ModelAndView("viewtimesheet", "monthlist", trans_list);
    }
}
