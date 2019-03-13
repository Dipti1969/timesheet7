package com.timesheet.emptransaction;

import com.timesheet.adminmanagetimesheet.ManageTimeSheetService;
import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.login.LoginService;
import com.timesheet.report.AdminReportService;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class CheckTotalHoursController extends SimpleFormController {

    private EmpTransactionService empTransactionService;
    private LoginService loginService;
    private AdminReportService adminReportService;
    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public AdminReportService getAdminReportService() {
        return adminReportService;
    }

    public void setAdminReportService(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
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

    public CheckTotalHoursController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("checktotalhours");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("YEAR_LIST", adminReportService.getYears());
        return super.formBackingObject(request);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Employee e = (Employee) request.getSession().getAttribute("emp");
        String empid = e.getEmp_id();
        String month = request.getParameter("monthlist");
        String year = request.getParameter("yearlist");
        List<EmpTransaction> trans_list = empTransactionService.getCheckTotalHours(month, year, empid);
        Map<String, Object> emp_trans_list = new HashMap<String, Object>();
        emp_trans_list.put("monthtransaction", trans_list);
        emp_trans_list.put("totalhours", timeSheetService.getMonthYearHours(empid, month, year));

        int month_no = Integer.parseInt(month);
       // System.out.println("month_no: "+month_no);
        String month_name="";
        switch (month_no) {
            case 1:
                month_name = "January";
                break;
            case 2:
                month_name = "February";
                break;
            case 3:
                month_name = "March";
                break;
            case 4:
                month_name = "April";
                break;
            case 5:
                month_name = "May";
                break;
            case 6:
                month_name = "June";
                break;
            case 7:
                month_name = "July";
                break;
            case 8:
                month_name = "August";
                break;
            case 9:
                month_name = "September";
                break;
            case 10:
                month_name = "October";
                break;
            case 11:
                month_name = "November";
                break;
            case 12:
                month_name = "December";
                break;
            default:
                month_name="";
                break;
        }
        emp_trans_list.put("month", month_name);
        emp_trans_list.put("year", year);
        return new ModelAndView("checkhours", "month_trans", emp_trans_list);

    }
}
