<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.timesheet.bean.Employee" %>
<%@page  import="com.timesheet.bean.EmpTransaction" %>
<%@page import="java.util.Date"%>
<% java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("EEEE, dd-MMM-yyyy");
   String dt1 = sdf1.format(new Date());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link rel="stylesheet" href="default.css" type="text/css" />
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
        </script>
    </head>
    <body>
        <table width="100%">
            <tr><td><%@include file="/WEB-INF/common/header.jsp" %></td></tr>
            <tr><td><%@include file="/WEB-INF/jsp/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">TimeSheet for <%=dt1 %></font></center></div>
                    <form:form name="viewTimeSheet" commandName="viewalltimesheet" action="">
                        <div style="min-height:250px; _height:270px;">
                            <table class="dataTable" width="100%" align="center" style="text-align:left;">
                                <tr>
                                    <td>
                                        <display:table name="${trans_list.trans_list}"
                                                       id="currentRowObject" pagesize="7"
                                                       class="dataTable3" export="true"  requestURI="viewalltimesheet.htm">

                                            <display:column headerClass="header1" property="emp_id" title="Employee"   ></display:column>
                                            <display:column headerClass="header1" property="start_time" title="Start Time"  ></display:column>
                                            <display:column headerClass="header1" property="end_time" title="End Time"  ></display:column>
                                            <display:column headerClass="header1" property="proj_name" title="Project"   ></display:column>
                                            <display:column headerClass="header1" property="assign_by_name" title="AssignBy"  ></display:column>
                                            <display:column headerClass="header1" property="proxy_empid" title="Proxy Name"  ></display:column>
                                            <display:column style="min-width:20px;" headerClass="header1" property="work_desc" title="Work Description"   ></display:column>
                                            <display:setProperty name="basic.msg.empty_list" value="No Record Found"  />
                                            <display:setProperty name="paging.banner.items_name" value="Records" />

                                        </display:table>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form:form>
                </td>
            </tr>
            <tr><td><%@include file="/WEB-INF/common/footer.jsp" %></td></tr>
        </table>
</html>
