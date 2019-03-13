<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.timesheet.bean.Project" %>
<%
            Project project = (Project) request.getSession().getAttribute("UPDATE_PROJECT");
            if (project == null) {
                project = new Project();
            }
%>
<link rel="stylesheet" href="default.css" />
<link rel="stylesheet" href="jquery.ui.core.css" />
<link rel="stylesheet" href="jquery.ui.theme.css" />
<link rel="stylesheet" href="jquery.ui.datepicker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
    </head>
    <script>
        $(document).ready(function() {
            $('.boxMe').box();
            $('.oxMe').box();
            $('.otherOne, .anotherOne').box();
        });
        function loginfocus()
        {
            document.updatepro.proj_name.focus();
        }
        function cancel()
        {
            document.location.href="manageproject.htm";
        }

        function isCharKey(evt)
        {	var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode!=46  && charCode!=45 && charCode!=95 && charCode > 32 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122))
                return false;
            else
                return true;
        }
        function focus1()
        {
            document.updatepro.end_date.focus();
        }
        function focus2()
        {
            document.updatepro.target_date.focus();
        }
        function focus3()
        {
            document.updatepro.proj_desc.focus();
        }
    </script>
    <style>
        .error{
            color:#ff0000;
            font-style:normal;
        }
    </style>
</head>
<body onload="loginfocus()">
    <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Update Project</font></center></div>
    <form:form method="POST" commandName="updatepro" action="updateproject.htm" name="updatepro">
        <div class="anotherOne bg-white" style="padding-left:15px;height:250px;" >
            <div align="right" style="color:red">  Fields marked with  * are mandatory  </div>
            <table>
                <tr>

                    <td><input style="border:1px solid #520000" type="hidden" size="20" name="proj_id" readonly value="<%= project.getProj_id()%>"/></td>
                </tr>
                <tr>
                    <td>Project Name<span style="color:red">* </span></td>
                    <td><input style="border:1px solid #520000" type="text" size="20" maxlength="50" name="proj_name" value="<%= request.getParameter("proj_name") == null ? project.getProj_name() : request.getParameter("proj_name")%>" onkeypress="return isCharKey(event)" />&nbsp;<form:errors path="proj_name" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Start Date<span style="color:red">* </span></td>
                    <td><input id="datepicker1" onchange="focus1()" style="border:1px solid #520000" type="text" size="20" maxlength="11" name="start_date" value="<%= request.getParameter("start_date") == null ? project.getStart_date() : request.getParameter("start_date")%>"/>&nbsp;(DD/MM/YYYY)&nbsp;<form:errors path="start_date" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>End Date</td>
                    <c:set var="enddate" value="<%= project.getEnd_date()%>"/>
                    <td><input id="datepicker2" onchange="focus2()" style="border:1px solid #520000" type="text" maxlength="11" size="20" name="end_date" value="<c:out value="${enddate}"/>"/>&nbsp;(DD/MM/YYYY)&nbsp;<form:errors path="end_date" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Target Date<span style="color:red">* </span></td>
                    <td><input id="datepicker3" onchange="focus3()" style="border:1px solid #520000" type="text" size="20" maxlength="11" name="target_date" value="<%= request.getParameter("target_date") == null ? project.getTarget_date() : request.getParameter("target_date")%>"/>&nbsp;(DD/MM/YYYY)&nbsp;<form:errors path="target_date" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Project Description<span style="color:red">* </span></td>
                    <td><textarea style="border:1px solid #520000" cols="20" rows="5" name="proj_desc" ><%= request.getParameter("proj_desc") == null ? project.getProj_desc() : request.getParameter("proj_desc")%></textarea>&nbsp;<form:errors path="proj_desc" cssClass="error" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input class="button" type="submit" value="Update" title="Update Project">
                        <input class="button" type="button" value="Cancel" onclick="cancel()" title="Back">
                        <input class="button" type="reset" value="Reset" title="Reset Data">
                    </td>
                </tr>
            </table>
        </div>
    </form:form>
</body>
<script type="text/javascript">
    $(function() {
        $('#datepicker1').datepicker({
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonText:'Click Here To Get Calendar',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $('#datepicker2').datepicker({
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonText:'Click Here To Get Calendar',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $('#datepicker3').datepicker({
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonText:'Click Here To Get Calendar',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });


    });
</script>
</html>
