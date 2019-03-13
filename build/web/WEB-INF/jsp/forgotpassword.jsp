<%-- 
    Document   : forgotpassword
    Created on : Feb 26, 2010, 4:25:16 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="form"  uri="http://www.springframework.org/tags/form"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Forgot Password Page</title>
        <script type="text/javascript">
            
            function loginfocus()
            {
                document.forgotpass.email.focus();
            }
            function cancel(){
                document.location.href = "index.htm"
            }
        </script>
    </head>
    <body onload="loginfocus()">
        <div style="height:300px;">
            <form:form commandName="forpassword" name="forgotpass">
                <table>
                    <tr>
                        <td>Enter your Email ID:&nbsp;</td>
                        <td>
                            <input type="text" name="email"/>
                        </td>
                        <td style="color:red">
                            <c:out value="${mes}" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right">
                            <input class="button" type="submit" value="Submit"/>                            
                            <input class="button" type="reset" value="Reset"/>
                            <input class="button" type="button" value="Back" onclick="cancel()">
                        </td>
                    </tr>
                    
                </table>
                        <div style="color:green; font-size:16px; font-weight:bold;" >
                            <c:out value="${message}" />
                        </div>

            </form:form>
        </div>
    </body>
</html>
