<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Project" %>
<%@page import="com.timesheet.bean.AssignBy" %>
<%
    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("HH:mm");
    TimeZone istTime = TimeZone.getTimeZone("IST");
    sdf1.setTimeZone(istTime);
    String curtime = sdf1.format(new Date());
%>
<%
    java.util.ArrayList<AssignBy> assignbyList = new java.util.ArrayList<AssignBy>();
    java.util.ArrayList<Employee> proxyEmpList = new java.util.ArrayList<Employee>();
    java.util.ArrayList<Project> projectList = new java.util.ArrayList<Project>();

    assignbyList = (java.util.ArrayList) request.getSession().getAttribute("assign_by_list");
    proxyEmpList = (java.util.ArrayList) request.getSession().getAttribute("proxy_emp_list");
    projectList = (java.util.ArrayList) request.getSession().getAttribute("project_list");


%>
<%
    String assign = request.getParameter("assign_by_id");
    String proxy = request.getParameter("proxy_empid");
    String proj = request.getParameter("proj_id");
    //EmpTransaction et = (EmpTransaction) request.getSession().getAttribute("transid");
    int id = (Integer) request.getSession().getAttribute("transid");
    String start_time = (String) request.getSession().getAttribute("start_time");
%>
<c:set var="assignby" value="<%= assign%>" />
<c:set var="proxyemp" value="<%= proxy%>" />
<c:set var="projname" value="<%= proj%>" />


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <link rel="stylesheet" href="default.css" type="text/css" />

        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.adminaddtimesheet.end_time.focus();
            }
            function isNumberKey(evt){

                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31  && charCode!=99 && charCode!=118 && (charCode < 48 || charCode > 58))
                    return false;
                else
                    return true;

            }
            function back(){
                document.location.href = "adminmanagetimesheet.htm";
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
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Admin Edit TimeSheet</font></center>
        </div>
        <form:form name="adminaddtimesheet" commandName="adminedittimesheet" action="adminedittimesheet.htm">
            <div class="anotherOne bg-white" style="padding-left:15px;height:277px;">
                <div align="right" style="color:red">  Fields marked with  * are mandatory  </div>
                <table>
                    <tr>
                        <td></td>
                        <td><input style="border:1px solid #520000" type="hidden" size="20" name="trans_id" readonly value="<%=id%>"/></td>
                    </tr>
                    <tr>
                        <td>Start Time ssssssssssss</td>
                        <td>
                            <input style="border:1px solid #520000"  type="text" name="start_time" id="start_time" value="<%=start_time%>" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td>End Time<span style="color:red">* </span></td>
                        <td>
                            <input style="border:1px solid #520000" type="text" maxlength="8" name="end_time" value="<%=curtime%>"  onkeypress="return isNumberKey(event)" />
                            (HH:MM)/(24 Hour Format)&nbsp;<form:errors path="end_time" cssClass="error" />  </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <td>AssignBy Name<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000;" name="assign_by_id" >
                                <option value="0">--Select--</option>
                                <% for (int i = 0; i < assignbyList.size(); i++) {
                                        AssignBy ab = (AssignBy) assignbyList.get(i);
                                %>
                                <option value="<%=ab.getAssign_by_id()%>"  <c:set var="assignbyid" value="<%=ab.getAssign_by_id()%>"/> <c:if test="${assignbyid eq assignby}"> selected </c:if> ><%=ab.getAssign_by_name()%></option>
                                <%}%>
                            </select>
                            <form:errors path="assign_by_id" cssClass="error" />
                        </td>
                        <td>  </td>
                    </tr>
                    <tr>
                        <td>Proxy Employee Name</td>
                        <td>
                            <select style="border:1px solid #520000;" name="proxy_empid" >
                                <option value="0">--Select--</option>
                                <% for (int i = 1; i < proxyEmpList.size(); i++) {
                                        Employee emp = (Employee) proxyEmpList.get(i);

                                %>
                                <c:set var="roll" value="<%=emp.getRole_id()%>"/>
                                <c:set var="rollid" value="3"/>
                                <c:set value="<%= emp.getEmp_fname()%>" var="name"/>
                                <c:if test="${roll ne rollid}">
                                    <option value="<%=emp.getEmp_id()%>" <c:set var="proxyempname" value="<%=emp.getEmp_id()%>"/>  <c:if test="${proxyempname eq proxyemp}"> selected </c:if> > <c:out value="${name}"/> </option>
                                </c:if>
                                <%}%>

                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Project Name<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000;" name="proj_id" >
                                <option value="0">--Select--</option>
                                <% for (int i = 0;
                                            i < projectList.size();
                                            i++) {
                                        Project proj1 = (Project) projectList.get(i);
                                %>
                                <option value="<%=proj1.getProj_id()%>" <c:set var="projid" value="<%=proj1.getProj_id()%>"/> <c:if test="${projid eq projname}"> selected </c:if> ><%=proj1.getProj_name()%></option>
                                <%}%>
                            </select>
                            <form:errors path="proj_id" cssClass="error" />
                        </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <td>Work Description<span style="color:red">* </span></td>
                        <td><textarea style="border:1px solid #520000" cols="30" rows="3" name="work_desc"><%= request.getParameter("work_desc") == null ? "" : request.getParameter("work_desc")%></textarea>&nbsp;<form:errors path="work_desc" cssClass="error" /></td>
                        <td>  </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="button" type="submit" value="Save" title="Save">
                            <input class="button" type="reset" value="Reset" title="Reset Data">
                            <input class="button" type="button" value="Back" onclick="back()" title="Back">
                        </td>
                    </tr>
                </table>
            </div>
        </form:form>
    </body>
</html>
