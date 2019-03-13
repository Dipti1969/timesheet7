/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.changeprofile;

import com.timesheet.bean.Employee;
import com.timesheet.emptransaction.EmpTransactionService;
import com.timesheet.login.LoginService;
import com.timesheet.registration.RegistrationService;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class updateProfileController extends SimpleFormController {

    private ProfileService profileService;
    private RegistrationService registrationService;
    private EmpTransactionService empTransactionService;
    private LoginService loginService;

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public EmpTransactionService getEmpTransactionService() {
        return empTransactionService;
    }

    public void setEmpTransactionService(EmpTransactionService empTransactionService) {
        this.empTransactionService = empTransactionService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public updateProfileController() {
        setCommandClass(Employee.class);
        setCommandName("updateprofile");
        setSessionForm(true);
    }
/*
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        System.out.println("Into The showForm() Method........");
        ModelAndView mav=new ModelAndView();
        HttpSession session=request.getSession();
        Employee emp=(Employee)session.getAttribute("emp");
        String emp_id=emp.getEmp_id();
        Employee employee = (Employee)profileService.getByPk(emp_id);
       /* Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("profile", emp);
        mav.addObject("employee",employee);
        mav.addObject("changeprofile",true);
        return mav;

    }
*/
   @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        LoggerUtils.info("In Class updateProfileController.java --> formBackingObject() Method :: Enter");
        try {
            Object object = super.formBackingObject(request);
            Employee employee = new Employee();
            HttpSession session = request.getSession();
            Employee emp = (Employee) session.getAttribute("emp");
            String emp_id = emp.getEmp_id();
           // System.out.println("The Employee Id from formBackingObject() is==" + emp_id);
            employee = profileService.getByPk(emp_id);
            session.setAttribute("UPDATE_PROFILE", employee);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            Employee employee = new Employee();
            String msg = "Your Profile has been successfully updated";
            String emp_id = request.getParameter("emp_id");
           // System.out.println("dooooooooo"+emp_id);
            String userid=request.getParameter("userid");
                       // System.out.println("doooonnnnnnnn_____ooooo"+userid);

            String emp_fname = request.getParameter("emp_fname");
            String fname = request.getParameter("fname");
            String emp_lname = request.getParameter("emp_lname");
            String gender = request.getParameter("gender");
            String emp_email = request.getParameter("emp_email");
            String emp_address = request.getParameter("emp_address");
            String emp_phone = request.getParameter("emp_phone");
            String emp_mobile = request.getParameter("emp_mobile");
            String emp_birthdate = request.getParameter("emp_birthdate");
            String emp_bdate = profileService.Insert_fmt_date(emp_birthdate);
            profileService.changeprofile(emp_id,userid,emp_fname, fname, emp_lname, gender, emp_email, emp_address, emp_phone, emp_mobile, emp_bdate);

            HttpSession session = request.getSession();
            Employee emp = (Employee) session.getAttribute("emp");
           // System.out.println("emppppppppppppppppppppppppppppppppppppppppppp" +emp);
            emp.setEmp_id(userid);
            //System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +emp);

            
            emp.setEmp_fname(emp_fname);
            session.setAttribute("emp", emp);
            int id = emp.getRole_id();
            if (id == 1) {
                return new ModelAndView("adminhome", "msg", msg);
            } else if (id == 2) {
                String stat = loginService.getStatus(userid);
                session.setAttribute("et", stat);
                Map<String, Object> emp_trans_list = new HashMap<String, Object>();
                
                emp_trans_list.put("msg", msg);
                return new ModelAndView("empindex", "trans_list", emp_trans_list);
            } else if (id == 3) {
                return new ModelAndView("receptionisthome", "msg", msg);
            } else {
                return new ModelAndView("index");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            HttpSession session = request.getSession();
            Employee emp = (Employee) session.getAttribute("emp");
            String emp_id = emp.getEmp_id();
            int id = emp.getRole_id();
            if (id == 1) {
                return new ModelAndView("adminhome");
            } else if (id == 2) {                
                return new ModelAndView("empindex");
            } else if (id == 3) {
                return new ModelAndView("receptionisthome");
            } else {
                return new ModelAndView("index");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
