<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.timesheet.bean.Department" %>
<%@page import="com.timesheet.bean.Role" %>
<%@page import="com.timesheet.bean.Designation" %>
<%@page import="com.timesheet.bean.Employee" %>

<%--<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            Employee employee = (Employee) request.getAttribute("VIEW_EMPLOYEE");
            if (employee == null) {
                employee = new Employee();
            }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeSheet</title>
        <link rel="stylesheet" href="default.css" type="text/css" />
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });

        </script>
        <script language="javascript">
            function cancel()
            {
                document.location.href="manageuser.htm";

            }

        </script>

        <style>
            .error{
                color:red;
                font-style:italic;
            }
        </style>
    </head>

    <body>
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">View User</font></center></div>

        <form:form name="viewEmployee" commandName="viewemployee" action="manageuser.htm">
            <div class="anotherOne bg-white" style="padding-left:15px;min-height:250px;">
                <table width="35%">

                    <tr>
                        <td>UserID</td>
                        <td> <%= employee.getEmp_id()%> </td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><%= employee.getEmp_password() %> </td>
                    </tr>
                    <tr>
                        <td>FirstName</td>
                        <td> <%= employee.getEmp_fname()%></td>
                    </tr>
                    <tr>
                        <td>LastName</td>
                        <td><%= employee.getEmp_lname()%></td>
                    </tr>
                    <tr>
                        <td>Gender</td>
                        <td><%= employee.getGender()%></td>

                    </tr>
                    <tr>
                        <td>Department</td>
                        <td> <%= employee.getDept_name()%> </td>

                    </tr>
                    <tr>
                        <td>Designation</td>
                        <td> <%= employee.getDesi_name()%> </td>

                    </tr>
                    <tr>
                        <td>Email</td>
                        <td> <%= employee.getEmp_email()%></td>

                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><%= employee.getEmp_address()%></td>

                    </tr>
                    <tr>
                        <td>PhoneNumber</td>
                        <td><%= employee.getEmp_phone()%></td>
                    </tr>
                    <tr>
                        <td>MobileNumber</td>
                        <td><%= employee.getEmp_mobile()%></td>

                    </tr>
                    <tr>
                        <td>Birthdate</td>
                        <td><%= employee.getEmp_birthdate()%></td>

                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><input class="button" type="submit" value="Back" onClick="cancel()" style="float: left" title="Back"/></td>
                    </tr>
                </table>

            </div>
        </form:form>
    </body>
</html>
