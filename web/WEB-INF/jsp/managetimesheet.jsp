<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<% String status = (String) session.getAttribute("et");%>
<% System.out.println("status in emphome.jsp: " + status);%>
<%@taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Employee" %>
<% Employee emp = (Employee) session.getAttribute("emp");%>
<% int roleid = emp.getRole_id();%>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy");
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
    </head>
    <body>
        <table width="100%">
            <tr>
                <td><jsp:include page="/WEB-INF/common/header.jsp" /></td>
            </tr>
            <tr>
                <td><jsp:include page="/WEB-INF/jsp/menu.jsp" /></td>
            </tr>
            <tr>
                <td>
                    <div class="anotherOne">
                        <c:forEach items="${trans_list.curr_trans}" var="date">
                            <c:set value="${date.trans_date}" var="dt" />
                        </c:forEach>
                        <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage Your TimeSheet</font></center></div>
                        <form:form name="timesheet" commandName="managetimesheet" method="post" >
                        <div style="min-height:250px; _height:270px;">
                            <table class="dataTable" width="100%" cellpadding="0" style="text-align:left;">
                                <tr>
                                    <td><display:table name="${trans_list.curr_trans}"
                                                   id="currentRowObject" defaultsort="1" pagesize="10"
                                                   class="dataTable3"  requestURI="managetimesheet.htm">
                                            <display:column headerClass="header1" property="trans_date" title="Date"
                                                            />
                                            <display:column headerClass="header1" property="start_time" title="Start Time"
                                                            />
                                            <display:column headerClass="header1" property="end_time" title="End Time"
                                                            />
                                            <display:column headerClass="header1" property="hour" title="Duration"
                                                            />
                                            <display:column headerClass="header1" property="assign_by_name" title="AssignBy"
                                                            />
                                            <display:column headerClass="header1" property="proxy_empid" title="Proxy Name"
                                                            />
                                            <display:column headerClass="header1" property="proj_name" title="Project"
                                                            />
                                            <display:column maxLength="50" headerClass="header1" property="work_desc" title="Work Description"
                                                            />

                                            <display:column  headerClass="header1" title="Action" paramId="trans_id" style="text-align:center;">
                                                <a href="updatetimesheet.htm?trans_id=<%=((EmpTransaction) currentRowObject).getTrans_id()%>&chk=managetimesheet">
                                                    <img title="Edit" src="images/edit_icon.gif"/>
                                                </a>
                                            </display:column>
                                            <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                                            <display:setProperty name="paging.banner.item_name" value="Record" />
                                            <display:setProperty name="paging.banner.items_name" value="Records" />
                                            <display:footer>
                                        <tr style="font-weight:bold;">
                                            <td colspan="3"  align="right">Total Hours -</td>
                                            <td><c:out value="${trans_list.totalhours}" /></td>
                                        </tr>
                                    </display:footer>
                                </display:table>
                                </td>
                                </tr>

                            </table>
                            <div style="color:green; margin-left:20px;font-size:18px;">
                                <c:out value="${trans_list.msg}"/>
                            </div>
                        </div>
                    </form:form>
                </td>
            </tr>
            <tr>
                <td><jsp:include page="/WEB-INF/common/footer.jsp"/></td>
            </tr>
        </table>
    </body>
</html>
