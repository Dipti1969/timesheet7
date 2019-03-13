/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.validation;

import com.timesheet.bean.Employee;
import com.timesheet.changeprofile.ProfileService;
import com.timesheet.util.LoggerUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author prodigy
 */
public class ProfileValidatorController extends EmailValidator implements Validator {

    private ProfileService profileService;

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public boolean supports(Class clazz) {
        LoggerUtils.info("In Class UserValidatorController.java --> supports() Method :: Enter");
        return Employee.class.isAssignableFrom(clazz);

    }

    public void validate(Object target, Errors errors) {
        LoggerUtils.info("In Class UserValidatorController.java --> validate() Method :: Enter");
        try {
            Employee employee = (Employee) target;
            String emp_id = employee.getEmp_id();
            String birthdate = employee.getEmp_birthdate();
            String emp_fname = employee.getEmp_fname();
            String emplname = employee.getEmp_lname();
            String addr = employee.getEmp_address();
            String user_id = employee.getUserid();
            String fname = employee.getFname();

            Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
            Pattern namePattern = Pattern.compile("[a-z[A-Z][0-9][\\s][.]]*");
            Pattern addrPattern = Pattern.compile("[a-z[A-Z][0-9][ \\t\\n\\x0B\\f\\r][\\p{Punct}&&[^<>]]]*");

            Matcher fnameMatcher = namePattern.matcher(fname);
            Matcher lnameMatcher = namePattern.matcher(emplname);
            Matcher birthMatcher = datePattern.matcher(birthdate);
            Matcher addrMatcher = addrPattern.matcher(addr);
            Matcher emp_fnameMatcher = namePattern.matcher(emp_fname);

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "emp_fname.required");
            ValidationUtils.rejectIfEmpty(errors, "gender", "gender.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emp_mobile", "emp_mobile.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emp_address", "emp_address.required");

            List<Employee> em = profileService.checkUserId(user_id);
            List<Employee> em1 = profileService.checkFName(fname);
            
            if (em.size() > 0) {
                for (int i = 0; i < em.size(); i++) {
                    Employee e = em.get(i);
                    String name = e.getUserid();                    
                    if (!emp_id.equalsIgnoreCase(name)) {
                        errors.rejectValue("userid", "Invalid.userid", "User Name already exist");
                    }
                }
            }

            if (em1.size() > 0) {
                for (int i = 0; i < em1.size(); i++) {
                    Employee e2 = em1.get(i);
                    String name1 = e2.getFname();
                    //System.out.println("name1: "+name1);
                    //System.out.println("emp_fname: "+emp_fname);
                    if (!emp_fname.equalsIgnoreCase(name1)) {
                        errors.rejectValue("fname", "Invalid.fname", "First Name already exist");
                    }
                }
            }

            if (user_id.length() < 6) {
                errors.rejectValue("userid", "Invalid.userid", "User Name Must be grater than six charecter");
            }
            if (fnameMatcher.matches() == false) {
                errors.rejectValue("fname", "Invalid.emp_fname", "Invalid First Name");
            }
            if (lnameMatcher.matches() == false) {
                errors.rejectValue("emp_lname", "Invalid.emp_lname", "Invalid Last Name");
            }
            if (EmailValidator.getInstance().isValid(employee.getEmp_email()) == false) {
                errors.rejectValue("emp_email", "Invalid.emp_email", "Please Enter The Correct Email");
            }

            try {
                if (birthdate.equals("")) {
                    ValidationUtils.rejectIfEmpty(errors, "emp_birthdate", "emp_birthdate.required");
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date date = new java.util.Date();
                    String currentdate = dateFormat.format(date);
                    String delcurdate = currentdate.replaceAll("/", "");
                    String delbirthdate = birthdate.replaceAll("/", "");
                    if (birthMatcher.matches() == true && validateDayMonth(birthdate) == true) {
                        Calendar cd = new GregorianCalendar(Integer.parseInt(delcurdate.substring(0, 4)), Integer.parseInt(delcurdate.substring(4, 6)), Integer.parseInt(delcurdate.substring(6, 8)));
                        Calendar birthd = new GregorianCalendar(Integer.parseInt(delbirthdate.substring(4, 8)), Integer.parseInt(delbirthdate.substring(2, 4)), Integer.parseInt(delbirthdate.substring(0, 2)));
                        if (cd.before(birthd) == true) {
                            errors.rejectValue("emp_birthdate", "Invalid.emp_birthdate", "Invalid Birth Date");
                        }
                    } else {
                        errors.rejectValue("emp_birthdate", "Invalid.emp_birthdate", "Invalid Birth Date");
                    }
                }

            } catch (Exception et) {
                et.printStackTrace();
            }
            if (addr.length() >= 2000) {
                errors.rejectValue("emp_address", "Invalid.emp_address", "Invalid Address");
            }
            if (addrMatcher.matches() == false) {
                errors.rejectValue("emp_address", "Invalid.emp_address", "can not accept < and >");
            }
            LoggerUtils.info("Error in Validator: " + errors.getAllErrors());
            LoggerUtils.info("In Class UserValidatorController.java --> validate() Method :: Exit");
        } catch (Exception e) {
            e.printStackTrace();
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
