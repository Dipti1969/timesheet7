/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.common;

import com.timesheet.master.*;
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
public class blockAssignController extends SimpleFormController {

    private ManageService manageService;

    public blockAssignController() {
    }

    public ManageService getManageService() {
        return manageService;
    }

    public void setManageService(ManageService manageService) {
        this.manageService = manageService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            String assign_by_id = request.getParameter("assign_by_id");
            int id = Integer.parseInt(assign_by_id);
            manageService.blockAssign(id);
            String msg = " Assign By is blocked";
            Map<String, Object> myModel = new HashMap<String, Object>();
            myModel.put("msg", msg);
            myModel.put("assign", manageService.select());
            return new ModelAndView("manageassignby", "a", myModel);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
    
 