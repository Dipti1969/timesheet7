package com.timesheet.master;

import com.timesheet.bean.Department;
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
public class UpdateDeptController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }


    public UpdateDeptController() {
        setCommandClass(Department.class);
        setCommandName("updatedept");
        setFormView("updatedept");
        

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Department dept=(Department)super.formBackingObject(request);
          Object object = super.formBackingObject(request);
         try {
          
           Department department=new Department();
            String id=request.getParameter("dept_id");
            //System.out.println("ID===="+id);
            int deptid=Integer.parseInt(id);
            department=masterService.getById(deptid);
            HttpSession session = request.getSession();
            session.setAttribute("UPDATE_DEPT",department);
            return object;
         }
         catch(Exception e)
         {
             
           return null;
         }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            Department dept = (Department) command;
            String dept_id=request.getParameter("dept_id");
            int id=Integer.parseInt(dept_id);
            String dept_name = request.getParameter("dept_name");
            if (dept_name.equals("")) {
                String msg = "Enter Department";
                return new ModelAndView("updatedept", "msg", msg);
            } else {
                String str = "Department successfully updated.";
                masterService.update(id, dept_name);
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("dept",masterService.select());
                myModel.put("msg",str);
                return new ModelAndView("managedept", "d", myModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }
}
