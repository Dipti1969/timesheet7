<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<% String status = (String) session.getAttribute("et");%>
<% System.out.println("status in emphome.jsp: " + status);%>
<%@taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="java.util.Date" %>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy");
 TimeZone istTime = TimeZone.getTimeZone("IST");
    sdf.setTimeZone(istTime);
        String dt = sdf.format(new Date());
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link href="defalut.css" rel="stylesheet" type="text/css" />
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
            function checkStatus(stat){
                var status = stat;
                // alert("status:"+status);
                if(status == 'F'){
                    document.getElementById('timesheet').enabled = true;
                    document.getElementById('timein').disabled = true;
                    document.getElementById('timein').style.backgroundColor = "#90626D";
                }
                else if(status == 'T' || status == 'null'){
                    //  alert("level 2");
                    document.getElementById('timein').enabled = true;
                    document.getElementById('timesheet').disabled = true;
                    document.getElementById('timesheet').style.backgroundColor = "#90626D";
                }

            }
            function StartTime(){ 
                document.location.href = "addnewtransaction.htm";
            }
            function TimeSheetEntry(){
                document.location.href = "addtimesheet.htm";
            }
            function addTimeSheet(){
                document.location.href = "addrighttimesheet.htm";
            }

            function viewTimeSheet(){
                window.open("viewalltimesheet.htm");                
            }
        </script>
        <style>
            .textWrap{
                max-width:30px;
            }
        </style>
    </head>
    <body onload="checkStatus('<%=status%>')">
        <table>
            <tr>
                <td width="220px">
                    <div style="padding-left:10px; padding-top:5px; padding-bottom:10px;">
                        <c:set var="timer" value="${trans_list.time_right}"/>

                        <c:if test="${timer eq 'F'}">
                            <form:form id="trans" name="trans" commandName="transaction" method="post">
                                <input style="background-color:#520000; color:#FFFFFF; cursor:pointer;"  id="timein" name="timein" type="button" value="Start Time" onclick="StartTime()" title="Start Time">
                                <input style="background-color:#520000; color:#FFFFFF; cursor:pointer;"  id="timesheet" name="timesheet" type="button" value="Add Entry" onclick="TimeSheetEntry()" title="Add Entry">
                            </form:form>
                        </c:if>
                        <c:if test="${timer eq 'T'}">
                            <input style="background-color:#520000; color:#FFFFFF;" id="timesheetright" class="button" name="timesheetright" type="button" value="Add Entry" onclick="addTimeSheet()" title="Add Entry">
                        </c:if>
                    </div>
                </td>
                <td style="color:red;"><c:out value="${trans_list.msg1}"/><font color="green" size="4px"><c:out value="${trans_list.msg}"/></font></td>
            </tr>
        </table>
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Your TimeSheet for <%=dt%></font></center></div>
        <div style="min-height:230px; _height:270px;">
            <table class="dataTable" align="left" width="100%" cellpadding="2" style="text-align:left;">
               <tr>
                    <td>
                        <display:table name="${trans_list.curr_trans}"
                                       id="currentRowObject" pagesize="15"
                                       class="dataTable3" requestURI="emphome.htm">

                            <display:column property="start_time" title="Start Time"
                                            headerClass="header1" sortable="true"/>
                            <display:column property="end_time" title="End Time"
                                            headerClass="header1" sortable="true" />
                            <display:column property="hour" title="Duration"
                                            headerClass="header1" sortable="true"/>
                            <display:column property="assign_by_name" title="AssignBy"
                                            headerClass="header1" sortable="true"/>
                            <display:column property="proxy_empid" title="Proxy Name"
                                            headerClass="header1" sortable="true"/>
                            <display:column property="proj_name" title="Project"
                                            headerClass="header1" sortable="true"/>
                            <display:column property="work_desc" title="Work Description"
                                            headerClass="header1" class="textWrap" sortable="true" />
                            <display:column style="text-align:center;"title="Action" paramId="trans_id" headerClass="header1">
                                <a href="updatetimesheet.htm?trans_id=<%=((EmpTransaction) currentRowObject).getTrans_id()%>&chk=emphome">
                                    <img title="Edit" src="images/edit_icon.gif"/>
                                </a>
                            </display:column>
                            <display:footer>
                        <tr style="font-weight:bold;">
                            <td colspan="2" align="right" >Total Hours- </td>
                            <td><c:out value="${trans_list.totalhours}" /></td>
                        </tr>
                    </display:footer>
                    <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                </display:table></td>
                </tr>
            </table>
        </div>
    </body>
</html>
