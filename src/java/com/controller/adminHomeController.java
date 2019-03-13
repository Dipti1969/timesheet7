package com.controller;

import com.timesheet.util.LoggerUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class adminHomeController extends SimpleFormController{
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoggerUtils.info("Inside AdminHomeController=====>");
        String XPath=request.getServletPath();
        try
        {
        return new ModelAndView("adminhome");
        }
        catch(Exception e)
        {
            request.setAttribute("backpage",XPath);
            return new ModelAndView("error");
        }
    }

    @Override
    protected ModelAndView handleInvalidSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("adminhome");
    }



}
