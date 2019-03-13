/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.common;

import com.timesheet.util.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author Admin
 */
public class ManageAssignByController extends SimpleFormController{

   private ManageService manageService;

    public ManageService getManageService() {
        return manageService;
    }

    public void setManageService(ManageService manageService) {
        this.manageService = manageService;
    }

   
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoggerUtils.info("In Class ManageAssignByController.java --> handleRequest() Method :: Enter");
        String XPath=request.getServletPath();
        try
        {
        Map<String,Object> myModel = new HashMap<String, Object>();
        myModel.put("assign",manageService.select());
        LoggerUtils.info("In Class ManageAssignByController.java --> handleRequest() Method :: Exit");
        return new ModelAndView("manageassignby","a",myModel);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.setAttribute("backpage",XPath);
            return new ModelAndView("error");
        }

    }
}
