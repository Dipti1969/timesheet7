<%--
    Document   : displayUser
    Created on : Jan 4, 2010, 10:26:09 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="com.timesheet.bean.AssignBy" %>
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
            function deleteAssignBy(aid){
                var id = aid;
                var ans = window.confirm("Are you sure- Do you want to delete?");
                if(ans == true){
                    document.location.href = "deleteassignby.htm?id="+id;
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
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage Assign By</font></center>
        </div>
        <form name="manageAssign" method="post" >
            <div class="anotherOne bg-white" style="min-height:280px;_height:280px;">
                <table>
                    <tr>
                        <td>
                            <a title="Add Assign By" href="addassignby.htm" style="text-decoration:none;color:#520000;font-size:14px;_margin-left:0px;"><img title="Add AssignBy" height="13px" width="13px" src="images/add.jpg" border="0"style="text-decoration:none"/>Add Assign By</a>
                        </td>
                    </tr>
                    <tr>
                        <td style="color:green; font-size:16px; font-weight:bold;">
                            <c:out value="${a.msg}"/>
                        </td>
                    </tr>
                </table>
                <table class="dataTable" style="text-align:left;">
                    <tr>
                        <td>
                            <display:table name="${a.assign}"
                                           id="currentRowObject" pagesize="10"
                                           class="dataTable3"  requestURI="manageassignby.htm">
                                <c:choose>
                                    <c:when test="${currentRowObject.status == 'F'}" >
                                        <display:column title="Assign By ID" headerClass="header1" style="text-align:center;"
                                                        property="assign_by_id"
                                                        >

                                        </display:column>
                                        <display:column property="assign_by_name" title="Assign By Name"
                                                        headerClass="header1"/>
                                        <display:column title="Action" headerClass="header1" style="text-align:center;">
                                            <a href="unblockassign.htm?assign_by_id=<%=((AssignBy)currentRowObject).getAssign_by_id() %>" ><img
                                                    title="Un Block" src="images/unblock.jpg"/></a> |
                                            <a onclick="javascript:deleteAssignBy(<%=((AssignBy)currentRowObject).getAssign_by_id() %>)" href="#"><img title="Delete" src="images/delete.gif"/></a>
                                            </display:column>
                                        </c:when>
                                        <c:otherwise>
                                            <display:column title="Assign By ID" headerClass="header1" style="text-align:center;"
                                                            property="assign_by_id"
                                                            >

                                        </display:column>
                                        <display:column property="assign_by_name" title="Assign By Name"
                                                        headerClass="header1"/>
                                        <display:column title="Action" headerClass="header1" style="text-align:center;">
                                            <a style="text-decoration:none;" href="updateassignby.htm?assign_by_id=<%=((AssignBy)currentRowObject).getAssign_by_id() %>">
                                                <img title="Edit" src="images/edit_icon.gif"/>
                                            </a> |
                                            <a href="blockassign.htm?assign_by_id=<%=((AssignBy)currentRowObject).getAssign_by_id() %>" ><img
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
