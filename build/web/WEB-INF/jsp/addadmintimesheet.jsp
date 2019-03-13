<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("HH:mm");
    
    
    TimeZone istTime = TimeZone.getTimeZone("IST");      
    
    sdf.setTimeZone(istTime);   
    sdf1.setTimeZone(istTime);
    
    String dt = sdf.format(new Date());
    String curtime = sdf1.format(new Date());
    
    System.out.println(dt+"---------------"+curtime);
%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.bean.Project" %>
<%@page import="com.timesheet.bean.AssignBy" %>
<%    
    java.util.ArrayList<Employee> empList = new java.util.ArrayList<Employee>();
    java.util.ArrayList<AssignBy> assignbyList = new java.util.ArrayList<AssignBy>();
    java.util.ArrayList<Employee> proxyEmpList = new java.util.ArrayList<Employee>();
    java.util.ArrayList<Project> projectList = new java.util.ArrayList<Project>();
    
    assignbyList = (java.util.ArrayList) request.getSession().getAttribute("assign_by_list");
    proxyEmpList = (java.util.ArrayList) request.getSession().getAttribute("proxy_emp_list");
    projectList = (java.util.ArrayList) request.getSession().getAttribute("project_list");
    empList = (java.util.ArrayList) request.getSession().getAttribute("emp_list");
%>
<%    
    String assign = request.getParameter("assign_by_id");
    String proxy = request.getParameter("proxy_empid");
    String proj = request.getParameter("proj_id");
    String empid = request.getParameter("emp_id");
%>
<c:set var="assignby" value="<%= assign%>" />
<c:set var="proxyemp" value="<%= proxy%>" />
<c:set var="projname" value="<%= proj%>" />
<c:set var="empid" value="<%=empid%>" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" href="default.css" />
        <link rel="stylesheet" href="jquery.ui.core.css" />
        <link rel="stylesheet" href="jquery.ui.theme.css" />
        <link rel="stylesheet" href="jquery.ui.datepicker.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>

        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.addtimesheet.emp_id.focus();
            }
            function focus1()
            {
                document.addtimesheet.start_time.focus();
            }
            function isNumberKey(evt){

                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31  && charCode!=99 && charCode!=118 && (charCode < 48 || charCode > 58))
                    return false;
                else
                    return true;

            }
            function isDateKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 32  && charCode!=99 && charCode!=118 && (charCode < 47 || charCode > 57))
                    return false;
                else
                    return true;
            }
            function cancel(){
                document.location.href = "adminmanagetimesheet.htm";
            }
            function back(){
                document.location.href = "adminmanagetimesheet.htm";
            }

            function redirectFocus(){
                document.getElementById('start_time').focus();
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
        <input class="button" type="button" value="Back" onclick="back()" title="Back">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Add TimeSheet</font></center>
        </div>
        <form:form name="addtimesheet" commandName="addadmintimesheet">
            <div class="anotherOne bg-white" style="padding-left:15px;min-height:277px;" >
                <div style="color:red;" align="right"> Fields marked with  * are mandatory </div>
                <table>
                    <tr>
                        <td>Employee Name<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" name="emp_id" >
                                <option value="0">--Select--</option>
                                <% for (int i = 0; i < empList.size(); i++) {
                                        Employee e = (Employee) empList.get(i);
                                %>
                                <c:set var="roll" value="<%=e.getRole_id()%>"/>
                                <c:set var="rollid" value="3"/>

                                <c:if test="${roll ne rollid}">
                                    <option value="<%=e.getEmp_id()%>" <c:set var="empid1" value="<%=e.getEmp_id()%>"/> <c:if test="${empid1 eq empid}"> selected </c:if>><%=e.getEmp_fname()%></option>
                                </c:if>
                                <%}%>
                            </select>
                            <form:errors path="emp_id" cssClass="error" /> 
                        </td>
                        <td> </td>
                    </tr>
                    <tr>
                        <td>Date<span style="color:red">* </span></td>
                        <td><input  id="datepicker" style="border:1px solid #520000" onchange="focus1()" type="text" maxlength="11" name="trans_date" size="20" value="<%= request.getParameter("trans_date") == null ? dt : request.getParameter("trans_date")%>" onkeypress="return isDateKey(event)" onchange="redirectFocus()" > (DD/MM/YYYY)&nbsp;<form:errors path="trans_date" cssClass="error" /> </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Start Time<span style="color:red">* </span></td>
                        <td>
                            <input style="border:1px solid #520000" type="text" maxlength="8" name="start_time" id="start_time" value="<%=request.getParameter("start_time") == null ? curtime : request.getParameter("start_time")%>" onkeypress="return isNumberKey(event)" />
                            (HH:MM)/(24 Hour Format)&nbsp;<form:errors path="start_time" cssClass="error" />
                        </td>
                        <td>  </td>
                    </tr>

                    <tr>
                        <td>End Time<span style="color:red">* </span></td>
                        <td>
                            <input style="border:1px solid #520000" type="text" maxlength="8" name="end_time" id="end_time" value="<%=request.getParameter("end_time") == null ? curtime : request.getParameter("end_time")%>" onkeypress="return isNumberKey(event)"/>
                            (HH:MM)/(24 Hour Format) &nbsp;<form:errors path="end_time" cssClass="error" /></td>
                        <td>  </td>
                    </tr>
                    <tr>
                        <td>AssignBy Name<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" name="assign_by_id" >
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
                            <select style="border:1px solid #520000" name="proxy_empid" >
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
                            <select style="border:1px solid #520000" name="proj_id" >
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
                        <td><textarea style="border:1px solid #520000" cols="30" rows="3" name="work_desc"><%= request.getParameter("work_desc") == null ? "" : request.getParameter("work_desc")%></textarea> <form:errors path="work_desc" cssClass="error" /></td>
                        <td>  </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="button" type="submit" value="Save" title="Save">
                            <input class="button" type="button" value="Cancel" onclick="cancel()" title="Back">
                            <input class="button" type="reset" value="Reset"  title="Reset Data"/>
                        </td>
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
                buttonText:'Click Here To Get Calendar',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
                
            });

        });
    </script>
</html>
