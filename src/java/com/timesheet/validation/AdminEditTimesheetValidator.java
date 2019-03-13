/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.validation;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.util.LoggerUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AdminEditTimesheetValidator implements Validator {

    public boolean supports(Class clazz) {
        LoggerUtils.info("In Class TimesheetValidatorController.java --> supports() Method :: Enter");
        return EmpTransaction.class.isAssignableFrom(clazz);

    }

    public void validate(Object target, Errors errors) {
        LoggerUtils.info("In Class TimesheetValidatorController.java --> validate() Method :: Enter");
        try {
            EmpTransaction empTransaction = (EmpTransaction) target;
            String work_desc = empTransaction.getWork_desc();
            String start_time = empTransaction.getStart_time();
            //System.out.println("StartTimre"+start_time);
            String end_time = empTransaction.getEnd_time();
            //System.out.println("End Time"+end_time);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "work_desc", "work_desc.required");

            Pattern descPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");
            Pattern timepattern = Pattern.compile("([0-1]\\d|2[0-3]):([0-5]\\d)");

            Matcher descMatcher = descPattern.matcher(work_desc);
           // Matcher starttimematcher = timepattern.matcher(start_time);
            Matcher endtimematcher = timepattern.matcher(end_time);

//            if (starttimematcher.matches() == false) {
//                errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
//            }
            if (endtimematcher.matches() == false || beforeTime(start_time, end_time) == true) {
                errors.rejectValue("end_time", "Invalid.End_time", "Invalid End Time");
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
            //System.out.println("Errors"+errors.getAllErrors());


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
            //System.out.println("into catch block ===="+ex);
            
            return false;
        }


    }
}
