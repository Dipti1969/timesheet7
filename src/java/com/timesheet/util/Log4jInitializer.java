package com.timesheet.util;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitializer extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    public void init(ServletConfig config) throws ServletException {

        //System.out.println("Inside init method of " + this.getClass().getName());

        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("Log4j_Initialization_File");

        //System.out.println("Log4j Preperty File == " + (prefix + file));

        File f = new File(prefix + file);

        if (f != null && f.exists()) {
            PropertyConfigurator.configure(prefix + file);
            LoggerUtils.debug("Application Logger initialization is done successfully.");
            //System.out.println("Application Logger initialization is done successfully.");
        }//end of if
        else {
            //System.out.println("Application Logger initialization is failed.");
        }//end of else

    }//end of method - init
}//end of class - Log4jInitializer
