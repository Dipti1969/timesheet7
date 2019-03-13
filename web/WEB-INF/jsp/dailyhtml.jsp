<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import ="javax.servlet.http.HttpServletRequest"%>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Employee" %>


<html>
    <head>
        <title>DAILY HTML REPORT</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="default.css" type="text/css" />
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

        <script type="text/javascript" language="javascript">

            function back(type,dt){

                 document.location.href = "adminreport.htm?type='"+type+"'&dt1="+dt+"";


            }


        </script>
        
    </head>
    <body>
         <table width="100%"><tr>
                <td><jsp:include page="/WEB-INF/common/header.jsp" /></td>
            </tr>
            <tr>
                <td><jsp:include page="/WEB-INF/jsp/menu.jsp" /></td>
            </tr></table>
        <input type="button" class="button" value="Back" onclick="back('${e.dType}','${e.dt1}')" title="Back">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Daily Report</font></center>
            
        </div>
        <form:form name="dailyhtml" commandName="dailyhtml" action="" method="POST">

            <table class="dataTable" align="center" width="100%" style="text-align:left;">

                <tr style="padding-top:5px;">

                    <td style="color:green; margin-left:5px;font-size:16px; font-weight:bold;">
                        <c:out value="${e.msg}"/>
                    </td>
                </tr>
                <tr>

                    <td colspan="2" style="border-top:1px solid #520000; ">
                   
                        <display:table class="dataTable" name="${e.employees}"
                                       id="currentRowObject" pagesize="15" defaultsort="1"
                                        requestURI="dailyhtml.htm">
                            

                                    <display:column title="EmployeeName" sortable="true"
                                                    headerClass="header1" property="emp_fname"
                                                     paramId="emp_id" >
                                    </display:column>
                                    <display:column property="hour" title="Hours" sortable="true" headerClass="header1"/>

                                    <display:column property="assign_by_name" title="AssignBy"
                                                    headerClass="header1" sortable="true" />
                                     <display:column property="proxy_empid" title="ProxyEmployee"
                                                     headerClass="header1" sortable="true" />
                                    <display:column property="proj_name" title="Project"
                                                     headerClass="header1" sortable="true" />
                                    <display:column property="dept_name" title="Department"
                                                    headerClass="header1" sortable="true" />
                                    <display:column property="work_desc" title="Work-Description"
                                                    headerClass="header1" sortable="true" />

                                <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                                <display:setProperty name="paging.banner.items_name" value="Records" />


<display:footer>
                            <tr style="font-weight:bold;">
                                <td colspan="1" align="left" >Total Hours </td>
                                <td><c:out value="${e.dur}"/></td>
                            </tr>
                            <tr><td></td></tr>
                        </display:footer>

                        </display:table>
                    </td>
                </tr>
                <tr><td><%@include file="/WEB-INF/common/footer.jsp" %></td></tr>
            </table>
        </form:form>
    </body>
</html>
