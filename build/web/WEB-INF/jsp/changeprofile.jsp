<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.timesheet.bean.Department" %>
<%@page import="com.timesheet.bean.Role" %>
<%@page import="com.timesheet.bean.Designation" %>
<%@page import="com.timesheet.bean.Employee" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            Employee employee = (Employee) request.getSession().getAttribute("UPDATE_PROFILE");
            System.out.println("The Id is====" + employee.getEmp_id());
            if (employee == null) {
                employee = new Employee();
            }

            Employee emp = (Employee) session.getAttribute("emp");
            int roleid = emp.getRole_id();
%>
<link rel="stylesheet" href="default.css" />
<link rel="stylesheet" href="jquery.ui.core.css" />
<link rel="stylesheet" href="jquery.ui.theme.css" />
<link rel="stylesheet" href="jquery.ui.datepicker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeSheet</title>

        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.Changeprofile.userid.focus();
            }
            function focus1()
            {
                document.getElementById('update').focus();
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && charCode!=40 && charCode!=41 && charCode!=99 && charCode!=118 && (charCode < 48 || charCode > 57)&& (charCode!=45))
                    return false;
                else
                    return true;
            }
            function isCharKey(evt)
            {	var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode!=46 && charCode!=45 && charCode!=95 && charCode > 32 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122) && (charCode < 48 || charCode > 57))
                    return false;
                else
                    return true;
            }
        </script>

        <style>
            .error{
                color:red;
                font-style:normal;
            }
        </style>
        <script language="javascript">
            function cancel(role){
                var roleid = role;
                if(roleid == 1){
                    document.location.href = "adminhome.htm";
                }else if(roleid == 2){
                    document.location.href = "empindex.htm";
                }else{
                    document.location.href = "receptionisthome.htm";
                }
            }
        </script>
    </head>

    <body onload="loginfocus()">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Change Profile</font></center>
        </div>
        <form:form name="Changeprofile" commandName="updateprofile" action="changeprofile.htm">
            <div class="anotherOne bg-white" >
                <div align="right" style="color:red">  Fields marked with  * are mandatory  </div>
                <input type="hidden" name="emp_id" value="<%=employee.getEmp_id()%>">
                <input type="hidden" name="emp_fname" value="<%=employee.getEmp_fname() %>">
                <table>
                    <tr>
                        <td>Login User Name<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" size="25" maxlength="20" name="userid" value="<%= request.getParameter("userid") == null ? employee.getEmp_id() : request.getParameter("userid")%>" /></td>
                        <td> <form:errors path="userid" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td>FirstName<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" size="25" maxlength="20" name="fname" value="<%= request.getParameter("fname") == null ? employee.getEmp_fname() : request.getParameter("fname")%>" onkeypress="return isCharKey(event)"/></td>
                        <td> <form:errors path="fname" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td>LastName</td>
                        <td><input style="border:1px solid #520000" type="text" size="25" maxlength="20" name="emp_lname" value="<%= request.getParameter("emp_lname") == null ? employee.getEmp_lname() : request.getParameter("emp_lname")%>" onkeypress="return isCharKey(event)"/></td>
                    </tr>
                    <tr>
                        <td>Gender<span style="color:red">* </span></td>
                        <td><input type="radio" <%=employee.getGender().equalsIgnoreCase("M") ? "checked" : ""%>  name="gender" value="M">Male
                            <input type="radio" name="gender" <%=employee.getGender().equalsIgnoreCase("F") ? "checked" : ""%> value="F">Female</td>
                        <td><form:errors path="gender" cssClass="error" /> </td>
                    </tr>

                    <tr>
                        <td>Email<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" name="emp_email" size="25" maxlength="50" value="<%= request.getParameter("emp_email") == null ? employee.getEmp_email() : request.getParameter("emp_email")%>" /></td>
                        <td> <form:errors path="emp_email" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td>Address<span style="color:red">* </span></td>
                        <td><textarea style="border:1px solid #520000" name="emp_address" rows="2" cols="22"><%= request.getParameter("emp_address") == null ? employee.getEmp_address() : request.getParameter("emp_address")%></textarea></td>
                        <td><form:errors path="emp_address" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td>PhoneNumber</td>
                        <td><input style="border:1px solid #520000" type="text" name="emp_phone" size="25" maxlength="15" value="<%= request.getParameter("emp_phone") == null ? employee.getEmp_phone() : request.getParameter("emp_phone")%>" onkeypress="return isNumberKey(event)"></td>
                    </tr>
                    <tr>
                        <td>MobileNumber<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" name="emp_mobile" maxlength="15" size="25" value="<%= request.getParameter("emp_mobile") == null ? employee.getEmp_mobile() : request.getParameter("emp_mobile")%>" onkeypress="return isNumberKey(event)"></td>
                        <td> <form:errors path="emp_mobile" cssClass="error" /> </td>

                    </tr>
                    <tr>
                        <td>Birthdate<span style="color:red">* </span></td>
                        <td><input id="datepicker1" style="border:1px solid #520000" type="text" onchange="focus1()" name="emp_birthdate" maxlength="11" size="25" value="<%= request.getParameter("emp_birthdate") == null ? employee.getEmp_birthdate() : request.getParameter("emp_birthdate")%>"></td>
                        <td>(DD/MM/YYYY)&nbsp;<form:errors path="emp_birthdate" cssClass="error" /> </td>
                        <td> </td>
                    </tr>

                    <tr>                        
                        <td colspan="2" align="right"><input class="button" id="update" type="submit" value="Update"/>
                            <input class="button" type="button" value="Cancel" onclick="cancel(<%=roleid%>)">
                            <input class="button" type="reset" value="Reset"/>
                        </td>
                        <td></td><td></td>
                    </tr>
                </table>

            </div>
        </form:form>
    </body>
    <script type="text/javascript">
        $(function() {
            $('#datepicker1').datepicker({
                showOn: 'button',
                buttonImage: 'images/calendar.gif',
                buttonText:'Click Here To Get Calendar',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });

        });
    </script>
</html>
