/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
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
public class AdminEditController extends SimpleFormController {

    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public AdminEditController() {
        

        setCommandClass(EmpTransaction.class);
        setCommandName("timesheerview");
        
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath = request.getServletPath();
        try {
            Map<String, Object> trans_status = new HashMap<String, Object>();
            trans_status.put("transactionstatus",timeSheetService.getStatus());
            return new ModelAndView("adminedit", "trans_status", trans_status);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("backpage", XPath);
            return new ModelAndView("error");
        }
    }
}
