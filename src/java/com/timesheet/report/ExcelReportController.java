package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ExcelReportController extends SimpleFormController {

    private AdminReportService adminReportService;

    public AdminReportService getAdminReportService() {
        return adminReportService;
    }

    public void setAdminReportService(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    public ExcelReportController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("excelreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String type = request.getParameter("type");
        boolean flag = false;

        if (type.equals("datewise")) {
            String dt = request.getParameter("datewise_txt");
            String date = adminReportService.Insert_fmt_date(dt);
            flag = adminReportService.getData(date);
            //System.out.println("Flag ===" + flag);
            if (flag == true) {

                List<EmpTransaction> list = adminReportService.getDateWise(date);
                model.put(DateWiseExcelView.WIDGET_LIST_KEY, list);
                return new ModelAndView(new DateWiseExcelView(), model);
            } else {
                String datewise_msg = " No Record Found date: " + date;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        } else if (type.equals("daily")) {
            String dt = request.getParameter("daily_txt");
            String date = adminReportService.Insert_fmt_date(dt);
            flag = adminReportService.getData(date);
            //System.out.println("Flag ===" + flag);

            if (flag == true) {

                List<EmpTransaction> list = adminReportService.getDateWise(date);
                List<Employee> empname = adminReportService.getTransEmpName(date);
                model.put(DailyReport.WIDGET_LIST_KEY, list);
                model.put("emplist", empname);
                //for(int i=0;i<)
                return new ModelAndView(new DailyReport(), model);
            } else {
                String datewise_msg = " No Record Found date: " + date;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        } else if (type.equals("customdate")) {
            String date1 = request.getParameter("customdate_txt1");
            String date2 = request.getParameter("customdate_txt2");
            String date3 = adminReportService.Insert_fmt_date(date1);
            String date4 = adminReportService.Insert_fmt_date(date2);
            flag = adminReportService.getCustomData(date3, date4);
            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getCustomDateWise(date3, date4);
                //System.out.println("List is====" + list);
                model.put(CustomDateWiseExcelView.TRANS_LIST, list);
                return new ModelAndView(new CustomDateWiseExcelView(), model);
            } else {
                String datewise_msg = " No Record Found For date: " + date1 + " & " + date2;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        } else if (type.equals("monthwise")) {
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            flag = adminReportService.getMonthData(month, year);
            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getMonthWise(month, year);
                model.put(MonthWiseExcelView.TRANS_LIST, list);
                return new ModelAndView(new MonthWiseExcelView(), model);
            } else {
                String datewise_msg = " No Record Found For Month : " + month + "& year " + year;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        }
//            else if (type.equals("newmonthwise")) {
//            String month = request.getParameter("month");
//            String year = request.getParameter("year");
//            flag = adminReportService.getMonthData(month, year);
//            if (flag == true) {
//                List<EmpTransaction> list = adminReportService.getMonthWise(month, year);
//                model.put(NewMonthWiseExcelview.TRANS_LIST, list);
//                return new ModelAndView(new NewMonthWiseExcelview(), model);
//            } else {
//                String datewise_msg = " No Record Found For Month : " + month + "& year " + year;
//                return new ModelAndView("adminreport", "msg", datewise_msg);
//            }
//        }
        else if (type.equals("yearwise")) {
            String year = request.getParameter("year");
            List<EmpTransaction> list = adminReportService.getYearWise(year);
            model.put(YearWiseExcelView.TRANS_LIST, list);
            return new ModelAndView(new YearWiseExcelView(), model);

        } else if (type.equals("employeewise")) {
            String emp = request.getParameter("emp");
            flag = adminReportService.getEmpData(emp);
            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getEmployeeWise(emp);
                model.put(EmployeeWiseExcelView.TRANS_LIST, list);
                return new ModelAndView(new EmployeeWiseExcelView(), model);
            } else {
                String datewise_msg = " No Record Found For Employee : " + emp;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        } else if (type.equals("catagory")) {
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
                String emp_id = request.getParameter("emp_id");
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
                String emp_id = request.getParameter("emp_id");
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
                String emp_id = request.getParameter("emp_id");
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
                String emp_id = request.getParameter("emp_id");
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
