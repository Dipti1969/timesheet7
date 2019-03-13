<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="display" uri="http://displaytag.sourceforge.net/" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="java.util.*"%>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    TimeZone istTime = TimeZone.getTimeZone("IST");
    sdf.setTimeZone(istTime);
    String curdate = sdf.format(new Date());
%>
<% java.util.ArrayList<Employee> emplist = new java.util.ArrayList<Employee>();
    emplist = (java.util.ArrayList) request.getSession().getAttribute("emplist");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link rel="stylesheet" href="jquery.ui.core.css" />
        <link rel="stylesheet" href="jquery.ui.theme.css" />
        <link rel="stylesheet" href="jquery.ui.datepicker.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>
            /* All you have to do is to call
             * a box() function on a selection
             */

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
        </script>
        <script>
            function ViewTimeSheet(){
                var id = document.getElementById('empid').value;
                document.location.href="viewtimesheet.htm?emp_id="+id+"&type=withoutdate";
            }
            function editTimeSheet(){
                document.location.href="adminedit.htm";
            }
            function addTimeSheet(){
                document.location.href = "addadmintimesheet.htm";
            }
            function setFocus(){
                document.getElementById('empid').focus();
            }
            function focus1(){
                document.getElementById('viewtimesheet').focus();
            }
        </script>
    </head>

    <body onload="setFocus()">
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage TimeSheet</font></center></div>
        <form:form name="adminManageTimeSheet" commandName="admintimesheet">
            <div class="anotherOne bg-white" style="min-height:245px; _height:245px;">
                <div style="border:1px solid #520000; margin-top:30px; padding-top:10px; padding-bottom:10px;"  >
                    <table align="center">
                        <tr>
                            <td>
                                <input type="button" class="button" value="Add TimeSheet" onclick="addTimeSheet()" title="Add TimeSheet">
                            </td>
                            <td>
                                <input type="button" class="button" value="Incomplete Transaction" onclick="editTimeSheet()" title="Incomplete Transaction">
                            </td>
                        </tr>
                    </table>
                </div>

                <div style="border:1px solid #520000; margin-top:10px; padding-top:10px; padding-bottom:10px;">
                    <table align="center">
                        <tr>

                            <td>Employee Name</td>
                            <td>
                                <select style="border:1px solid #520000" id="empid" name="empid">
                                    <option value="0">--Select--</option>
                                    <% for (int i = 1; i < emplist.size(); i++) {
                                            Employee emp = (Employee) emplist.get(i);
                                    %>
                                    <c:set var="roll" value="<%=emp.getRole_id()%>"/>
                                    <c:set var="rollid" value="3"/>

                                    <c:if test="${roll ne rollid}">
                                        <option value="<%=emp.getEmp_id()%>"><%=emp.getEmp_fname()%></option>
                                    </c:if>
                                    <%}%>
                                </select>
                            </td>
                            <td colspan="2" align="center">
                                <input id="viewtimesheet" class="button" type="button" value="View TimeSheet" onclick="ViewTimeSheet()" title="View TimeSheet">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" align="center" style="color:red;"><c:out value="${msg}" /></td>
                        </tr>
                    </table>
                </div>
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
