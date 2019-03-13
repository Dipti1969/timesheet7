<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.timesheet.bean.Department" %>
<%@page import="com.timesheet.bean.Role" %>
<%@page import="com.timesheet.bean.Designation" %>
<%
            String deptid = (String) request.getParameter("dept_id");
            String desiid = (String) request.getParameter("desi_id");
            String roleid = (String) request.getParameter("role_id");
            String gender1 = (String) request.getParameter("gender");
%>
<c:set var="dept1" value="<%= deptid %>" />
<c:set var="desi1" value="<%= desiid %>" />
<c:set var="role1" value="<%= roleid %>" />
<c:set var="gender2" value="<%= gender1%>" />
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

        <script type="text/javascript">
            
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });           
            function loginfocus()
            {
                document.employeeregistration.emp_id.focus();
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 32  && charCode!=40 && charCode!=41 && charCode!=99 && charCode!=118 && (charCode < 48 || charCode > 57) && (charCode!=45))
                    return false;
                else
                    return true;
            }
            function isCharKey(evt)
            {	var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode!=46 && charCode!=45 && charCode!=95 && charCode > 32 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122))
                    return false;
                else
                    return true;
            }
            function cancel()
            {
                document.location.href="manageuser.htm";

            }
             function changefocus()
            {
                document.employeeregistration.add.focus();
            }
        </script>        

        <style>
            .error{
                color:#ff0000;
                font-style:normal;
            }
        </style>
    </head>
    <body onload="loginfocus()">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Add User</font></center>
        </div>
        <form:form name="employeeregistration" commandName="addemployee">

            <div class="anotherOne bg-white" style="padding-left:15px;" >
                <div align="right" style="color:red">  Fields marked with  * are mandatory  </div>
                <table>
                    <tr>
                        <td>User ID<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" size="20" name="emp_id"  maxlength="20"  value="<%= request.getParameter("emp_id") == null ? "" : request.getParameter("emp_id")%>"/>&nbsp;<form:errors path="emp_id" cssClass="error"  /> </td>
                    </tr>
                    <tr>
                        <td>Password<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="password" size="20" name="emp_password"  maxlength="20" value="<%= request.getParameter("emp_password") == null ? "" : request.getParameter("emp_password")%>"/>&nbsp;<form:errors path="emp_password" cssClass="error"  /></td>
                    </tr>
                    <tr>
                        <td>First Name<span style="color:red">* </span></td>
                        <td align="left"><input style="border:1px solid #520000" type="text" maxlength="20" size="20" name="emp_fname" value="<%= request.getParameter("emp_fname") == null ? "" : request.getParameter("emp_fname")%>" onkeypress="return isCharKey(event)"/>&nbsp;<form:errors path="emp_fname" cssClass="error"  /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input style="border:1px solid #520000" type="text" size="20" maxlength="20" name="emp_lname" value="<%= request.getParameter("emp_lname") == null ? "" : request.getParameter("emp_lname")%>" onkeypress="return isCharKey(event)" />&nbsp; <form:errors path="emp_lname" cssClass="error"  /></td>
                    </tr>
                    <tr>
                        <td>Gender<span style="color:red">* </span></td>
                        <td>Male
                            <input type="radio" name="gender" value="M"  <c:if test="${'M' eq gender2}"> checked </c:if> /> Female<input type="radio" name="gender" value="F"   <c:if test="${'F' eq gender2}"> checked </c:if> />&nbsp;<form:errors path="gender" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>Department<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" id="dept_id" name="dept_id" >
                                <option value="0">Select</option>
                                <%
            java.util.ArrayList<Department> departmentList = new java.util.ArrayList<Department>();
            departmentList = (java.util.ArrayList) request.getSession().getAttribute("DEPARTMENT_LIST");
            for (int i = 0; i < departmentList.size(); i++) {
                Department department = (Department) departmentList.get(i);
                                %>

                                <option value="<%=department.getDept_id()%>" <c:set var="dept" value="<%=department.getDept_id() %>"/> <c:if test="${dept eq dept1}"> selected </c:if> ><%=department.getDept_name()%></option>
                                <%}%>
                            </select>&nbsp;<form:errors path="dept_id" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td>Role<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" name="role_id">
                                <option value="0">Select</option>
                                <%
            java.util.ArrayList<Role> roleList = new java.util.ArrayList<Role>();
            roleList = (java.util.ArrayList) request.getSession().getAttribute("ROLE_LIST");
            for (int i = 0; i < roleList.size(); i++) {
                Role role = (Role) roleList.get(i);
                                %>
                                <option value="<%=role.getRole_id()%>"  <c:set var="role" value="<%=role.getRole_id() %>"/> <c:if test="${role eq role1}"> selected </c:if> ><%=role.getRole_name()%></option>
                                <%}%>
                            </select>&nbsp;<form:errors path="role_id" cssClass="error" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Allow Manully<br>TimeSheet?
                        </td>
                        <td>
                            <input type="checkbox" name="time_right" value="T" />
                        </td>
                    </tr>
                    <tr>
                        <td>Designation<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" name="desi_id" >
                                <option value="0">Select</option>
                                <%
            java.util.ArrayList<Designation> designationList = new java.util.ArrayList<Designation>();
            designationList = (java.util.ArrayList) request.getSession().getAttribute("DESIGNATION_LIST");
            for (int i = 0; i < designationList.size(); i++) {
                Designation designation = (Designation) designationList.get(i);
                                %>
                                <option value="<%=designation.getDesi_id()%>"  <c:set var="desi" value="<%=designation.getDesi_id() %>"/> <c:if test="${desi eq desi1}"> selected </c:if> ><%=designation.getDesi_name()%></option>
                                <%}%>
                            </select>&nbsp;<form:errors path="desi_id" cssClass="error" />
                        </td>
                      </tr>
                    <tr>
                        <td>Email<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" maxlength="50" name="emp_email" size="20" value="<%=request.getParameter("emp_email") == null ? "" : request.getParameter("emp_email")%>"/>&nbsp;<form:errors path="emp_email" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><textarea  style="border:1px solid #520000" name="emp_address" rows="2" cols="17"><%= request.getParameter("emp_address") == null ? "" : request.getParameter("emp_address")%></textarea>&nbsp;<form:errors path="emp_address" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td>Phone Number</td>
                        <td><input style="border:1px solid #520000" type="text" name="emp_phone" size="20" maxlength="15" value="<%= request.getParameter("emp_phone") == null ? "" : request.getParameter("emp_phone")%>" onkeypress="return isNumberKey(event)"></td>

                    </tr>
                    <tr>
                        <td>Mobile Number</td>
                        <td><input style="border:1px solid #520000" type="text" name="emp_mobile" size="20" maxlength="15" value="<%= request.getParameter("emp_mobile") == null ? "" : request.getParameter("emp_mobile")%>" onkeypress="return isNumberKey(event)"></td>
                    </tr>
                    <tr>
                        <td>Birth Date<span style="color:red">* </span></td>
                        <td><input id="datepicker" style="border:1px solid #520000" type="text" maxlength="11" name="emp_birthdate" size="20" value="<%= request.getParameter("emp_birthdate") == null ? "" : request.getParameter("emp_birthdate")%>" onchange="changefocus()" >&nbsp;(DD/MM/YYYY)&nbsp;<form:errors path="emp_birthdate" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="button" type="submit" id="add" value="Add" title="Add User" />
                            <input class="button" type="button" value="Cancel" onclick="cancel()" title="Back" />
                            <input class="button" type="reset" value="Reset" title="Reset Data" />
                        </td>
                        <td></td>
                    </tr>
                </table>
            </div>
        </form:form>
    </body>
    <script type="text/javascript">
        $(function() {
            $('#datepicker').datepicker({
                showOn: 'button',
                buttonImage: 'images/calendar.gif',
                buttonText: 'Click here to get Calendar',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
           
        });
    </script>
</html>
