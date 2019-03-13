<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        TimeZone istTime = TimeZone.getTimeZone("IST");
        sdf.setTimeZone(istTime);
        String dt = sdf.format(new Date());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link href="default.css" rel="stylesheet" type="text/css" />
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
        </script>
        <script>
            function back(){
                document.location.href="checktotalhours.htm";
            }
        </script>
    </head>
    <body>
        <input type="button" class="button" value="Back" onclick="back()" />
        <div class="anotherOne"><center><font style="font-size:16px;font-weight:bold;" color="#FFFFFF">Your TimeSheet for <c:out value="${month_trans.month}"/> <c:out value="${month_trans.year}"/> </font></center></div>
        <div style="min-height:350px; _height:350px;">
            <table class="dataTable" align="left" width="100%" cellpadding="2" style="text-align:left;">
                <tr>
                    <td>
                        <display:table name="${month_trans.monthtransaction}" id="currentRowObject"
                                       pagesize="15" requestURI="checkhours.htm" class="dataTable3" >
                            <display:column property="trans_date" title="Date"
                                            headerClass="header1" sortable="true" />
                            <display:column property="start_time" title="Start Time"
                                            headerClass="header1" sortable="true" />
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
                            <display:footer>
                        <tr style="font-weight:bold;">
                            <td colspan="3" align="right" >Total Hours :</td>
                            <td><c:out value="${month_trans.totalhours}" /></td>
                        </tr>
                    </display:footer>
                    <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                </display:table>
                </td>
                </tr>
            </table>
        </div>
    </body>
</html>
