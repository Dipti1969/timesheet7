<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import ="javax.servlet.http.HttpServletRequest"%>
<%@page import="com.timesheet.bean.Project" %>

<html>
    <head>
        <title></title>
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
            function deleteProj(pid){
                var id = pid;
                var ans = window.confirm("Are you sure - Do you want to delete?");
                if(ans == true){
                    document.location.href = "deleteproject.htm?id="+id;
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
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage Project</font></center>
        </div>
        <form name="displayproject" method="post" action="">
            <table class="dataTable"  align="center" width="100%"  style="text-align:left;">
                <tr style="padding-top:5px;">
                    <td style="font-weight:bold;">
                        <a title="Add Project" href="addproject.htm" style="text-decoration:none;color:#520000; font-size:14px;"><img title="Add Project" height="13px" width="13px" src="images/add.jpg" border="0" />Add Project</a>
                    </td>
                    <td style="color:green; margin-left:5px;font-size:16px; font-weight:bold;">
                        <c:out value="${proj_list.msg}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="border-top:1px solid #520000; ">
                        <display:table name="${proj_list.projects}"
                                       id="currentRowObject" pagesize="15" defaultsort="1"
                                       class="dataTable3" requestURI="manageproject.htm">
                            <c:choose>
                                <c:when test="${currentRowObject.status == 'F'}" >

                                    <display:column title="ProjectID" sortable="true"
                                                    property="proj_id" headerClass="header1"
                                                    href="viewproject.htm" paramId="proj_id" >
                                    </display:column>
                                    <display:column property="proj_name" title="ProjectName"
                                                    headerClass="header1" sortable="true" />
                                    <display:column property="start_date" title="StartDate"
                                                    headerClass="header1" sortable="true" />
                                    <display:column property="end_date" title="EndDate"
                                                    headerClass="header1" sortable="true" />
                                    <display:column property="target_date" title="TargetDate"
                                                    headerClass="header1" sortable="true" />
                                    <display:column property="proj_desc" title="ProjectDescription"
                                                    headerClass="header1" sortable="true" />
                                    <display:column title="Action"  headerClass="header1"  style="text-align:center;">
                                            <a href="unblockproject.htm?proj_id=<%=((Project)currentRowObject).getProj_id()%>" ><img
                                                    title="UnBlock" src="images/unblock.jpg"/></a> |
                                           <a onclick="javascript:deleteProj(<%=((Project)currentRowObject).getProj_id()%>)" href="#"><img title="Delete" src="images/delete.gif"/></a>
                                            </display:column>
                                    </c:when>
                                    <c:otherwise>
                                        <display:column title="ProjectID" sortable="true"
                                                        property="proj_id" headerClass="header1"
                                                        href="viewproject.htm" paramId="proj_id" >
                                        </display:column>
                                        <display:column property="proj_name" title="ProjectName"
                                                        headerClass="header1" sortable="true" />
                                        <display:column property="start_date" title="StartDate"
                                                        headerClass="header1" sortable="true" />
                                        <display:column property="end_date" title="EndDate"
                                                        headerClass="header1" sortable="true" />
                                        <display:column property="target_date" title="TargetDate"
                                                        headerClass="header1" sortable="true" />
                                        <display:column property="proj_desc" title="ProjectDescription"
                                                        headerClass="header1" sortable="true" />
                                        <display:column title="Action" headerClass="header1" style="text-align:center;">

                                        <a style="text-decoration:none;" href="updateproject.htm?proj_id=<%=((Project)currentRowObject).getProj_id() %>">
                                            <img title="Edit" src="images/edit_icon.gif"/>
                                        </a> |
                                        <a  href="blockproject.htm?proj_id=<%=((Project)currentRowObject).getProj_id()%>" ><img
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
        </form>
    </body>
</html>
