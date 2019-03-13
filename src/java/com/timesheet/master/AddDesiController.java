package com.timesheet.master;

import com.timesheet.bean.Designation;
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
public class AddDesiController extends SimpleFormController {

    private MasterService masterService;

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    public AddDesiController() {
        setCommandClass(Designation.class);
        setCommandName("adddesi");


    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String XPath = request.getServletPath();
        try {
            Designation desi = (Designation) command;
            String desi_name = request.getParameter("desi_name");
            if (desi_name.equals("")) {
                String msg = " Enter Designation";
                return new ModelAndView("adddesi", "msg", msg);
            } else {
                String str = "Designation successfully added.";
                masterService.adddesi(desi);
                Map<String, Object> myModel = new HashMap<String, Object>();
                myModel.put("desi", masterService.selectdesi());
                myModel.put("msg",str);
                return new ModelAndView("managedesi", "d", myModel);
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
        myModel.put("desi", masterService.selectdesi());
        return new ModelAndView("managedesi", "d", myModel);
    }
}
