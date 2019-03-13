package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ViewTimeSheetController extends SimpleFormController {

    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public ViewTimeSheetController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("viewtimesheet");

    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        Map<String, Object> trans_list = new HashMap<String, Object>();
        String type = request.getParameter("type") == null ? "withoutdate" : request.getParameter("type");
       // System.out.println("type in ViewTimeSheetController: "+type);
        HttpSession session = request.getSession();
        if (type.equals("withoutdate")) {
            String emp_id = request.getParameter("emp_id");
            String empname = timeSheetService.getEmpName(emp_id);
           
            session.setAttribute("emp_id",emp_id);
            session.setAttribute("empname",empname);
      
            String msg = "No Record Found for " + empname;
//            System.out.println("sjdskjlas");

            if (emp_id.equals("0")) {
                String msg1 = "Select Employee";
                return new ModelAndView("adminmanagetimesheet", "msg", msg1);
            } else {
                if (timeSheetService.getMonthTransaction(emp_id) != null) {
                    trans_list.put("emp_id", emp_id);
                    trans_list.put("monthtransaction", timeSheetService.getMonthTransaction(emp_id));
                    trans_list.put("dur", timeSheetService.getAllTotalDuration(emp_id));
                    trans_list.put("empname", empname);
                   //  System.out.println("haaaaaaa");
                    return new ModelAndView("viewtimesheet", "monthlist", trans_list);
                } else {
                    // System.out.println("naaaa");
                    return new ModelAndView("adminmanagetimesheet", "msg", msg);
                    //System.out.println("sjdskjlas");
                }
                   //System.out.println("sjdskjlas");
            }
        } else {
            String emp_id = (String) session.getAttribute("emp_id");
            String empname = (String) session.getAttribute("empname");
            String dt = request.getParameter("tr_date");
            String trans_date = timeSheetService.Insert_fmt_date(dt);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dat = sdf.parse(trans_date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE, dd-MMM-yyyy");
            String viewdate = sdf1.format(dat);
                        
            trans_list.put("viewdate", viewdate);
            trans_list.put("monthtransaction", timeSheetService.getDateTransaction(emp_id, trans_date));
            trans_list.put("dur", timeSheetService.getTotalDurationWithDate(emp_id, trans_date));
            return new ModelAndView("viewtimesheet", "monthlist", trans_list);

        }
    }
}
