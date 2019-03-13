<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="com.timesheet.bean.Department" %>
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
        <script language="javascript">
            function deleteDept(aid){
                var id = aid;
                var ans = window.confirm("Are you sure - Do you want to delete?");
                if(ans == true){
                    document.location.href = "deletedept.htm?id="+id;
                }
            }
            function back(){
                document.location.href = "managemasters.htm";
            }
        </script>
    </head>
    <body>
        <input type="button" class="button" value="Back" onclick="back()" title="Back">
        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage Department</font></center>
        </div>
        <form name="managedept" method="post" >
            <div class="anotherOne bg-white" style="min-height:280px;_height:280px;">
                <table>
                    <tr>
                        <td>
                            <a title="Add Department" href="adddept.htm" style="text-decoration:none;color:#520000;font-size:14px;_margin-left:0px;">
                                <img title="Add Department" height="13px" width="13px" src="images/add.jpg" style="text-decoration:none"/>
                                Add Department
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td style="color:green; font-size:16px; font-weight:bold;">
                            <c:out value="${d.msg}"/>
                        </td>
                    </tr>
                </table>
                <table class="dataTable" style="text-align:left;">
                    <tr>
                        <td><display:table name="${d.dept}"
                                       id="currentRowObject" pagesize="15"
                                       class="dataTable3"  requestURI="managedept.htm">
                                <c:choose>
                                    <c:when test="${currentRowObject.status == 'F'}" >

                                        <display:column title="DepartmentID" headerClass="header1" style="text-align:center;"
                                                        property="dept_id" sortable="true"
                                                        >

                                        </display:column>
                                        <display:column property="dept_name" title="Department"
                                                        headerClass="header1" sortable="true"/>
                                        <display:column title="Action"  headerClass="header1"  style="text-align:center;">
                                            <a href="unblockdept.htm?dept_id=<%=((Department)currentRowObject).getDept_id() %>" ><img
                                                    title="UnBlock" src="images/unblock.jpg"/></a> |
                                            <a onclick="javascript:deleteDept(<%=((Department)currentRowObject).getDept_id() %>)" href="#"><img title="Delete" src="images/delete.gif"/></a>
                                            </display:column>
                                        </c:when>
                                        <c:otherwise>
                                            <display:column title="DepartmentID" headerClass="header1" style="text-align:center;"
                                                            property="dept_id" sortable="true" >

                                        </display:column>
                                        <display:column property="dept_name" title="Department"
                                                        headerClass="header1" sortable="true"/>
                                        <display:column title="Action" headerClass="header1" style="text-align:center;">
                                            <a style="text-decoration:none;" href="updatedept.htm?dept_id=<%=((Department)currentRowObject).getDept_id() %>">
                                                <img title="Edit" src="images/edit_icon.gif"/>
                                            </a> |
                                            <a href="blockdept.htm?dept_id=<%=((Department)currentRowObject).getDept_id() %>" ><img
                                                    title="Block" src="images/block.jpg"/></a>
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
