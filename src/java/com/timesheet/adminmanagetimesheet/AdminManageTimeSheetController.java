package com.timesheet.adminmanagetimesheet;

import com.timesheet.bean.EmpTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AdminManageTimeSheetController extends SimpleFormController {

    private ManageTimeSheetService timeSheetService;

    public ManageTimeSheetService getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(ManageTimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public AdminManageTimeSheetController() {
        setCommandClass(EmpTransaction.class);
        setCommandName("admintimesheet");
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String XPath=request.getServletPath();
        try
        {
        HttpSession session = request.getSession(false);
        session.setAttribute("emplist", timeSheetService.getEmployees());
        return new ModelAndView("adminmanagetimesheet");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.setAttribute("backpage",XPath);
            return new ModelAndView("error");
        }
    }
}
