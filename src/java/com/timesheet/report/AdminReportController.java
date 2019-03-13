package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.emptransaction.EmpTransactionService;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AdminReportController extends SimpleFormController {

    private AdminReportService adminReportService;
    private DataSource jt;
    private EmpTransactionService empTransactionService;

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public AdminReportService getAdminReportService() {
        return adminReportService;
    }

    public void setAdminReportService(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    public DataSource getJt() {
        return jt;
    }

    public void setJt(DataSource jt) {
        this.jt = jt;
    }

    public AdminReportController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("adminreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }


    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("YEAR_LIST", adminReportService.getYears());
        session.setAttribute("EMPLIST", adminReportService.getEmployees());
        session.setAttribute("assign_by_list", empTransactionService.getAssignByList());
        session.setAttribute("project_list", empTransactionService.getProjectList());
                
         return super.formBackingObject(request);
    }

     
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        String reportstr = (String) request.getParameter("id");
        String reportType = (String) request.getParameter("reportType");


        Map<String, Object> params = new HashMap<String, Object>();
        String msg1 = "No Record Found";
         if (reportstr != null) {
            try {
                if (reportType.equals("pdf")) {
                    String reportDest = "";
                    String reportSource = "";
                    String pdfPath = null;
                    boolean flag = false;
                    JasperReport jasperReport = null;
                    String datewise_msg = "Invalid value entered";

                    if (reportstr.equals("datewise")) {
                        String date = (String) request.getParameter("datewise_txt");
                        String dt = adminReportService.Insert_fmt_date(date);
                        flag = adminReportService.getData(dt);
                        if (flag == true) {
                            reportSource = getServletContext().getRealPath("/report/admin/datewise.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/datewise.pdf");
                            pdfPath = "report\\admin\\datewise.pdf";
                            if (!date.equals("")) {
                                String fmtdate = adminReportService.Insert_fmt_date(date);
                                params.put("datewise_txt", fmtdate);
                                params.put("dayDate", adminReportService.getDay_Date(fmtdate));

                                // response.setHeader("Content-disposition", "attachment; filename=datewisereport.pdf");
                            } else {
                                return new ModelAndView("adminreport", "msg", datewise_msg);
                            }
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                    } else if (reportstr.equals("customdate")) {
                        String date1 = (String) request.getParameter("customdate_txt1");
                        String date2 = (String) request.getParameter("customdate_txt2");
                        String date3 = adminReportService.Insert_fmt_date(date1);
                        String date4 = adminReportService.Insert_fmt_date(date2);
                        flag = adminReportService.getCustomData(date3, date4);
                        if (flag == true) {
                            reportSource = getServletContext().getRealPath("/report/admin/customdate.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/customdate.pdf");
                            pdfPath = "report\\admin\\customdate.pdf";
                            if (!date1.equals("") || !date2.equals("")) {
                                params.put("upper_date", date3);
                                params.put("lower_date", date4);
                                params.put("custom_fmt1", adminReportService.getDay_Date(date3));
                                params.put("custom_fmt2", adminReportService.getDay_Date(date4));
                                // response.setHeader("Content-disposition", "attachment; filename=customdatereport.pdf");
                            } else {
                                return new ModelAndView("adminreport", "msg", datewise_msg);
                            }
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                    } else if (reportstr.equals("monthwise")) {
                        String month = (String) request.getParameter("monthlist");
                        String year = (String) request.getParameter("yearlist");
                        flag = adminReportService.getMonthData(month, year);
                        if (flag == true) {
                            reportSource = getServletContext().getRealPath("/report/admin/monthwise.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/monthwise.pdf");
                            pdfPath = "report\\admin\\monthwise.pdf";
                            if (!month.equals("0") && !year.equals("0")) {
                                String query = "SELECT em.emp_fname AS EmployeeName, DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                                        "SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS Hours, " +
                                        "abm.assign_by_name AS AssignByName, concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                                        "pm.proj_name AS Project, dm.dept_name AS Department, " +
                                        "et.work_desc AS WorkDescription, MONTHNAME(et.trans_date) AS Month FROM employee_master em, employee_master em1, " +
                                        "employee_transaction et, department_master dm, project_master pm, assign_by_master abm	" +
                                        "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND abm.assign_by_id=et.assign_by AND " +
                                        "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " +
                                        "GROUP BY EmployeeName,Date,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                                        "ORDER BY EmployeeName,Date";
                                params.put("monthWiseQuery", query);
                                params.put("year", year);
                                //   response.setHeader("Content-disposition", "attachment; filename=monthwisereport.pdf");
                            } else {
                                return new ModelAndView("adminreport", "msg", datewise_msg);
                            }
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                   }
//                        else if (reportstr.equals("newmonthwise")) {
//                        String month = (String) request.getParameter("monthlist2");
//                        String year = (String) request.getParameter("yearlist2");
//                        flag = adminReportService.getMonthData(month, year);
//                        if (flag == true) {
//                            reportSource = getServletContext().getRealPath("/report/admin/newmonthwise.jrxml");
//                            reportDest = getServletContext().getRealPath("/report/admin/newmonthwise.pdf");
//                            pdfPath = "report\\admin\\newmonthwise.pdf";
//                            if (!month.equals("0") && !year.equals("0")) {
//                                String query = "SELECT em.emp_fname AS EmployeeName, DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
//                                        "SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS Hours, " +
//                                        "abm.assign_by_name AS AssignByName, concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
//                                        "pm.proj_name AS Project, dm.dept_name AS Department, " +
//                                        "et.work_desc AS WorkDescription, MONTHNAME(et.trans_date) AS Month FROM employee_master em, employee_master em1, " +
//                                        "employee_transaction et, department_master dm, project_master pm, assign_by_master abm	" +
//                                        "WHERE et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND abm.assign_by_id=et.assign_by AND " +
//                                        "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " +
//                                        "GROUP BY EmployeeName,Date,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
//                                        "ORDER BY EmployeeName,Date";
//                                params.put("newmonthWiseQuery", query);
//                                params.put("year", year);
//                                //   response.setHeader("Content-disposition", "attachment; filename=monthwisereport.pdf");
//                            } else {
//                                return new ModelAndView("adminreport", "msg", datewise_msg);
//                            }
//                        } else {
//                            return new ModelAndView("adminreport", "msg", msg1);
//                        }
//                    }
                        else if (reportstr.equals("yearwise")) {
                        String year = (String) request.getParameter("yearlist1");
                        reportSource = getServletContext().getRealPath("/report/admin/yearwise.jrxml");
                        reportDest = getServletContext().getRealPath("/report/admin/yearwise.pdf");
                        pdfPath = "report\\admin\\yearwise.pdf";
                        if (!year.equals("0")) {
                            String query = "SELECT MONTHNAME(et.trans_date) AS Month, MONTH(et.trans_date) AS MONTHNO, " +
                                    "em.emp_fname AS EmployeeName, DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                                    "SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS Hours, " +
                                    "abm.assign_by_name AS AssignByName,concat_ws(' ',em1.emp_fname,em1.emp_lname) AS ProxyEmployee, " +
                                    "pm.proj_name AS Project, dm.dept_name AS Department, et.work_desc AS WorkDescription " +
                                    "FROM employee_master em, employee_master em1, employee_transaction et, department_master dm, " +
                                    "project_master pm, assign_by_master abm WHERE " +
                                    "et.emp_id=em.emp_id AND em1.emp_id=et.proxy_empid AND abm.assign_by_id=et.assign_by AND " +
                                    "pm.proj_id=et.proj_id AND dm.dept_id=em.dept_id AND et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-%m-%d') " +
                                    "GROUP BY Month,EmployeeName,Date,Hours,AssignByName,ProxyEmployee,Project,Department,WorkDescription " +
                                    "ORDER BY MONTHNO,Date,EmployeeName";
                            params.put("yearWiseQuery", query);
                            params.put("year", year);
                            //     response.setHeader("Content-disposition", "attachment; filename=yearwisereport.pdf");
                        } else {
                            return new ModelAndView("adminreport", "msg", datewise_msg);
                        }

                    } else if (reportstr.equals("employeewise")) {
                        String empid = (String) request.getParameter("emplist");
                        //System.out.println("empid===" + empid);
                        flag = adminReportService.getEmpData(empid);
                        if (flag == true) {
                            reportSource = getServletContext().getRealPath("/report/admin/employeewise.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/employeewise.pdf");
                            pdfPath = "report\\admin\\employeewise.pdf";
                            if (!empid.equals("")) {
                                params.put("empid", empid);
                                //    response.setHeader("Content-disposition", "attachment; filename=employeewisereport.pdf");
                            } else {
                                return new ModelAndView("adminreport", "msg", datewise_msg);
                            }
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                    } else if (reportstr.equals("criteria")) {
                        String date1 = request.getParameter("criteria_txt1");
                        String date2 = request.getParameter("criteria_txt2");
                        String start_date = adminReportService.Insert_fmt_date(date1);
                        String end_date = adminReportService.Insert_fmt_date(date2);
                        String emp_id = request.getParameter("emplist1");
                        String assign_by_id = request.getParameter("assign_by_id");
                        int assign = Integer.parseInt(assign_by_id);
                        String proj_id = request.getParameter("proj_id");
                        int proj = Integer.parseInt(proj_id);

                        if (emp_id.equals("all") && assign_by_id.equals("0") && proj_id.equals("0")) {
                            //report1
                            flag = adminReportService.getCustomData(start_date, end_date);
                            if (flag == true) {
                                reportSource = getServletContext().getRealPath("/report/admin/report1.jrxml");
                                reportDest = getServletContext().getRealPath("/report/admin/report1.pdf");
                                pdfPath = "report\\admin\\report1.pdf";
                                params.put("upper_date", start_date);
                                params.put("lower_date", end_date);
                            } else {

                                return new ModelAndView("adminreport", "msg",msg1);
                            }
                            //    response.setHeader("Content-disposition", "attachment; filename=criteriawisereport1.pdf");

                        } else if (assign_by_id.equals("0") && proj_id.equals("0")) {
                            //report2
                           flag=adminReportService.getReport2Data(emp_id, start_date, end_date);
                           if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report2.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report2.pdf");
                            pdfPath = "report\\admin\\report2.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("emp_id", emp_id);
                            params.put("emp_name", adminReportService.getEmpName(emp_id));
                           }
                           else{
                               return new ModelAndView("adminreport", "msg",msg1);
                           }

                        } else if (emp_id.equals("all") && proj_id.equals("0")) {
                            //report3
                            flag=adminReportService.getAssignByCatData(assign, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report3.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report3.pdf");
                            pdfPath = "report\\admin\\report3.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("assign_by_id", assign_by_id);
                            params.put("assign_by_name", adminReportService.getAssignByName(assign));
                            }
                            else{
                                return new ModelAndView("adminreport", "msg",msg1);
                            }

                        } else if (emp_id.equals("all") && assign_by_id.equals("0")) {
                            //report4
                            flag=adminReportService.getProjectCatData(proj, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report4.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report4.pdf");
                            pdfPath = "report\\admin\\report4.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("proj_id", proj_id);
                            params.put("proj_name", adminReportService.getProjectName(proj));
                            }
                            else{
                                 return new ModelAndView("adminreport", "msg",msg1);
                            }

                        } else if (proj_id.equals("0")) {
                            //report5
                            flag=adminReportService.getReport5Data(emp_id, assign, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report5.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report5.pdf");
                            pdfPath = "report\\admin\\report5.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("emp_id", emp_id);
                            params.put("assign_by_id", assign_by_id);
                            params.put("assign_by_name", adminReportService.getAssignByName(assign));
                            params.put("emp_name", adminReportService.getEmpName(emp_id));
                            }else{
                                 return new ModelAndView("adminreport", "msg",msg1);
                            }
                        }
                         else if (assign_by_id.equals("0")) {
                            //report6
                            flag=adminReportService.getReport6Data(emp_id, proj, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report6.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report6.pdf");
                            pdfPath = "report\\admin\\report6.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("emp_id", emp_id);
                            params.put("proj_id", proj_id);
                            params.put("emp_name", adminReportService.getEmpName(emp_id));
                            params.put("proj_name", adminReportService.getProjectName(proj));
                        }else{
                             return new ModelAndView("adminreport", "msg",msg1);
                        }
                        } else if (emp_id.equals("all")) {
                            //report7
                            flag=adminReportService.getReport7Data(assign, proj, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report7.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report7.pdf");
                            pdfPath = "report\\admin\\report7.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("assign_by_id", assign_by_id);
                            params.put("proj_id", proj_id);
                            params.put("proj_name", adminReportService.getProjectName(proj));
                            params.put("assign_by_name", adminReportService.getAssignByName(assign));
                            }else{
                                return new ModelAndView("adminreport", "msg",msg1);
                            }
                        } else {
                            //report8
                            flag=adminReportService.getReport8Data(emp_id, assign, proj, start_date, end_date);
                            if(flag==true){
                            reportSource = getServletContext().getRealPath("/report/admin/report8.jrxml");
                            reportDest = getServletContext().getRealPath("/report/admin/report8.pdf");
                            pdfPath = "report\\admin\\report8.pdf";
                            params.put("upper_date", start_date);
                            params.put("lower_date", end_date);
                            params.put("emp_id", emp_id);
                            params.put("assign_by_id", assign_by_id);
                            params.put("proj_id", proj_id);
                            params.put("emp_name", adminReportService.getEmpName(emp_id));
                            params.put("proj_name", adminReportService.getProjectName(proj));
                            params.put("assign_by_name", adminReportService.getAssignByName(assign));
                        }else{
                                return new ModelAndView("adminreport", "msg",msg1);
                        }
                        }
                    }
                    
                    jasperReport = JasperCompileManager.compileReport(reportSource);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jt.getConnection());
                    JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                    response.setContentType("application/pdf");
                    OutputStream out = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                    request.setAttribute("PDFPATH", pdfPath);
                    return new ModelAndView("showadminreport");
                } else if (reportType.equals("excel")) {
                    if (reportstr.equals("datewise")) {
                        //System.out.println("context: " + getServletContext());
                        String date = (String) request.getParameter("datewise_txt");
                        String dt = adminReportService.Insert_fmt_date(date);
                        boolean flag = adminReportService.getData(dt);
                        //System.out.println("Flag========" + flag);
                        if (flag == true) {
                            response.setContentType("application/vnd.ms-excel");
                            HttpSession session = request.getSession(false);
                            session.setAttribute("datewise", adminReportService.getDateWise(dt));
                            return new ModelAndView("datewiseExcel");
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                    } else {
                        return null;
                    }
                }else if (reportType.equals("html")) {
                    if (reportstr.equals("datewise")) {
                         //System.out.println("context: " + getServletContext());
                        String date = (String) request.getParameter("datewise_txt");
                        String dt = adminReportService.Insert_fmt_date(date);
                        boolean flag = adminReportService.getData(dt);
                        //System.out.println("Flag========" + flag);
                        if (flag == true) {
                            
                            HttpSession session = request.getSession(false);
                            session.setAttribute("datewise", adminReportService.getDateWise(dt));
                            return new ModelAndView("datewisehtml");
                        } else {
                            return new ModelAndView("adminreport", "msg", msg1);
                        }
                    } else {
                        return null;
                    }
                } else {
                    String rep_type_sel = "select choice (pdf / excel / html)";
                    return new ModelAndView("adminreport", "msg", rep_type_sel);
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            String reportstrnull = "select choice";
            return new ModelAndView("adminreport", "msg", reportstrnull);
        }
    }
}
