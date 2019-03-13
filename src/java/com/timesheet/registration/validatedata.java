package com.timesheet.registration;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class validatedata extends HttpServlet {

    RegistrationService rs = new RegistrationServiceImpl();

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        RegistrationService rs = (RegistrationService) wac.getBean("registrationservice");

        String id = request.getParameter("emp_id");
        //System.out.println("id: " + id);
        boolean flag = rs.check(id);
        //System.out.println("flag in servler: " + flag);
        if (flag == false) {
            response.addHeader("flag", "no");
        } else {
            response.addHeader("flag", "yes");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
