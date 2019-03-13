<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.Employee" %>
<% Employee emp = (Employee) session.getAttribute("emp");%>
<%int roleid = emp.getRole_id();%>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Employee" %>
<%  java.util.ArrayList<Employee> employeeList = new java.util.ArrayList<Employee>();
            employeeList = (java.util.ArrayList<Employee>) request.getSession().getAttribute("EMPLIST");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link rel="stylesheet" href="default.css" type="text/css" />
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.changepassFrm.oldpass.focus();
            }
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
            function resetPass(){
                var pass = document.getElementById('emp_password').value;
                var empid = document.getElementById('emplist').value;
                if(pass !="" && empid!=""){
                    document.location.href="resetpassword.htm?emp_password="+pass+"&emplist="+empid;
                }else{
                    alert("enter password or  select employee");
                }
                
            }
        </script>

        <title>Timeshet</title>

    </head>
    <body onload="loginfocus()">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Password Management</font></center>
        </div>
        <form:form name="changepassFrm" commandName="changepass" method="post">
            <div class="anotherOne bg-white" style="height: 230px;">
                <div align="right" style="color:red;"> Fields marked with  * are mandatory </div>
                <%if (roleid == 1) {%>
                <div style="border:1px solid #520000;">
                <%}%>
                    <table width="100%">
                        <tr>
                            <td>
                                <div>
                                    <table>
                                        <tr>
                                            <td colspan="2" align="center" bgcolor="#520000" style="color:#fff;">Change Your Password</td>
                                        </tr>
                                        <tr>
                                            <td>Old Password<span style="color:red">* </span></td>
                                            <td><input style="border:1px solid #520000" type="password" name="oldpass" maxlength="20"></td>


                                        </tr>
                                        <tr>
                                            <td>New Password<span style="color:red">* </span></td>
                                            <td><input style="border:1px solid #520000" type="password" name="newpass" maxlength="20"></td>


                                        </tr>
                                        <tr>
                                            <td>Confirm Password<span style="color:red">* </span></td>
                                            <td><input style="border:1px solid #520000" type="password" name="confirmpass" maxlength="20"></td>


                                        </tr>
                                        <tr>
                                            <td align="center" style="color:red;" colspan="2"><c:out value="${msg}" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>

                                            <td colspan="3" align="right"><input class="button" type="submit" value="Submit" title="Submit">
                                                <input class="button" type="button" value="Cancel" onclick="cancel(<%=roleid%>)" title="Back">
                                            </td>
                                        </tr>

                                    </table>
                                    <div style="color:green;font-size:18px;">
                                        <c:out value="${successmsg}" />
                                    </div>
                                </div>
                            </td>
                            <td style="vertical-align:top;">
                                <%if (roleid == 1) {%>
                                <div style="height:125px;">
                                    <table>
                                        <tr>
                                            <td colspan="2" align="center" bgcolor="#520000" style="color:#fff;">Reset User Password</td>
                                        </tr>
                                        <tr>
                                            <td>Employee<span style="color:red">* </span></td>
                                            <td>
                                                <select style="border:1px solid #520000; " id="emplist" name="emplist">
                                                    <option value="" >--Select--</option>
                                                    <%for (int i = 1; i < employeeList.size(); i++) {
        Employee emp1 = (Employee) employeeList.get(i);
                                                    %>
                                                    <c:set var="roll" value="<%=emp1.getRole_id()%>"/>
                                                    <c:set var="rollid" value="1"/>

                                                    <c:if test="${roll ne rollid}">
                                                        <option value="<%=emp1.getEmp_id()%>"><%=emp1.getEmp_fname()%><%=" "%> <%= emp1.getEmp_lname()%></option>
                                                    </c:if>
                                                    <%}%>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>New Password<span style="color:red">* </span></td>
                                            <td>
                                                <input style="border:1px solid #520000;" maxlength="20" id="emp_password" name="emp_password" type="password">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="right">
                                                <input class="button" type="button" value="Reset Password" onclick="resetPass()" title="Reset Employee Password">
                                                   <input class="button" type="button" value="Cancel" onclick="cancel(<%=roleid%>)" title="Back">
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <%}%>
                            </td>
                        </tr>
                    </table>
                 <%if (roleid == 1) {%>
                </div>
                <%}%>
            </div>
        </form:form>
    </body>
</html>
