/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.validation;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.emptransaction.EmpTransactionService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RightAddTimesheetValidator implements Validator {

    private EmpTransactionService empTransactionService;

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public boolean supports(Class clazz) {
        return EmpTransaction.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        try {
            //System.out.println("==== In RightAddTimeSheetValidator ====");
            EmpTransaction empTransaction = (EmpTransaction) target;
            String emp_id = empTransaction.getEmp_id();
            String work_desc = empTransaction.getWork_desc();
            String start_time = empTransaction.getStart_time();
            String end_time = empTransaction.getEnd_time();
            String trans_date = empTransaction.getTrans_date();

            //System.out.println("start_time: " + start_time);
            //System.out.println("end_time: " + end_time);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "work_desc", "work_desc.required");

            Pattern descPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");
            Pattern timepattern = Pattern.compile("([0-1]\\d|2[0-3]):([0-5]\\d)");
            Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");

            Matcher descMatcher = descPattern.matcher(work_desc);
            Matcher starttimematcher = timepattern.matcher(start_time);
            Matcher endtimematcher = timepattern.matcher(end_time);
            Matcher dateMatcher = datePattern.matcher(trans_date);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
             TimeZone istTime = TimeZone.getTimeZone("IST"); 
             dateFormat.setTimeZone(istTime);
            java.util.Date date = new java.util.Date();
            String currentdate = dateFormat.format(date);
            //System.out.println("CurrentDate is====" + currentdate);


            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date dt = sdf.parse(trans_date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            String fmt_date = sdf1.format(dt);
            //System.out.println("Fmt_Date is===" + fmt_date);

          //root timesheet validation commented
            
            /*  List<EmpTransaction> et = empTransactionService.getEnd_Time(fmt_date, emp_id);
             //System.out.println("THe Size of List is====" + et.size());

             EmpTransaction et1 = null;
             String endtime = null;
             String starttime = null;
             String endtime1 = null;
             String starttime1 = null;
             //String starttime2 = null;

             //            If (end_time = "00.00"){
             //            errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
             //            }

             if (et.size() > 0) {
             for (int i = 0; i < et.size(); i++) {
             et1 = et.get(i);
             starttime = et1.getStart_time();
             endtime = et1.getEnd_time();
             if (starttimematcher.matches() == false || beforeTime(start_time, end_time) == true || (CheckStartTime(start_time, starttime, endtime)) == true || UnBoundTime(start_time, end_time, starttime, endtime) == true) {
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
             if (endtimematcher.matches() == false || beforeTime1(start_time, end_time) == true || (CheckEndTime(end_time, starttime1, endtime1)) == true || UnBoundTime(start_time, end_time, starttime1, endtime1) == true) {
             errors.rejectValue("end_time", "Invalid.End_time", "Invalid End Time");
             break;
             }
             }
             }*/

            List<EmpTransaction> et = empTransactionService.getEnd_TimeForValidation(currentdate, emp_id);
            EmpTransaction et1;
            String endtime = null;
            String starttime = null;
            String endtime1 = null;
            String starttime1 = null;
            //System.out.println("********************************************");
            //System.out.println("et.sie>>>>>>>>>>>>>>>>>"+et.size());
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
                    if (starttimematcher.matches() == false || (CheckStartTime(start_time, starttime, endtime)) == true || beforeTime(start_time, end_time) == true || UnBoundTime(start_time, end_time, starttime, endtime) == true  ) {
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

            try {
                if (trans_date.equals("")) {
                    ValidationUtils.rejectIfEmpty(errors, "trans_date", "trans_date.required");
                } else {
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date date1 = new java.util.Date();
                    // //System.out.println("********DATE" +date);

                    String currentdate1 = dateFormat1.format(date1);
                    // //System.out.println("********DATE" +currentdate);
                    //      String delcurdate = currentdate.replaceAll("/", "");
                    ////System.out.println("********delcurdate" +delcurdate);

                    SimpleDateFormat trans = new SimpleDateFormat("dd/mm/yyyy");
                    Date dt1 = trans.parse(trans_date);
                    String transdate = dateFormat1.format(dt1);

                    //    String delbirthdate = transdate.replaceAll("/", "");
                    //   //System.out.println("********delbirthdate:   " +delbirthdate);

                    //  //System.out.println("MYDATE:   " +delbirthdate);


                    if (dateMatcher.matches() == true && validateDayMonth(trans_date) == true) {

                        // Calendar cd = new GregorianCalendar(Integer.parseInt(delcurdate.substring(0, 4)), Integer.parseInt(delcurdate.substring(4, 6)), Integer.parseInt(delcurdate.substring(6, 8)));
                        //  //System.out.println("CURRENT DATE:  "  +cd);
                        //  Calendar birthd = new GregorianCalendar(Integer.parseInt(delbirthdate.substring(4, 8)), Integer.parseInt(delbirthdate.substring(2, 4)), Integer.parseInt(delbirthdate.substring(0, 2)));
                        // //System.out.println("MY DATE:  "  +birthd);

                        if (currentdate1.compareTo(transdate) < 0) {
                            errors.rejectValue("trans_date", "Invalid.trans_date", "Enter valid date");
                        }
                    } else {
                        errors.rejectValue("trans_date", "Invalid.trans_date", "Enter valid date");
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            if (empTransaction.getAssign_by_id() == 0) {
                errors.rejectValue("assign_by_id", "Invalid.assign_by_id", "Please Select The Assign By Name");
            }

            if (empTransaction.getProj_id() == 0) {
                errors.rejectValue("proj_id", "Invalid.proj_id", "Please Select The Project");
            }
            if (descMatcher.matches() == false) {
                errors.rejectValue("work_desc", "Invalid.work_desc", "can not accept < and >");
            }

            //System.out.println("All Errors in validator: " + errors.getAllErrors());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean beforeTime(String time1, String time2) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date start = sdf.parse(time1);
            Date end = sdf.parse(time2);
            //System.out.println("start ans end parse in beforeTime:: " + start + ",,," + end);
            if (start.after(end) || start.equals(end)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            //System.out.println("Error in beforeTime validation: " + ex);
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
            }
            else if (new_start.equals(old_start)) {
                return true;
            }
            else {
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
            }
            else if (new_end.equals(old_end)) {
               return true;
            } 
            else {
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
