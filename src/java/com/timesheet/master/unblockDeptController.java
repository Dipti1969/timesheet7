/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.master;

import com.timesheet.project.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/** 
 *
 * @author prodigy
 */
public class unblockDeptController extends SimpleFormController {

    private MasterService masterService;

    public unblockDeptController() {
    }

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    

    

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
           String dept_id = request.getParameter("dept_id");
            int id = Integer.parseInt(dept_id);
            masterService.unblockdept(id);
            String msg = " Department is Unblocked";
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("dept", masterService.select());
            myModel.put("msg", msg);
            return new ModelAndView("managedept", "d", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
