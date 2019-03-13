/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.validation;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.emptransaction.EmpTransactionService;
import com.timesheet.util.LoggerUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author prodigy
 */
public class TimesheetValidatorController implements Validator {

    private EmpTransactionService empTransactionService;

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public boolean supports(Class clazz) {
        LoggerUtils.info("In Class TimesheetValidatorController.java --> supports() Method :: Enter");
        return EmpTransaction.class.isAssignableFrom(clazz);

    }

    public void validate(Object target, Errors errors) {
        LoggerUtils.info("In Class TimesheetValidatorController.java --> validate() Method :: Enter");
        try {

            EmpTransaction empTransaction = (EmpTransaction) target;
            //System.out.println("00000000000000000000000000000000000000000000000000000");
            //System.out.println("00000000000000000" + empTransaction.getTrans_id() + "0000000000000");
            //System.out.println("00000000000000000000000000000000000000000000000000000");

            long trans_id = empTransaction.getTrans_id();
            String trans_date = empTransaction.getTrans_date();
            String work_desc = empTransaction.getWork_desc();
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "work_desc", "work_desc.required");
            if (empTransaction.getAssign_by_id() == 0) {
                errors.rejectValue("assign_by_id", "Invalid.assign_by_id", "Please Select The Assign By Name");
            }

            if (empTransaction.getProj_id() == 0) {
                errors.rejectValue("proj_id", "Invalid.proj_id", "Please Select The Project");
            }
            Pattern descPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");

            Matcher descMatcher = descPattern.matcher(work_desc);

            if (descMatcher.matches() == false) {
                errors.rejectValue("work_desc", "Invalid.work_desc", "can not accept < and >");
            }
            try {
                String emp_id = empTransaction.getEmp_id();
                String time_right = empTransactionService.checkRight(emp_id);
                //System.out.println("into the time_right" + time_right);
                if (time_right.equals("T")) {
                    String start_time = empTransaction.getStart_time();
                    String end_time = empTransaction.getEnd_time();
                    Pattern timepattern = Pattern.compile("([0-1]\\d|2[0-3]):([0-5]\\d)");
                    Matcher starttimematcher = timepattern.matcher(start_time);
                    Matcher endtimematcher = timepattern.matcher(end_time);
                    /*  SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                     Date dt = sdf.parse(trans_date);
                     SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                     String fmt_date = sdf1.format(dt);

                     List<EmpTransaction> et = empTransactionService.getEnd_Time(fmt_date, emp_id);
                     EmpTransaction et1;
                     String endtime = null;
                     if (et.size() > 0) {
                     for (int i = 0; i < et.size(); i++) {
                     et1 = et.get(i);
                     endtime = et1.getEnd_time();
                     //System.out.println("End_Time=====" + endtime);
                     if (starttimematcher.matches() == false || beforeTime(endtime, start_time) == true) {
                     errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
                     }
                     }
                     }
                     */
                    List<EmpTransaction> et = empTransactionService.getEnd_TimeForValidationUpdate(trans_date, emp_id,trans_id);
                    EmpTransaction et1;
                    String endtime = null;
                    String starttime = null;
                    String endtime1 = null;
                    String starttime1 = null;
                    //System.out.println("********************************************");
                    //System.out.println("et.sie>>>>>>>>>>>>>>>>>" + et.size());
                    //System.out.println("********************************************");
                    if (et.size() > 0) {
                        for (int i = 0; i < et.size(); i++) {
                            et1 = et.get(i);
                            starttime = et1.getStart_time();
                            endtime = et1.getEnd_time();
                            //System.out.println("getendtimeashhhhhhh" + endtime);
                            //System.out.println("firstvalidator" + starttimematcher.matches());
                            //System.out.println("secondvalidator" + beforeTime(start_time, end_time));
                            //System.out.println("thirdvalidator:::::::::::::::::::::::::::::::::::::::::" + (CheckStartTime(start_time, starttime, endtime)));
                            //System.out.println("fourthvalidator" + UnBoundTime(start_time, end_time, starttime, endtime));
                            if (starttimematcher.matches() == false || (CheckStartTime(start_time, starttime, endtime)) == true || beforeTime(start_time, end_time) == true || UnBoundTime(start_time, end_time, starttime, endtime) == true) {
                                errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
                                break;
                            }
                        }
                    }

                    if (et.size() > 0) {
                        for (int i = 0; i < et.size(); i++) {
                            et1 = et.get(i);
                            starttime1 = et1.getStart_time();
                            endtime1 = et1.getEnd_time();

                            //System.out.println("firstvalidator" + endtimematcher.matches());
                            //System.out.println("secondvalidator" + UnBoundTime(start_time, end_time, starttime1, endtime1));


                            if (endtimematcher.matches() == false || (CheckEndTime(end_time, starttime1, endtime1)) == true || beforeTime1(start_time, end_time) == true || UnBoundTime(start_time, end_time, starttime1, endtime1) == true) {
                                errors.rejectValue("end_time", "Invalid.End_time", "Invalid End Time");
                                break;
                            }
                        }
                    }


                    if (starttimematcher.matches() == false) {
                        errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
                    }
                    if (endtimematcher.matches() == false || beforeTime(start_time, end_time) == true) {
                        errors.rejectValue("end_time", "Invalid.End_time", "Invalid End Time");
                    }

                }
            } catch (IndexOutOfBoundsException ie) {
                ie.printStackTrace();
            }

            LoggerUtils.info("In Class TimesheetValidatorController.java --> validate() Method :: Exit");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean beforeTime(String time1, String time2) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date start = sdf.parse(time1);
            Date end = sdf.parse(time2);
            if (start.after(end) || start.equals(end)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("into catch block ====" + ex);

            return false;
        }


    }

    public boolean CheckStartTime(String start_time, String starttime, String endtime) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date new_start = sdf.parse(start_time);
            Date old_start = sdf.parse(starttime);
            Date old_end = sdf.parse(endtime);
            //System.out.println("new_start: " + new_start);
            //System.out.println("start and end parse in CheckStartTime:: " + old_start + ",,," + old_end);
            if (new_start.after(old_start) && new_start.before(old_end)) {
                return true;
            } else if (new_start.equals(old_start)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("Error in beforeTime validation: " + ex);
            return false;
        }


    }

    public static boolean validateDayMonth(String birthdate) {
        boolean isValid = false;
        String[] dateArray = birthdate.split("/");

        int day = Integer.valueOf(dateArray[0]).intValue();
        int month = Integer.valueOf(dateArray[1]).intValue();
        int year = Integer.valueOf(dateArray[2]).intValue();
        if ((day > 0 && day <= 31) && (month > 0 && month <= 12)) {
            isValid = true;
            try {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setLenient(false);
                cal.set(year, (month - 1), day);
                cal.add(Calendar.SECOND, 1);
            } catch (IllegalArgumentException iae) {
                isValid = false;
            }

        }
        return isValid;
    }

    public boolean CheckEndTime(String end_time, String starttime, String endtime) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date new_end = sdf.parse(end_time);
            Date old_start = sdf.parse(starttime);
            Date old_end = sdf.parse(endtime);
            //System.out.println("new_end: " + new_end);
            //System.out.println("start ans end parse in CheckEndTime: " + old_start + ",,," + old_end);
            if (new_end.after(old_start) && new_end.before(old_end)) {
                return true;
            } else if (new_end.equals(old_end)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("Error in beforeTime validation: " + ex);
            return false;
        }
    }

    public boolean beforeTime1(String time1, String time2) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date start = sdf.parse(time1);
            Date end = sdf.parse(time2);
            if (end.before(start)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("into catch block ====");
            return false;
        }
    }

    public boolean UnBoundTime(String start_time, String end_time, String starttime, String endtime) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date new_start = sdf.parse(start_time);
            Date new_end = sdf.parse(end_time);
            Date old_start = sdf.parse(starttime);
            Date old_end = sdf.parse(endtime);
            if (new_start.before(old_start) && new_end.after(old_end)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("Error in beforeTime validation: " + ex);
            return false;
        }
    }
}
