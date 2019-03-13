/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.report;



import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import com.timesheet.emptransaction.EmpTransactionService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class HtmlReportController extends SimpleFormController {

    private AdminReportService adminReportService;
    private EmpTransactionService empTransactionService;
   

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


    public HtmlReportController() {
        //Initialize controller properties here or in the Web Application Context

        setCommandClass(EmpTransaction.class);
        setCommandName("htmlreport");
        //setSuccessView("successView");
        //setFormView("formView");
    }

    

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String type = request.getParameter("type");
        //System.out.println("*******" +type);
        model.put("dType",type);
        //System.out.println(model);
        boolean flag = false;
         

        if (type.equals("datewise")) {
            String dt = request.getParameter("datewise_txt");
             model.put("dt1", dt);
             String date = adminReportService.Insert_fmt_date(dt);
                model.put("dur", adminReportService.getTotalDurationWithDate(date));
                
            flag = adminReportService.getData(date);
            //System.out.println("Flag ===" + flag);
            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getDateWise(date);
                model.put("employees", list);
                 return new ModelAndView("datewisehtml","e",model);
            } else {
                String datewise_msg = " No Record Found date: " + date;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        }  else if (type.equals("daily")) {
            String dt = request.getParameter("daily_txt");
            model.put("dt1", dt);
             String date = adminReportService.Insert_fmt_date(dt);

            
            model.put("dur", adminReportService.getTotalDurationWithDate(date));

            flag = adminReportService.getData(date);
            //System.out.println("Flag ===" + flag);

            if (flag == true) {

                List<EmpTransaction> list = adminReportService.getDateWise(date);
                List<Employee> empname = adminReportService.getTransEmpName(date);
                model.put("employees", list);
                model.put("emplist", empname);
                
                return new ModelAndView("dailyhtml","e", model);
            } else {
                String datewise_msg = " No Record Found date: " + date;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        }else if (type.equals("customdate")) {
            String date1 = request.getParameter("customdate_txt1");
            model.put("dt1", date1);
            String date2 = request.getParameter("customdate_txt2");
            model.put("dt2", date2);
            String date3 = adminReportService.Insert_fmt_date(date1);
            String date4 = adminReportService.Insert_fmt_date(date2);
           
            model.put("dur", adminReportService.getTotalDurationWithCustomDate(date3,date4));

            flag = adminReportService.getCustomData(date3, date4);
            if (flag == true) {
              
                    List<EmpTransaction> list = adminReportService.getCustomDateWise(date3, date4);
 
                model.put("employees", list);

                                return new ModelAndView("customdatewisehtml","e", model);
            } else {
                String datewise_msg = " No Record Found For date: " + date1 + " & " + date2;
                return new ModelAndView("adminreport", "msg", datewise_msg);
           
        }
        }else if (type.equals("monthwise")) {
            String month = request.getParameter("month");
            model.put("omonth", month);

            String year = request.getParameter("year");
                        model.put("oyear", year);

              
            model.put("dur", adminReportService.getTotalDurationWithMonth(month, year));
           
            flag = adminReportService.getMonthData(month, year);

            

            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getMonthWise(month, year);
                model.put("employees", list);
                return new ModelAndView("monthwisehtml","e", model);
            } else {
                String datewise_msg = " No Record Found For Month : " + month + "& year " + year;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        }  else if (type.equals("yearwise")) {
            String year = request.getParameter("year");

            model.put("year1", year);
              
             model.put("dur", adminReportService.getTotalDurationWithYear(year));
             //System.out.println("yearwisesouttotalduraton^^^^^^^^^" +model);
            List<EmpTransaction> list = adminReportService.getYearWise(year);
            model.put("employees", list);
            return new ModelAndView("yearwisehtml","y", model);

        }else if (type.equals("employeewise")) {
            String emp = request.getParameter("emp");
            model.put("empr", emp);
            flag = adminReportService.getEmpData(emp);
            if (flag == true) {
                List<EmpTransaction> list = adminReportService.getEmployeeWise(emp);
                model.put("employees", list);
                model.put("dur", adminReportService.getAllTotalDuration(emp));
                
                return new ModelAndView("employeewisehtml","e", model);
            } else {
                String datewise_msg = " No Record Found For Employee : " + emp;
                return new ModelAndView("adminreport", "msg", datewise_msg);
            }
        } else if (type.equals("criteria")) {
            String cat = request.getParameter("cat");
            model.put("cat1", cat);
            String date1 = request.getParameter("start_date");
            String date2 = request.getParameter("end_date");
            String start_date = adminReportService.Insert_fmt_date(date1);
            String end_date = adminReportService.Insert_fmt_date(date2);

            //System.out.println("cat: " + cat);
            if (cat.equals("project")) {
                String proj = request.getParameter("proj_id");
                 int proj_id = Integer.parseInt(proj);
                //System.out.println("proj_id in ExcelReportController: " + proj_id);
//                model.put("dur", adminReportService.getTotalDurationWithCustomDateReport2(emp_id,start_date,end_date));

                flag = adminReportService.getProjectCatData(proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getProjectCatagory(proj_id, start_date, end_date);
                    model.put("employees", list);
                    return new ModelAndView("projectcatagorywisehtml","e",model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }
            } else if (cat.equals("assign")) {
                String assign = request.getParameter("assign_by_id");
                model.put("asgn", assign);
                 model.put("crdate1", date1);
                  model.put("crdate2", date2);

                int assign_by_id = Integer.parseInt(assign);
                flag = adminReportService.getAssignByCatData(assign_by_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getAssignByCatagory(assign_by_id, start_date, end_date);
                    model.put("employees", list);
                    return new ModelAndView("assigncatagoryhtml","e",model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }
            } else if (cat.equals("report2")) {
                String emp_id = request.getParameter("emp_id");
                                model.put("emp_id", emp_id);
                                 model.put("rep2date1", date1);
                  model.put("rep2date2", date2);
               
                 model.put("dur", adminReportService.getTotalDurationWithCustomDateReport2(emp_id,start_date,end_date));
                flag = adminReportService.getReport2Data(emp_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport2(emp_id, start_date, end_date);
                    model.put("employees", list);
                    return new ModelAndView("report2html","e",model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            }else if (cat.equals("report1")) {
                 String datec1 = request.getParameter("start_date");
                model.put("criteriadate1", datec1);
            String datec2 = request.getParameter("end_date");
                            model.put("criteriadate2", datec2);
             String date3 = adminReportService.Insert_fmt_date(datec1);
            String date4 = adminReportService.Insert_fmt_date(datec2);
            String proj = request.getParameter("proj_id");
            proj = "All";
             model.put("proj", proj);



            model.put("dur", adminReportService.getTotalDurationWithCustomDate(date3,date4));

        flag = adminReportService.getCustomData(date3, date4);
            if (flag == true) {

                    List<EmpTransaction> list = adminReportService.getCustomDateWise(date3, date4);


                //System.out.println("List is====" + list);
                model.put("employees", list);
                //System.out.println("####" +model);
                                 return new ModelAndView("report1html","e", model);
            } else {
                String datewise_msg = " No Record Found For date: " + date1 + " & " + date2;
                return new ModelAndView("adminreport", "msg", datewise_msg);

        }
        } else if (cat.equals("report5")) {
                String emp_id = request.getParameter("emp_id");
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                flag = adminReportService.getReport5Data(emp_id, assign_by_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport5(emp_id, assign_by_id, start_date, end_date);
                    model.put("employees", list);
                    return new ModelAndView("report5html","e",model);
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
                    model.put("employees", list);
                    return new ModelAndView("report6html","e",model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else if (cat.equals("report7")) {
                String assign = request.getParameter("assign_by_id");
                int assign_by_id = Integer.parseInt(assign);
                String proj = request.getParameter("proj_id");
                int proj_id = Integer.parseInt(proj);
            model.put("dur", adminReportService.getTotalDurationWithCustomDateReport7(assign_by_id, proj_id, start_date, end_date));

                 flag = adminReportService.getReport7Data(assign_by_id, proj_id, start_date, end_date);
                if (flag == true) {
                  //System.out.println("here1");
                    List<EmpTransaction> list = adminReportService.getReport7(assign_by_id, proj_id, start_date, end_date);
                                      //System.out.println("here2....." +list);

                    model.put("employees", list);
                    return new ModelAndView("report7html","e",model);
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
                model.put("dur", adminReportService.getTotalDurationWithCustomDateReport8(emp_id, assign_by_id, proj_id, start_date, end_date));

                flag = adminReportService.getReport8Data(emp_id, assign_by_id, proj_id, start_date, end_date);
                if (flag == true) {
                    List<EmpTransaction> list = adminReportService.getReport8(emp_id, assign_by_id, proj_id, start_date, end_date);

                    model.put("employees", list);

                    return new ModelAndView("report8html","e",model);
                } else {
                    String datewise_msg = " No Record Found";
                    return new ModelAndView("adminreport", "msg", datewise_msg);
                }

            } else {
                return null;
            }
        }else {
            return null;
           

        }
    }


}
