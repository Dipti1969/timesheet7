<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.timesheet.bean.Department" %>
<%@page import="com.timesheet.bean.Role" %>
<%@page import="com.timesheet.bean.Designation" %>
<%@page import="com.timesheet.bean.Employee" %>
<%
    Employee employee =(Employee)request.getSession().getAttribute("UPDATE_EMPLOYEE");
    System.out.println("The Id is===="+ employee.getEmp_id());
    
    java.util.ArrayList<Department> departmentList = new java.util.ArrayList<Department>();
    departmentList = (java.util.ArrayList) request.getSession().getAttribute("DEPARTMENT_LIST");
    java.util.ArrayList<Designation> designationList = new java.util.ArrayList<Designation>();
    designationList = (java.util.ArrayList) request.getSession().getAttribute("DESIGNATION_LIST");
    if(employee==null)
        {
            employee=new Employee();
        }
%>

<link rel="stylesheet" href="default.css" />
<link rel="stylesheet" href="jquery.ui.core.css" />
<link rel="stylesheet" href="jquery.ui.theme.css" />
<link rel="stylesheet" href="jquery.ui.datepicker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
<c:set var="dept_test" value="<%=employee.getDept_id() %>"/>
<c:set var="desi_test" value="<%=employee.getDesi_id() %>"/>
<c:set var="role1" value="<%=employee.getRole_id() %>" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeSheet</title>
    </head>
       <script>            
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.updateuser.fname.focus();
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31  && charCode!=40 && charCode!=41 && charCode!=99 && charCode!=118 && (charCode < 48 || charCode > 57) && (charCode!=45))
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
            function cancel()
            {
                document.location.href="manageuser.htm";

            }
            function changefocus()
            {
                document.updateuser.add.focus();
            }
        </script>

        <style>
            .error{
                color:red;
                font-style:normal;
            }
        </style>

    <body onload="loginfocus()">
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Edit User</font></center></div>
        <form:form method="POST" commandName="editemployee" action="updateuser.htm" name="updateuser">
            <div class="anotherOne bg-white" style="padding-left:15px;">
                  <div align="right" style="color:red">  Fields marked with  * are mandatory  </div>

                <table>
                    <tr>                       
                        <td><input style="border:1px solid #520000" type="hidden" size="20" name="emp_id" readonly value="<%= employee.getEmp_id()%>"/></td>
                        <td><input type="hidden" name="emp_fname" value="<%=employee.getEmp_fname() %>"></td>
                   </tr>
                    <tr>
                        <td>FirstName<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" type="text" maxlength="20" size="20" name="fname" value="<%= request.getParameter("fname") == null ? employee.getEmp_fname() : request.getParameter("fname")%>" onkeypress="return isCharKey(event)"/>&nbsp;<form:errors path="fname" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>LastName</td>
                        <td><input style="border:1px solid #520000" type="text" maxlength="20" size="20" name="emp_lname" value="<%= request.getParameter("emp_lname") == null ? employee.getEmp_lname() : request.getParameter("emp_lname")%>" onkeypress="return isCharKey(event)"/>&nbsp;<form:errors path="emp_lname" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>Gender<span style="color:red">* </span></td>
                        <td><input type="radio" <%=employee.getGender().equalsIgnoreCase("M") ? "checked" : ""%>  name="gender" value="M">Male
                            <input type="radio" name="gender" <%=employee.getGender().equalsIgnoreCase("F") ? "checked" : ""%> value="F">Female&nbsp;&nbsp;<form:errors path="gender" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>Department<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" id="dept_id" name="dept_id" value="<%= employee.getDept_id()%>" >
                                <option value="0">Select</option>
                                <%
            for (int i = 0; i < departmentList.size(); i++) {
                Department department = (Department) departmentList.get(i);
                                %>
                                <option value="<%=department.getDept_id()%>"<%=department.getDept_id()%><c:set var="dept" value="<%=department.getDept_id() %>"/> <c:if test="${dept eq dept_test}"> selected </c:if> ><%=department.getDept_name()%></option>
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
                        <td>Designation<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" name="desi_id" >
                                <option value="0">Select</option>
                                <%
            for (int i = 0; i < designationList.size(); i++) {
                Designation designation = (Designation) designationList.get(i);
                                %>
                                <option value="<%=designation.getDesi_id()%>" <c:set var="desi" value="<%=designation.getDesi_id() %>"/> <c:if test="${desi eq desi_test}"> selected </c:if>><%=designation.getDesi_name()%></option>
                                <%}%>
                            </select>&nbsp;<form:errors path="desi_id" cssClass="error" />
                        </td>
                        </tr>
                     <tr>
                        <td>
                            Allow Manually<br>TimeSheet?
                        </td>
                        <td>
                            <input type="checkbox" name="time_right" <c:set var="timer" value="<%=employee.getTime_right() %>" /> <c:if test="${timer eq 'T'}">checked</c:if>  value="T" />
                        </td>
                    </tr>
                    <tr>
                        <td>Email<span style="color:red">* </span></td>
                        <td><input style="border:1px solid #520000" maxlength="50" type="text" name="emp_email" size="20" value="<%= request.getParameter("emp_email") == null ? employee.getEmp_email() : request.getParameter("emp_email")%>" />&nbsp;<form:errors path="emp_email" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td><textarea style="border:1px solid #520000" name="emp_address" rows="2" cols="17"><%= request.getParameter("emp_address") == null ? employee.getEmp_address() : request.getParameter("emp_address")%></textarea>&nbsp;<form:errors path="emp_address" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td>PhoneNumber</td>
                        <td><input style="border:1px solid #520000"  maxlength="15" type="text" name="emp_phone" size="20" value="<%= request.getParameter("emp_phone") == null ? employee.getEmp_phone() : request.getParameter("emp_phone")%>" onkeypress="return isNumberKey(event)"></td>
                    </tr>
                    <tr>
                        <td>MobileNumber</td>
                        <td><input style="border:1px solid #520000" maxlength="15" type="text" name="emp_mobile" size="20" value="<%= request.getParameter("emp_mobile") == null ? employee.getEmp_mobile() : request.getParameter("emp_mobile")%>" onkeypress="return isNumberKey(event)"></td>
                    </tr>
                    <tr>
                        <td>Birthdate<span style="color:red">* </span></td>
                        <td><input id="datepicker1" style="border:1px solid #520000" type="text" name="emp_birthdate" size="20" maxlength="11" onchange="changefocus()" value="<%= request.getParameter("emp_birthdate") == null ? employee.getEmp_birthdate() : request.getParameter("emp_birthdate")%>">&nbsp;(DD/MM/YYYY)&nbsp;<form:errors path="emp_birthdate" cssClass="error"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input class="button" id="add" type="submit" value="Update" title="Update User"/>
                            <input class="button" type="button" value="Cancel" onclick="cancel()" title="Back">
                            <input class="button" type="reset" value="Reset" title="Reset Data"/>
                        </td>
                        <td></td>
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
