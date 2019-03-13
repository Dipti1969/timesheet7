package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class EmpExcelController extends SimpleFormController {

    private AdminReportService adminReportService;

    public AdminReportService getAdminReportService() {
        return adminReportService;
    }

    public void setAdminReportService(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    public EmpExcelController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("excelreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("emp");
        String emp_id = e.getEmp_id();
        String type = request.getParameter("type");
        boolean flag = false;
        if (type.equals("catagory")) {
            String cat = request.getParameter("cat");
            String date1 = request.getParameter("start_date");
            String date2 = request.getParameter("end_date");
            String start_date = adminReportService.Insert_fmt_date(date1);
            String end_date = adminReportService.Insert_fmt_date(date2);

            //System.out.println("cat: " + cat);
            if (cat.equals("project")) {
                String proj = request.getParameter("proj_id");
                int proj_id = Integer.parseInt(proj);
                //System.out.println("proj_id in ExcelReportController: " + proj_id);
                flag = adminReportService.getProjectCatData(proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getProjectCatagory(proj_id, start_date, end_date);
                    model.put(ProjectCatagoryWiseExcelView.TRANS_LIST, list);
                    return new ModelAndView(new ProjectCatagoryWiseExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }
            } else if (cat.equals("assign")) {
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                flag = adminReportService.getAssignByCatData(assign_by_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getAssignByCatagory(assign_by_id, start_date, end_date);
                    model.put(AssignByCatagoryWiseExcelView.TRANS_LIST, list);
                    return new ModelAndView(new AssignByCatagoryWiseExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }
            } else if (cat.equals("report2")) {
               // String emp_id = request.getParameter("emp_id");
                flag = adminReportService.getReport2Data(emp_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport2(emp_id, start_date, end_date);
                    model.put(Report2ExcelView.TRANS_LIST, list);
                    return new ModelAndView(new Report2ExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else if (cat.equals("report5")) {
               // String emp_id = request.getParameter("emp_id");
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                flag = adminReportService.getReport5Data(emp_id, assign_by_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport5(emp_id, assign_by_id, start_date, end_date);
                    model.put(Report5ExcelView.TRANS_LIST, list);
                    return new ModelAndView(new Report5ExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else if (cat.equals("report6")) {
                //String emp_id = request.getParameter("emp_id");
                String proj = request.getParameter("proj_id");
                int proj_id = Integer.parseInt(proj);
                flag = adminReportService.getReport6Data(emp_id, proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport6(emp_id, proj_id, start_date, end_date);
                    model.put(Report6ExcelView.TRANS_LIST, list);
                    return new ModelAndView(new Report6ExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else if (cat.equals("report7")) {
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                String proj = request.getParameter("proj_id");
                int proj_id = Integer.parseInt(proj);
                flag = adminReportService.getReport7Data(assign_by_id, proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport7(assign_by_id, proj_id, start_date, end_date);
                    model.put(Report7ExcelView.TRANS_LIST, list);
                    return new ModelAndView(new Report7ExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else if (cat.equals("report8")) {
               // String emp_id = request.getParameter("emp_id");
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                String proj = request.getParameter("proj_id");
                int proj_id = Integer.parseInt(proj);
                flag = adminReportService.getReport8Data(emp_id, assign_by_id, proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport8(emp_id, assign_by_id, proj_id, start_date, end_date);
                    model.put(Report8ExcelView.TRANS_LIST, list);
                    return new ModelAndView(new Report8ExcelView(), model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
