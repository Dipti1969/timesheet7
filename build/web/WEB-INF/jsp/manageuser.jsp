<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="java.util.ArrayList"%>
<%@page import ="javax.servlet.http.HttpServletRequest"%>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.registration.RegistrationService" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <style>

            .FColor a{
                color:#000000;
            }
        </style>
        <script language="javascript">
            
            function test(userId,serverPath){
                var xmlhttp=false;
                try {
                    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (E) {
                        xmlhttp = false;
                    }
                }
                if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
                    try {
                        xmlhttp = new XMLHttpRequest();
                    } catch (e) {
                        xmlhttp=false;
                    }
                }
                if (!xmlhttp && window.createRequest) {
                    try {
                        xmlhttp = window.createRequest();
                    } catch (e) {
                        xmlhttp=false;
                    }
                }
                url=serverPath+"/validatedata?emp_id="+userId;
                xmlhttp.open("HEAD", url,true);
                xmlhttp.onreadystatechange=function() {
                    if (xmlhttp.readyState==4) {                        
                        if(xmlhttp.getResponseHeader("flag")=="yes" ){                           
                            var ans = window.confirm("User related transactions found - Do you want to delete this user?");                            
                            if(ans == true){
                                document.location.href = "deleteuser.htm?empid="+userId;
                            }
                        }else{
                            var ans = window.confirm("User related transactions not found - Do you want to delete this user?");
                            if(ans == true){
                                document.location.href = "deleteuser.htm?empid="+userId;
                            }
                        }
                    }
                }
                xmlhttp.send(null);
            }

            function getXmlHttpRequest() {
                var xmlhttp=false;
                try {
                    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (E) {
                        xmlhttp = false;
                    }
                }

                if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
                    try {
                        xmlhttp = new XMLHttpRequest();
                    } catch (e) {
                        xmlhttp=false;
                    }
                }
                if (!xmlhttp && window.createRequest) {
                    try {
                        xmlhttp = window.createRequest();
                    } catch (e) {
                        xmlhttp=false;
                    }
                }
                return xmlhttp;
            }
        </script>
    </head>
    <body>
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage User</font></center>
        </div>
        <form name="displayuser" method="post" >
            <div style="min-height:250px;">
                <table class="dataTable" align="center" width="100%"  style="text-align:left;">
                    <tr style="padding-top:5px;">
                        <td style="font-weight:bold;">
                            <a title="Add User" href="registration.htm" style="text-decoration:none; color:#520000; font-size:14px;"><img width="13px" height="13px" title="Add User" src="images/add.jpg"/> Add User</a>
                        </td>
                        <td style="color:green; margin-left:5px; font-size:16px; font-weight:bold;">
                            <c:out value="${e.msg}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="border-top:1px solid #520000;">
                            <display:table name="${e.employees}" id="currentRowObject" defaultsort="1" pagesize="15"
                                           class="dataTable3" requestURI="manageuser.htm" >
                                <c:choose>
                                    <c:when test="${currentRowObject.emp_status == 'F'}" >
                                        <display:column title="Emp ID" sortable="true"
                                                        property="emp_id" class="FColor" 
                                                        headerClass="header1" paramId="emp_id" href="viewuser.htm" >
                                        </display:column>
                                        <display:column property="emp_password" headerClass="header1" title="Password"
                                                        sortable="true"/>
                                        <display:column property="emp_fname" headerClass="header1" title="FirstName"
                                                        class="FColor" sortable="true" />
                                        <display:column property="dept_name" headerClass="header1" title="Department"
                                                        class="FColor" sortable="true" />
                                        <display:column property="desi_name" headerClass="header1" title="Designation"
                                                        class="FColor" sortable="true" />                                        
                                        <display:column property="emp_mobile" headerClass="header1" title="Mobile"
                                                        class="FColor" sortable="true" />
                                        <display:column property="emp_birthdate" headerClass="header1" title="BirthDate"
                                                        class="FColor" sortable="true" />
                                        <display:column title="Action"  headerClass="header1" class="FColor" style="text-align:center;">
                                            <a href="unblockuser.htm?emp_id=<%=((Employee)currentRowObject).getEmp_id()%>" ><img
                                                    title="UnBlock" src="images/unblock.jpg"/></a> |
                                            <a href="#" onclick="javascript:test('<%=((Employee)currentRowObject).getEmp_id()%>','${pageContext.request.contextPath}')"><img title="Delete" src="images/delete.gif"/></a>
                                            </display:column>
                                        </c:when>
                                        <c:otherwise>
                                            <display:column title="Emp ID" sortable="true"
                                                            property="emp_id"
                                                            headerClass="header1" paramId="emp_id"  href="viewuser.htm" >
                                            </display:column>
                                            <display:column property="emp_password" headerClass="header1" title="Password"
                                                            sortable="true"/>
                                            <display:column property="emp_fname" headerClass="header1" title="FirstName"
                                                            sortable="true"/>
                                            <display:column property="dept_name" headerClass="header1" title="Department"
                                                            sortable="true"/>
                                            <display:column property="desi_name" headerClass="header1" title="Designation"
                                                            sortable="true"/>                                            
                                            <display:column property="emp_mobile" headerClass="header1" title="Mobile"
                                                            sortable="true"/>
                                            <display:column property="emp_birthdate" headerClass="header1" title="BirthDate"
                                                            sortable="true"/>


                                        <display:column title="Action"  headerClass="header1" style="text-align:center;">
                                            <a href="updateuser.htm?emp_id=<%=((Employee)currentRowObject).getEmp_id()%>"><img
                                                    title="Edit" src="images/edit_icon.gif"/></a> |
                                            <a  href="blockuser.htm?emp_id=<%=((Employee)currentRowObject).getEmp_id()%>"><img
                                                    title="Block" src="images/block.jpg"/> </a>

                                        </display:column>
                                    </c:otherwise>
                                </c:choose>
                                <display:setProperty name="basic.msg.empty_list" value="No Record Found" />
                                <display:setProperty name="paging.banner.items_name" value="Records" />
                            </display:table>
                        </td>

                    </tr>
                </table>               
            </div>                
        </form>       
    </body>
</html>
