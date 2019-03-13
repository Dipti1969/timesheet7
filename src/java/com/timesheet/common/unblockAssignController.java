/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.common;

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
public class unblockAssignController extends SimpleFormController {

   private ManageService manageService;

    public unblockAssignController() {
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
            manageService.unblockAssign(id);
            String msg = " Assign By is Unblocked";
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
