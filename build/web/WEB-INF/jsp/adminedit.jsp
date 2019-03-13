<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="com.timesheet.bean.EmpTransaction" %>

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
            function back(){
                document.location.href = "adminmanagetimesheet.htm";
            }
        </script>
    </head>
    <body>
        <input class="button" type="button" value="Back" onclick="back()" title="Back">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Incomplete Transactions</font></center>
        </div>
        <div style="min-height:260px; _height:260px">
            <table class="dataTable" align="left" width="100%" cellpadding="2" style="text-align:left;">
                <tr>
                    <td>
                        <display:table name="${trans_status.transactionstatus}"
                                       id="currentRowObject"  pagesize="15"
                                       class="dataTable3"  requestURI="adminedit.htm">                            
                            <display:column property="emp_id" title="Employee ID"
                                            headerClass="header1" sortable="true" />
                            <display:column property="trans_date" title="Date"
                                            headerClass="header1" sortable="true" />
                            <display:column property="start_time" title="Start Time"
                                            headerClass="header1" sortable="true" />
                            <display:column title="Action"  headerClass="header1" style="text-align:center;">
                                <a href="adminedittimesheet.htm?trans_id=<%=((EmpTransaction) currentRowObject).getTrans_id()%>&start_time=<%=((EmpTransaction) currentRowObject).getStart_time()%>" ><img
                                        title="Edit TimeSheet" src="images/edit_icon.gif"/></a>
                                </display:column>

                            <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                        </display:table></td>
                </tr>
            </table>
        </div>
    </body>
</html>
