/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.validation;

import com.timesheet.bean.Project;
import com.timesheet.util.LoggerUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author prodigy
 */
public class ProjectValidatorController implements Validator {

    public boolean supports(Class clazz) {
        //System.out.println("In Class ProjectValidatorController.java --> supports() Method :: Enter");
        return Project.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        LoggerUtils.info("In Class ProjectValidatorController.java --> validate() Method :: Enter");
       
        try {
            Project project = (Project) target;
            String projname = project.getProj_name();
            String startdate = project.getStart_date();
            String targetdate = project.getTarget_date();
            String pro_desc = project.getProj_desc();

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start_date", "start_date.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "target_date", "target_date.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "proj_name", "proj_name.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "proj_desc", "proj_desc.required");

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date = new java.util.Date();
            String currentdate = dateFormat.format(date);

            String delstartdate = startdate.replaceAll("/", "");
            String deltargetdate = targetdate.replaceAll("/", "");
            String delcurdate = currentdate.replaceAll("/", "");
            
            //System.out.println("Strart date=====" + delstartdate);
            //System.out.println("target date=====" + deltargetdate);

            Calendar st = new GregorianCalendar(Integer.parseInt(delstartdate.substring(4, 8)), Integer.parseInt(delstartdate.substring(2, 4)), Integer.parseInt(delstartdate.substring(0, 2)));
            Calendar tg = new GregorianCalendar(Integer.parseInt(deltargetdate.substring(4, 8)), Integer.parseInt(deltargetdate.substring(2, 4)), Integer.parseInt(deltargetdate.substring(0, 2)));
            Calendar cd = new GregorianCalendar(Integer.parseInt(delcurdate.substring(0, 4)), Integer.parseInt(delcurdate.substring(4, 6)), Integer.parseInt(delcurdate.substring(6, 8)));

            Pattern namePattern = Pattern.compile("[a-z[A-Z][\\s]]*");
            Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
            Pattern descPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");

            Matcher startMatcher = datePattern.matcher(startdate);
            //Matcher endMatcher=datePattern.matcher(enddate);
            Matcher targetMatcher = datePattern.matcher(targetdate);
            Matcher pnameMatcher = namePattern.matcher(projname);
            Matcher descMatcher = descPattern.matcher(pro_desc);

            if (startMatcher.matches() == false || validateDayMonth(startdate) == false || cd.before(st) == true ) {
                errors.rejectValue("start_date", "Invalid.strat_date", "Invalid Start Date");
            }
//        if (endMatcher.matches() == false || validateDayMonth(enddate) == false) {
//            errors.rejectValue("end_date", "Invalid.end_date", "Invalid End Date");
//        }
            if (targetMatcher.matches() == false || validateDayMonth(targetdate) == false) {
                errors.rejectValue("target_date", "Invalid.target_date", "Invalid Target Date");
            }

            if (tg.before(st) ) {
                errors.rejectValue("target_date", "Invalid.target_date", "Target Date can't be less than Start date");
            }
            
            if (pnameMatcher.matches() == false) {
                errors.rejectValue("proj_name", "Invalid.proj_name", "Invalid Project Name");
            }
            if (pro_desc.length() >= 3000) {
                errors.rejectValue("proj_desc", "Invalid.proj_desc", "Invalid Description");
            }
            if (descMatcher.matches() == false) {
                errors.rejectValue("proj_desc", "Invalid.proj_desc", "can not accept < and >");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateDayMonth(String date) {
        boolean isValid = false;

        String[] dateArray = date.split("/");

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
}
