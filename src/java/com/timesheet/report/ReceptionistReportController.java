package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.util.LoggerUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ReceptionistReportController extends SimpleFormController {

    private AdminReportService adminReportService;
    private DataSource jt;

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

    public ReceptionistReportController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("receptionistreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException, ClassNotFoundException, IOException, SQLException, JRException {
        LoggerUtils.info("Inside ReceptionistReportController class onSubmit() called");
        String reportSource = "";
        String reportDest = "";
        String pdfPath = null;
        boolean flag = false;
        String reportstr = (String) request.getParameter("id");
        LoggerUtils.info("reportstr: " + reportstr);
        Map<String, Object> params = new HashMap<String, Object>();
        //ServletOutputStream out = null;
        String msg = "invalid date entered";
        String msg1 = "No Record Found";
        if (reportstr != null) {
            try {

                JasperReport jasperReport = null;
                if (reportstr.equals("daily")) {
                    String date1 = (String) request.getParameter("daily_txt");
                    String date = adminReportService.Insert_fmt_date(date1);
                    flag = adminReportService.getData(date);
                    if (flag == true) {
                        reportSource = getServletContext().getRealPath("/report/admin/daily.jrxml");
                        reportDest = getServletContext().getRealPath("/report/receptionist/daily.xls");
                        pdfPath = "report\\receptionist\\daily.xls";
                        if (!date1.equals("")) {
                            params.put("daily_txt", date);
                            params.put("dayDate", adminReportService.getDay_Date(date));
                        } else {
                            return new ModelAndView("receptionistreport", "msg", msg);
                        }
                    } else {
                        return new ModelAndView("receptionistreport", "msg", msg1);
                    }
                }

                jasperReport = JasperCompileManager.compileReport(reportSource);
                LoggerUtils.info("reportdest  = " + reportDest);
                LoggerUtils.info("jt: " + jt.getConnection());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jt.getConnection());
                JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                response.setContentType("application/pdf");
                OutputStream out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                request.setAttribute("PDFPATH", pdfPath);
                return new ModelAndView("receptionistreport");
            } catch (Exception e) {
                return null;
            }
        } else {
            String choiceMsg = "select the choice";
            return new ModelAndView("receptionistreport", "msg", choiceMsg);
        }
    }
}
