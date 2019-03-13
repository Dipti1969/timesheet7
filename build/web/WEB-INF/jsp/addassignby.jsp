<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeSheet</title>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/jRounded.js"></script>
        <link rel="stylesheet" href="default.css" type="text/css" />
        <script>
         
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
            function loginfocus()
            {
                document.addassignby.assign_by_name.focus();
            }
            function cancel()
            {
                document.location.href="manageassignby.htm";
            }
            function isCharKey(evt)
            {	var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode!=46  && charCode!=45 && charCode!=95 && charCode > 32 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122))
                    return false;
                else
                    return true;
            }
        </script>

        <style>
            .error{
                color:red;
                font-style:italic;
            }
        </style>
    </head>

    <body onload="loginfocus()">
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Add AssignBy</font></center></div>

        <form:form name="addassignby" commandName="addassign">
            <div class="anotherOne bg-white" style="padding-left:15px;height:250px;">
                <table>
                    <tr>
                        <td>AssignBy Name</td>
                        <td><input style="border:1px solid #520000" type="text" maxlength="20" size="20" name="assign_by_name" onkeypress="return isCharKey(event)"/></td>
                        <td style="color:red;"><c:out value="${msg}" /></td>
                    </tr>

                    <tr>
                        <td></td>
                        <td><input class="button" type="submit" value="Add" title="Add Assign By"/>
                            <input class="button" type="button" value="Cancel" onclick="cancel()" title="Back">
                            <input class="button" type="reset" value="Reset" title="Reset Data">
                        </td>
                        <td></td>
                    </tr>
                </table>

            </div>
        </form:form>
    </body>
</html>
