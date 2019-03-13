package com.timesheet.master;

import com.timesheet.bean.Department;
import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author prodigy
 */
public class AddDeptController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    public AddDeptController() {
        setCommandClass(Department.class);
        setCommandName("adddept");


    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        LoggerUtils.info("In Class AddAssignByController.java --> onSubmit() Method :: Enter");
        String XPath = request.getServletPath();
        try {
            Department dept = (Department) command;
            String dept_name = request.getParameter("dept_name");
            if (dept_name.equals("")) {
                String msg = " Enter Department";
                return new ModelAndView("adddept", "msg", msg);
            } else {
                masterService.add(dept);
                String str = "Department successfully added.";
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("dept", masterService.select());
                myModel.put("msg",str);
                return new ModelAndView("managedept", "d", myModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("dept", masterService.select());
        return new ModelAndView("managedept", "d", myModel);
    }
}
