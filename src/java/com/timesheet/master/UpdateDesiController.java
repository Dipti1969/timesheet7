package com.timesheet.master;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
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
public class UpdateDesiController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }


    public UpdateDesiController() {
        setCommandClass(Designation.class);
        setCommandName("updatedesi");
        setFormView("updatedesi");
        

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Designation desi=(Designation)super.formBackingObject(request);
          Object object = super.formBackingObject(request);
         try {
          
           Designation designation=new Designation();
            String id=request.getParameter("desi_id");
            //System.out.println("ID===="+id);
            int desiid=Integer.parseInt(id);
            designation=masterService.getByDesi(desiid);
            HttpSession session = request.getSession();
            session.setAttribute("UPDATE_DESI",designation);
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
            Designation desi = (Designation) command;
            String desi_id=request.getParameter("desi_id");
            int id=Integer.parseInt(desi_id);
            String desi_name = request.getParameter("desi_name");
            if (desi_name.equals("")) {
                String msg = "Enter Designation";
                return new ModelAndView("updatedesi", "msg", msg);
            } else {
                String str = "Designation successfully updated.";
                masterService.updateDesi(id, desi_name);
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("desi",masterService.selectdesi());
                myModel.put("msg",str);
                return new ModelAndView("managedesi", "d", myModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }

    }
}
