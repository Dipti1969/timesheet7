<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.bean.Project" %>
<%@page import="com.timesheet.bean.AssignBy" %>
<%@page  import="com.timesheet.bean.EmpTransaction" %>
<%@page import="java.util.*"%>
<% 
    java.text.SimpleDateFormat sdfdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
    TimeZone istTime = TimeZone.getTimeZone("IST");      
    sdfdt.setTimeZone(istTime);   
    String dt = sdfdt.format(new Date());
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
    sdf.setTimeZone(istTime);   
    String curtime = sdf.format(new Date());
%>
<%  java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("EEEE, dd-MMM-yyyy");
    sdf1.setTimeZone(istTime);  
    String dt1 = sdf1.format(new Date());
%>
<%
  java.util.ArrayList<AssignBy> assignbyList = new java.util.ArrayList<AssignBy>();
  java.util.ArrayList<Employee> proxyEmpList = new java.util.ArrayList<Employee>();
  java.util.ArrayList<Project> projectList = new java.util.ArrayList<Project>();
  java.util.ArrayList<EmpTransaction> transList = new java.util.ArrayList<EmpTransaction>();

  assignbyList = (java.util.ArrayList) request.getSession().getAttribute("assign_by_list");
  proxyEmpList = (java.util.ArrayList) request.getSession().getAttribute("proxy_emp_list");
  projectList = (java.util.ArrayList) request.getSession().getAttribute("project_list");
  transList = (java.util.ArrayList) request.getSession().getAttribute("trans_list");
%>
<%
  String assign = request.getParameter("assign_by_id");
  String proxy = request.getParameter("proxy_empid");
  String proj = request.getParameter("proj_id");
%>
<% Employee employee = (Employee)session.getAttribute("emp"); %>
<% String empid = (String)employee.getEmp_id(); %>
<c:set var="assignby" value="<%= assign%>" />
<c:set var="proxyemp" value="<%= proxy%>" />
<c:set var="projname" value="<%= proj%>" />
<c:set var="empid" value="<%=empid %>" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" href="default.css" />
        <link rel="stylesheet" href="jquery.ui.core.css" />
        <link rel="stylesheet" href="jquery.ui.theme.css" />
        <link rel="stylesheet" href="jquery.ui.datepicker.css" />


 <!--script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script-->

        <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui.css" />
        <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>


        <link rel="stylesheet" href="default.css" type="text/css" />
        <script>

            $(document).ready(function() {
               
               
                $('#datepicker').datepicker({
                    showOn: 'button',
                    buttonImage: 'images/calendar.gif',
                    buttonText:'Click Here To Get Calendar',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true,
                    dateFormat:"dd/mm/yy",
                    minDate:-1,
                    maxDate:0
                     
                
                });
           
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
                
                                

                  
                
            });
            function loginfocus()
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
            
            function cancel(){
                document.location.href = "empindex.htm";
            }
            
            function redirectFocus(){
                document.getElementById('start_time').focus();
            }
            
            function isDateKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 32  && charCode!=99 && charCode!=118 && (charCode < 47 || charCode > 57))
                    return false;
                else
                    return true;
            }
            function focus1()
            {
                document.addtimesheet.start_time.focus();
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
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Add TimeSheet</font></center>
        </div>
        <form:form  name="addtimesheet" commandName="addrighttimesheet">
            <div class="anotherOne bg-white" style="padding-left:15px;min-height:277px;" >
                <div align="right" style="color:red;"> Fields marked with  * are mandatory </div>
                <input type="hidden" name="emp_id" value="<%=employee.getEmp_id()%>">
                <table>

                    <tr>
                        <td>Date<span style="color:red">* </span></td>
                        <td><input readonly id="datepicker" style="border:1px solid #520000" onchange="focus1()" type="text" maxlength="11" name="trans_date" size="20" value="<%= request.getParameter("trans_date") == null ? dt : request.getParameter("trans_date")%>" onkeypress="return isDateKey(event)" onchange="redirectFocus()" > (DD/MM/YYYY)&nbsp;<form:errors path="trans_date" cssClass="error" /> </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>Start Time<span style="color:red">* </span></td>
                        <td>
                            <input style="border:1px solid #520000" type="text" maxlength="8" name="start_time" id="start_time" value="<%=request.getParameter("start_time") == null ? curtime : request.getParameter("start_time")%>" onkeypress="return isNumberKey(event)" />
                            (HH:MM)/(24 Hour Format)
                            <form:errors path="start_time" cssClass="error" /> </td>
                    </tr>

                    <tr>
                        <td>End Time<span style="color:red">* </span></td>
                        <td>
                            <input style="border:1px solid #520000" type="text" maxlength="8" name="end_time" id="end_time" value="<%=request.getParameter("end_time") == null ? curtime : request.getParameter("end_time")%>" onkeypress="return isNumberKey(event)"/>
                            (HH:MM)/(24 Hour Format)
                            <form:errors path="end_time" cssClass="error" /> </td>
                    </tr>

                    <tr>
                        <td>AssignBy Name<span style="color:red">* </span></td>
                        <td>
                            <select style="border:1px solid #520000" id="assign_by_id" name="assign_by_id" >
                                <option value="0">--Select--</option>
                                <% for (int i = 0; i < assignbyList.size(); i++) {
                AssignBy ab = (AssignBy) assignbyList.get(i);
                                %>
                                <option value="<%=ab.getAssign_by_id()%>"  <c:set var="assignbyid" value="<%=ab.getAssign_by_id() %>"/> <c:if test="${assignbyid eq assignby}"> selected </c:if> ><%=ab.getAssign_by_name()%></option>
                                <%}%>
                            </select>
                            <form:errors path="assign_by_id" cssClass="error" /> </td>
                    </tr>

                    <tr>
                        <td>Proxy Employee Name</td>
                        <td>
                            <select style="border:1px solid #520000" id="proxy_empid" name="proxy_empid" >
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
                            <select style="border:1px solid #520000" id="proj_id" name="proj_id" >
                                <option value="0">--Select--</option>
                                <% for (int i = 0;
                    i < projectList.size();
                    i++) {
                Project proj1 = (Project) projectList.get(i);
                                %>
                                <option value="<%=proj1.getProj_id()%>" <c:set var="projid" value="<%=proj1.getProj_id() %>"/> <c:if test="${projid eq projname}"> selected </c:if> ><%=proj1.getProj_name()%></option>
                                <%}%>
                            </select>
                            <form:errors path="proj_id" cssClass="error" /> </td>
                    </tr>

                    <tr>
                        <td>Work Description<span style="color:red">* </span></td>
                        <td><textarea id="work_desc" style="border:1px solid #520000" cols="30" rows="3" name="work_desc"><%= request.getParameter("work_desc") == null || request.getParameter("work_desc") == "" ? "" : request.getParameter("work_desc")%></textarea>
                            <form:errors path="work_desc" cssClass="error" /> </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="button" type="submit" value="Save" title="Save" >
                            <input class="button" type="reset" value="Reset" title="Reset Data">
                            <input class="button" type="button" value="Cancel" onclick="cancel()" title="Cancel">
                        </td>
                    </tr>
                </table>

            </form:form>
            <div style="height:20px;">

            </div>
            <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">All Employee TimeSheet for <%=dt1%></font></center></div>

            <div style="min-height:250px; _min-height:270px;">
                <table class="dataTable" width="100%" align="center" style="text-align:left;">
                    <tr>
                        <td>

                            <display:table name="<%=transList %>"
                                           id="currentRowObject" pagesize="15"
                                           class="dataTable3" requestURI="addrighttimesheet.htm">
                                <c:set var="ee" value="${currentRowObject.emp_id}"/>
                                <c:set var="eee" value="${empid}" />
                                <c:choose>
                                    <c:when test="${ee eq eee}">
                                        <display:column headerClass="header1" property="emp_id" title="Employee" sortable="true"></display:column>
                                        <display:column headerClass="header1" property="start_time" title="Start Time" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="end_time" title="End Time" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="proj_name" title="Project"  sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="assign_by_name" title="AssignBy" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="proxy_empid" title="Proxy Name" sortable="true" ></display:column>
                                        <display:column style="min-width:20px;" headerClass="header1" property="work_desc" title="Work Description" sortable="true"  ></display:column>
                                        <display:column title="Action" headerClass="header1" style="text-align:center;">
                                            <a href="updatetimesheet.htm?trans_id=<%=((EmpTransaction) currentRowObject).getTrans_id()%>&chk=addrighttimesheet">
                                                <img title="Edit" src="images/edit_icon.gif"/>
                                            </a>
                                        </display:column>
                                    </c:when>
                                    <c:otherwise>
                                        <display:column headerClass="header1" property="emp_id" title="Employee" sortable="true"></display:column>
                                        <display:column headerClass="header1" property="start_time" title="Start Time" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="end_time" title="End Time" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="proj_name" title="Project"  sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="assign_by_name" title="AssignBy" sortable="true" ></display:column>
                                        <display:column headerClass="header1" property="proxy_empid" title="Proxy Name" sortable="true" ></display:column>
                                        <display:column style="min-width:20px;" headerClass="header1" property="work_desc" title="Work Description" sortable="true"  ></display:column>
                                        <display:column title="Action"  headerClass="header1" style="text-align:center;">
                                        </display:column>
                                    </c:otherwise>
                                </c:choose>
                                <display:setProperty name="basic.msg.empty_list" value="No Record Found"  />
                                <display:setProperty name="paging.banner.items_name" value="Records" />

                            </display:table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
    <script type="text/javascript">
    
     
    </script>
</html>
