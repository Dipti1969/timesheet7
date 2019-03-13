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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EditAdminTimeSheetValidator implements Validator {

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
            //System.out.println("===== In EditAdminTimeSheetValidator ======");
            EmpTransaction empTransaction = (EmpTransaction) target;
            String emp_id = empTransaction.getEmp_id();
            String work_desc = empTransaction.getWork_desc();
            String start_time = empTransaction.getStart_time();
            String end_time = empTransaction.getEnd_time();
            String trans_date = empTransaction.getTrans_date();
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "work_desc", "work_desc.required");

            Pattern descPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");
            Pattern timepattern = Pattern.compile("([0-1]\\d|2[0-3]):([0-5]\\d)");
            Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");

            Matcher descMatcher = descPattern.matcher(work_desc);
            Matcher starttimematcher = timepattern.matcher(start_time);
            Matcher endtimematcher = timepattern.matcher(end_time);
            Matcher dateMatcher = datePattern.matcher(trans_date);

            /*  SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date dt = sdf.parse(trans_date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            String fmt_date = sdf1.format(dt);
            //System.out.println("Fmt_Date is===" + fmt_date);

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
            }*/
            if (starttimematcher.matches() == false || beforeTime(start_time, end_time) == true) {
                errors.rejectValue("start_time", "Invalid.Start_time", "Invalid Start Time");
            }

            if (endtimematcher.matches() == false) {
                errors.rejectValue("end_time", "Invalid.End_time", "Invalid End Time");
            }

            try {
                if (trans_date.equals("")) {
                    ValidationUtils.rejectIfEmpty(errors, "trans_date", "trans_date.required");
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date date = new java.util.Date();
                    String currentdate = dateFormat.format(date);
                    String delcurdate = currentdate.replaceAll("/", "");
                    String delbirthdate = trans_date.replaceAll("/", "");
                    if (dateMatcher.matches() == true && validateDayMonth(trans_date) == true) {
                        Calendar cd = new GregorianCalendar(Integer.parseInt(delcurdate.substring(0, 4)), Integer.parseInt(delcurdate.substring(4, 6)), Integer.parseInt(delcurdate.substring(6, 8)));
                        Calendar birthd = new GregorianCalendar(Integer.parseInt(delbirthdate.substring(4, 8)), Integer.parseInt(delbirthdate.substring(2, 4)), Integer.parseInt(delbirthdate.substring(0, 2)));
                        if (cd.before(birthd) == true) {
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
                errors.rejectValue("assign_by_id", "Invalid.assign_by_id", "Select The Assign By Name");
            }

            if (empTransaction.getProj_id() == 0) {
                errors.rejectValue("proj_id", "Invalid.proj_id", "Select The Project");
            }
            if (descMatcher.matches() == false) {
                errors.rejectValue("work_desc", "Invalid.work_desc", "can not accept < and >");
            }
            //System.out.println("All Erorrs: " + errors.getAllErrors());

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
            //System.out.println("into catch block ====");
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
}

