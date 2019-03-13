<%@page import="java.util.*"%>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy");
    TimeZone istTime = TimeZone.getTimeZone("IST");
    sdf.setTimeZone(istTime);
    String dt = sdf.format(new Date());
%>
<%@page import="com.timesheet.bean.Employee" %>
<% Employee emp1 = (Employee) session.getAttribute("emp");%>

<div class="header">
    <table width="100%">
        <tr>
            <td rowspan="3">
                <div style="font-family:Arial; font-size:34px; color:#FFFFFF; padding-left:15px;"><img height="77px"  width="400px" src="images/prodigy_logo.jpg"></div>
            </td>
            <td align="right" colspan="2" >
                <div style="font-family:sans-serif; font-size:24px; font-weight:bold; color:#FFFFFF; padding-right:15px;">TimeSheet</div>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <div style="font-family:sans-serif; font-size:14px; font-weight:bold; color:#FFFFFF; padding-right:40px;"><%=dt%></div>
            </td>
            <td>
        <tr>
            <td> </td>
            <td align="right" style="font-weight:bold;">
                <% if (emp1 != null) {%>
                <%int roleid = emp1.getRole_id();%>
                Welcome, <%=emp1.getEmp_fname()%> |
                <% if (roleid == 1) {%>
                <a href="adminhome.htm" style="text-decoration:none; color:#FFFFFF;">Home</a>
                <% } else if (roleid == 2) {%>
                <a href="empindex.htm" style="text-decoration:none; color:#FFFFFF;">Home</a>
                <% } else {%>
                <a href="receptionisthome.htm" style="text-decoration:none; color:#FFFFFF;">Home</a>
                <% }%>
                | <a href="index.htm" style="text-decoration:none; color:#FFFFFF;">Logout</a>
                <% }%>
            </td>
        </tr>
        </td>
        </tr>
    </table>
</div>
