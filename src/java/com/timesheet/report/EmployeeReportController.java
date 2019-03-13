package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class EmployeeReportController extends SimpleFormController {

    private AdminReportService adminReportService;
    private DataSource jt;

    public DataSource getJt() {
        return jt;
    }

    public void setJt(DataSource jt) {
        this.jt = jt;
    }

    public AdminReportService getAdminReportService() {
        return adminReportService;
    }

    public void setAdminReportService(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    public EmployeeReportController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("empreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("YEAR_LIST", adminReportService.getYears());
        session.setAttribute("EMPLIST", adminReportService.getEmployees());
         session.setAttribute("assign_by_list", adminReportService.getAssignByList());
        session.setAttribute("project_list", adminReportService.getProjectList());
        return super.formBackingObject(request);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String reportDest = "";
        String reportSource = "";
        String pdfPath = null;
        boolean flag = false;
        String reportstr = (String) request.getParameter("id");
        Map<String, Object> params = new HashMap<String, Object>();
        ServletOutputStream out = null;
        //System.out.println("reportstr: " + reportstr);
        if (reportstr != null) {
            try {
                HttpSession session = request.getSession();
                Employee e = (Employee) session.getAttribute("emp");
                String empid = e.getEmp_id();
                String empname = e.getEmp_fname();
                params.put("empid", empid);
                JasperReport jasperReport = null;
                String datewise_msg = "Invalid value entered";
                String msg1 = "No Record Found";
                if (reportstr.equals("datewise")) {
                    String date1 = (String) request.getParameter("datewise_txt");
                    String dt = adminReportService.Insert_fmt_date(date1);
                    flag = adminReportService.getData(dt);
                    if (flag == true) {
                        reportSource = getServletContext().getRealPath("/report/employee/datewise.jrxml");
                        reportDest = getServletContext().getRealPath("/report/employee//datewise.pdf");
                        pdfPath = "report\\employee\\datewise.pdf";
                        if (!date1.equals("")) {
                            String date = adminReportService.Insert_fmt_date(date1);
                            params.put("datewise_txt", date);
                            params.put("dayDate", adminReportService.getDay_Date(date));
                            params.put("empname",empname);
                            //  response.setHeader("Content-Disposition", "attachment; filename=datewisereport.pdf");
                        } else {
                            //System.out.println("in else...." + datewise_msg);
                            return new ModelAndView("employeereport", "msg", datewise_msg);
                        }
                    } else {
                        return new ModelAndView("employeereport", "msg", msg1);
                    }
                } else if (reportstr.equals("customdate")) {
                    String date1 = (String) request.getParameter("customdate_txt1");
                    String date2 = (String) request.getParameter("customdate_txt2");
                    String date3 = adminReportService.Insert_fmt_date(date1);
                    String date4 = adminReportService.Insert_fmt_date(date2);
                    flag = adminReportService.getCustomData(date3, date4);
                    if (flag == true) {
                        reportSource = getServletContext().getRealPath("/report/employee/customdate.jrxml");
                        reportDest = getServletContext().getRealPath("/report/employee/customdate.pdf");
                        pdfPath = "report\\employee\\customdate.pdf";
                        if (!date1.equals("") || !date2.equals("")) {
                            params.put("upper_date", date3);
                            params.put("lower_date", date4);
                            params.put("custom_fmt1", adminReportService.getDay_Date(date3));
                            params.put("custom_fmt2", adminReportService.getDay_Date(date4));
                            params.put("empname",empname);
                        } else {
                            return new ModelAndView("employeereport", "msg", datewise_msg);
                        }
                    } else {
                        return new ModelAndView("employeereport", "msg", msg1);
                    }
                } else if (reportstr.equals("monthwise")) {
                    String month = (String) request.getParameter("monthlist");
                    String year = (String) request.getParameter("yearlist");
                    flag = adminReportService.getMonthData(month, year);
                    if (flag == true) {
                        reportSource = getServletContext().getRealPath("/report/employee/monthwise.jrxml");
                        reportDest = getServletContext().getRealPath("/report/employee/monthwise.pdf");
                        pdfPath = "report\\employee\\monthwise.pdf";
                        if (!month.equals("0") && !year.equals("0")) {
                            String query = "SELECT DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, MONTH(et.trans_date) AS MONTHNO," +
                                    "SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS Hours, " +
                                    "abm.assign_by_name AS AssignByName, concat_ws(' ',em.emp_fname,em.emp_lname) AS ProxyEmployee, " +
                                    "pm.proj_name AS Project, " +
                                    "et.work_desc AS WorkDescription, MONTHNAME(et.trans_date) AS Month FROM employee_master em, " +
                                    "employee_transaction et, project_master pm, assign_by_master abm " +
                                    "WHERE em.emp_id=et.proxy_empid AND abm.assign_by_id=et.assign_by AND " +
                                    "et.emp_id=$P{empid} AND pm.proj_id=et.proj_id AND et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-" + month + "-%d') " +
                                    "GROUP BY Date,Hours,AssignByName,ProxyEmployee,Project,WorkDescription " +
                                    "ORDER BY Date";
                            params.put("monthWiseQuery", query);
                            params.put("year", year);
                            params.put("empname",empname);
                        } else {
                            return new ModelAndView("employeereport", "msg", datewise_msg);
                        }
                    } else {
                        return new ModelAndView("employeereport", "msg", msg1);
                    }
                } else if (reportstr.equals("yearwise")) {
                    String year = (String) request.getParameter("yearlist1");
                    flag = adminReportService.getEmpYearData(year, empid);
                    if (flag == true) {
                        reportSource = getServletContext().getRealPath("/report/employee/yearwise.jrxml");
                        reportDest = getServletContext().getRealPath("/report/employee/yearwise.pdf");
                        pdfPath = "report\\employee\\yearwise.pdf";

                        if (!year.equals("0")) {
                            String query = "SELECT MONTHNAME(et.trans_date) AS Month, " +
                                    "DATE_FORMAT(et.trans_date,'%d/%m/%Y') As Date, " +
                                    "SEC_TO_TIME(TIME_TO_SEC(TIMEDIFF(end_time,start_time))) AS Hours, " +
                                    "abm.assign_by_name AS AssignByName, concat_ws(' ',em.emp_fname,em.emp_lname) AS ProxyEmployee, " +
                                    "pm.proj_name AS Project,et.work_desc AS WorkDescription " +
                                    "FROM employee_master em, employee_transaction et, " +
                                    "project_master pm, assign_by_master abm WHERE " +
                                    "et.emp_id=$P{empid} AND em.emp_id=et.proxy_empid AND abm.assign_by_id=et.assign_by AND " +
                                    "pm.proj_id=et.proj_id AND et.trans_date=DATE_FORMAT(et.trans_date,'" + year + "-%m-%d') " +
                                    "GROUP BY Month,Date,Hours,AssignByName,ProxyEmployee,Project,WorkDescription " +
                                    "ORDER BY MONTH(et.trans_date),DAY(et.trans_date)";
                            params.put("yearWiseQuery", query);
                            params.put("year", year);
                            params.put("empname",empname);
                        } else {
                            return new ModelAndView("employeereport", "msg", datewise_msg);
                        }
                    }else{
                        return new ModelAndView("employeereport", "msg", msg1);
                    }

                }
                jasperReport = JasperCompileManager.compileReport(reportSource);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jt.getConnection());
                JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                response.setContentType("application/pdf");
                out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                request.setAttribute("PDFPATH", pdfPath);
                return new ModelAndView("employeereport");
            } catch (DataAccessException dae) {
                //System.out.println("dae error in EmployeeReportController: "+dae);
                return new ModelAndView("employeereport");
            } catch (JRException je) {
                //System.out.println("JRException in EmployeeReportController: "+je);
                return new ModelAndView("employeereport");
            }catch(IllegalStateException ise){
                return new ModelAndView("employeereport");
            }
        } else {
            String choicemsg = "select the choice";
            return new ModelAndView("employeereport", "msg", choicemsg);
        }
    }
}
