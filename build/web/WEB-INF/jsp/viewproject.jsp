<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    Project project =(Project)request.getAttribute("VIEW_PROJECT");
    if(project==null)
        {
            project=new Project();
        }
    String blank = "";
%>
  <%@page import="com.timesheet.bean.Project" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeSheet</title>
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
        function cancel()
            {
                document.location.href="manageproject.htm";
            }
        </script>
      <style>
            .error{
                color:red;
                font-style:italic;
            }
        </style>
    </head>
   <body>
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">View Project</font></center></div>

        <form:form name="viewProject" commandName="viewproject" action="manageproject.htm">
            <div class="anotherOne bg-white" style="padding-left:15px;height:230px;">
                <table>
                    <tr>
                        <td>Project ID: </td>
                        <td> <%= project.getProj_id() %> </td>
                    </tr>

                    <tr>
                        <td>Project Name: </td>
                        <td> <%= project.getProj_name() %></td>
                    </tr>
                    <tr>
                        <td>Start Date: </td>
                        <td><%= project.getStart_date() %></td>
                    </tr>
                    <tr>
                    <td>End Date: </td>
                     <td>
                         <%if (project.getEnd_date() != null){%>
                            <%= project.getEnd_date() %>
                         <%}else {%>
                            <%=blank %>
                            <% } %>
                     </td>

                </tr>
                <tr>
                    <td>Target Date: </td>
                    <td> <%= project.getTarget_date() %> </td>

                </tr>
                <tr>
                    <td>Description: </td>
                    <td> <%= project.getProj_desc() %> </td>

                </tr>
                
                    <tr>
                        <td>&nbsp;</td>
                        <td><input class="button" type="submit" value="Back" onClick="cancel()" style="float: left" title="Back"/></td>
                    </tr>
                </table>

            </div>
        </form:form>
    </body>
</html>
