<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Employee" %>
<%  java.util.ArrayList<String> yearList = new java.util.ArrayList<String>();
            java.util.ArrayList<Employee> employeeList = new java.util.ArrayList<Employee>();

            yearList = (java.util.ArrayList<String>) request.getSession().getAttribute("YEAR_LIST");
            employeeList = (java.util.ArrayList<Employee>) request.getSession().getAttribute("EMPLIST");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
        <link rel="stylesheet" href="default.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <script>

            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
        </script>

        <script language="javascript">
            function redirect(){
                var m = document.getElementById('monthlist').value;
                var y = document.getElementById('yearlist').value;

                if(m!=0 && y!=0){
                    document.forms.checktotalhours.submit();
                }else{
                    alert("Select Month and Year");
                }
            }
            function setFocus(){
                document.checktotalhours.monthlist.focus();
            }
        </script>
    </head>

    <body onload="setFocus()">
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Check Total Hours</font></center></div>
        <form:form name="checktotalhours" commandName="checktotalhours">
            <div class="anotherOne bg-white" style="min-height:245px; _height:240px;">
                <table align="center">
                    <tr>
                        <td>
                            <select id="monthlist" name="monthlist" style="border:1px solid #520000;">
                                <option value="0">Select Month</option>
                                <option value="01">January</option>
                                <option value="02">February</option>
                                <option value="03">March</option>
                                <option value="04">April</option>
                                <option value="05">May</option>
                                <option value="06">June</option>
                                <option value="07">July</option>
                                <option value="08">August</option>
                                <option value="09">September</option>
                                <option value="10">October</option>
                                <option value="11">November</option>
                                <option value="12">December</option>
                            </select>
                        </td>
                        <td>
                            <select id="yearlist" style="border:1px solid #520000;" name="yearlist">
                                <option value="0">Select Year</option>
                                <% for (int i = 0; i < yearList.size(); i++) {
            Object year = (Object) yearList.get(i);
                                %>
                                <option value="<%=year.toString().substring(3, 7)%>"><%=year.toString().substring(3, 7)%></option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <tr> <td></td> </tr>
                    
                    <tr>
                        <td colspan="2" align="center"><input class="button" type="button" value="View" onclick="redirect()"></td>
                    </tr>
                </table>
            </div>
        </form:form>
    </body>
</html>
