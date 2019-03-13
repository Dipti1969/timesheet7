<%@page import="com.timesheet.bean.Employee" %>
<% Employee emp = (Employee) session.getAttribute("emp");%>
<% int roleid = emp.getRole_id(); %>
<% String right = emp.getTime_right(); %>
<% System.out.println("right: "+right);%>
<html>
    <head>
        <title></title>
        <link href="default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <% if (roleid == 1) {%>
        <table width="100%" style="border:2px solid #520000;">
            <tr class="navigation" >
                <td class="rollover" width="14%"><a title="Manage User" href="manageuser.htm" style="padding:4px 13px; color:#FFFFFF;">Manage&nbsp;User</a></td>
                <td class="rollover" width="15%"><a title="Manage Masters" href="managemasters.htm" style="padding:4px 10px; color:#FFFFFF;">Manage&nbsp;Masters</a></td>
                <td class="rollover" width="15%"><a title="Manage TimeSheet" href="adminmanagetimesheet.htm" style="padding:4px 0px; color:#FFFFFF;">Manage&nbsp;TimeSheet </a></td>
                <td class="rollover" width="15%"><a title="Password Management" href="changepassword.htm" style="padding:4px 2px; color:#FFFFFF;">Password&nbsp;Management</a></td>
                <td class="rollover" width="10%"><a title="View Reports" href="adminreport.htm" style="padding:4px 12px; color:#FFFFFF;">Reports</a></td>
                <td class="rollover" width="15%"><a title="Change Profile" href="changeprofile.htm" style="padding:4px 12px; color:#FFFFFF;">Change&nbsp;Profile</a></td>
            </tr>
        </table>
        <% } else if (roleid == 2) {%>
        <table width="100%" style="border:2px solid #520000;" >
            <tr class="navigation" >
                <%if(right.equals("T")){%>                
                <td class="rollover" width="17%">
                    <a title="Enter TimeSheet" href="addrighttimesheet.htm" style="padding:4px 4px; color:#FFFFFF;">Enter&nbsp;TimeSheet </a>
                </td>
                <%}else{%>               
                    <td class="rollover" width="17%">
                    <a title="Enter TimeSheet" href="emphome.htm" style="padding:4px 4px; color:#FFFFFF;">Enter&nbsp;TimeSheet </a>
                </td>
                <%}%>
                <td class="rollover" width="17%">
                    <!--a title="Manage TimeSheet" href="managetimesheet.htm" style="padding:4px 3px; color:#FFFFFF;">Manage&nbsp;TimeSheet </a-->
                     <a title="Manage TimeSheet" href="javascript:void(0)" style="pointer-events:none;padding:4px 3px; color:#FFFFFF;">Manage&nbsp;TimeSheet </a>
                </td>
                <td class="rollover" width="16%">
                    <a title="Check Total Hours" href="checktotalhours.htm" style="padding:4px 15px; color:#FFFFFF;">Check&nbsp;Hours</a>
                </td>
                <td class="rollover" width="16%">
                    <a title="Change Password" href="changepassword.htm" style="padding:4px 20px; color:#FFFFFF;">Change&nbsp;Password</a>
                </td>
                <td class="rollover" width="16%">
                    <a title="View Reports" href="employeereport.htm" style="padding:4px 40px; color:#FFFFFF;">Reports</a>
                </td>
                <td class="rollover" width="16%">
                    <a title="Change Profile" href="changeprofile.htm" style="padding:4px 25px; color:#FFFFFF;">Change&nbsp;Profile</a>
                </td>
            </tr>
        </table>
        <% } else if (roleid == 3) {%>
        <table width="100%" style="border:2px solid #520000;">
            <tr class="navigation" >
                <td class="rollover" width="33%"><a title="Report" href="receptionistreport.htm" style="padding:4px 109px; color:#FFFFFF;">Report</a></td>
                <td class="rollover" width="33%"><a title="Change Password" href="changepassword.htm" style="padding:4px 71px; color:#FFFFFF; " >Change&nbsp;Password</a></td>
                <td class="rollover" width="33%"><a title="Change Profile" href="changeprofile.htm" style="padding:4px 81px; color:#FFFFFF;" >Change&nbsp;Profile</a></td>
            </tr>
        </table>
        <% }%>
    </body>
</html>