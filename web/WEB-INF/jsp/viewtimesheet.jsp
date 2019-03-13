<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@taglib prefix="display" uri="http://displaytag.sourceforge.net/" %>--%>
<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.timesheet.bean.Employee" %>
<%@page  import="com.timesheet.bean.EmpTransaction" %>
<%String empname = (String)session.getAttribute("empname"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link rel="stylesheet" href="default.css" type="text/css" />
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
        </script>
        <script language="javascript">
            function deleteTrans(tid,eid){
                var trans_id = tid;
                var empid = eid;                
                var ans = window.confirm("Are you sure want to delete this transaction?");
                if(ans == true){
                    document.location.href = "deletetransaction.htm?trans_id="+trans_id+"&empid="+empid;
                }
            }
            function back(){
                document.location.href = "adminmanagetimesheet.htm";
            }
            function changefocus(){
                document.getElementById('viewrecords').focus();
            }
            function ViewDateTimeSheet(){
                var trans_date = document.getElementById('datepicker').value;
                if(trans_date != ""){
                    document.location.href="viewtimesheet.htm?tr_date="+trans_date+"&type=withdate";
                }else{
                    alert("Enter Date");
                }
                
            }
        </script>
    </head>
    <body>
        <form:form name="viewTimeSheet" commandName="viewtimesheet" action="viewtimesheet.htm">
            
            <table width="100%">
                <tr>
                    <td align="left"><input class="button" type="button" value="Back" onclick="back()" title="Back"></td>
                    <td align="center" style="color:green; font-weight:bold;">
                        <c:out value="${monthlist.msg}"/>
                    </td>
                    <td align="right">
                        Search by Date:&nbsp;&nbsp;<input id="datepicker" style="border:1px solid #520000" type="text" maxlength="11" name="trans_date" size="20" onchange="changefocus()" >&nbsp;(DD/MM/YYYY)
                        <input id="viewrecords" type="button" class="button" onclick="ViewDateTimeSheet()" value="View Records" >
                    </td>
                </tr>
            </table>

            <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">TimeSheet for <%=empname %></font></center></div>
            <div style="min-height:250px; _height:270px;">
                <table class="dataTable" width="100%" align="center" style="text-align:left;">
                    <tr>
                        <td>
                            <display:table name="${monthlist.monthtransaction}"
                                           id="currentRowObject"  pagesize="15" sort="list"
                                           class="dataTable3"  requestURI="viewtimesheet.htm">
                                <display:column headerClass="header1" property="proj_name" title="Project" sortable="true"  ></display:column>
                                <display:column headerClass="header1" property="trans_date" title="Date" sortable="true" ></display:column>
                                <display:column headerClass="header1" property="start_time" title="Start Time" sortable="true"  ></display:column>
                                <display:column headerClass="header1" property="end_time" title="End Time" sortable="true" ></display:column>
                                <display:column headerClass="header1" property="hour" title="Duration" sortable="true"  ></display:column>
                                <display:column headerClass="header1" property="assign_by_name" title="AssignBy" sortable="true"  ></display:column>
                                <display:column headerClass="header1" property="proxy_empid" title="Proxy Name" sortable="true" ></display:column>
                                <display:column style="min-width:20px;" headerClass="header1" property="work_desc" title="Work Description" sortable="true"  ></display:column>
                                <display:column title="Action"  headerClass="header1" style="text-align:center;">
                                    <a href="updatetransaction.htm?trans_id=<%=((EmpTransaction) currentRowObject).getTrans_id()%>&emp_id=<%=((EmpTransaction) currentRowObject).getEmp_id() %>" >
                                        <img title="Edit" src="images/edit_icon.gif"/></a> |
                                    <a onclick="javascript:deleteTrans(<%=((EmpTransaction)currentRowObject).getTrans_id() %>,'<%=((EmpTransaction) currentRowObject).getEmp_id() %>')" href="#"><img title="Delete" src="images/delete.gif"/></a>
                                    </display:column>
                                    <display:setProperty name="basic.msg.empty_list" value="No Record Found"  />
                                    <display:setProperty name="paging.banner.items_name" value="Records" />

                                <display:footer>
                            <tr style="font-weight:bold;">
                                <td colspan="4" align="right" >Total Hours </td>
                                <td><c:out value="${monthlist.dur}"/></td>
                            </tr>
                        </display:footer>

                    </display:table>
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
                buttonText: 'Click here to get Calendar',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });

        });
    </script>
</html>
