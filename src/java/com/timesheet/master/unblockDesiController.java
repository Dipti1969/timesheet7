/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.master;

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
public class unblockDesiController extends SimpleFormController {

    private MasterService masterService;

    public unblockDesiController() {
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
            String desi_id = request.getParameter("desi_id");
            int id = Integer.parseInt(desi_id);
            masterService.unblockdesi(id);
            String msg = " Designation is Unblocked";
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("msg", msg);
            myModel.put("desi", masterService.selectdesi());
            return new ModelAndView("managedesi", "d", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
