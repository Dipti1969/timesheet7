<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login Page</title>
    </head>
    <script>
        function loginfocus()
        {
            document.loginform.username.focus();
        }
    </script>
    <body onload="loginfocus()" style="height:100%;">
        <div style="padding:50px 10px;">
            <table width="100%">
                <tr>
                    <!-- logincontent start--------------------------------------->
                    <td width="60%">
                        <table>
                            <tr>
                                <td rowspan="2" colspan="2"><img src="images/clock.jpg" height="45" width="55" alt="Search" /></td>
                                <td></td>
                                <td><font color="#520000" style="font-weight:bold;">TimeSheet</font></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">Employee can save his/her work using TimeSheet.</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><img src="images/spacer.gif" height="10" width="50" /></td>
                            </tr>
                            <tr>
                                <td rowspan="2" colspan="2">
                                    <img src="images/email_logo.jpg" height="50" width="50" />
                                </td>
                                <td></td>
                                <td><font color="#520000" style="font-weight:bold;">Notification</font></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">New User will get his/her username and password by<br>auto-generated notification.</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><img src="images/spacer.gif" height="10" width="50" /></td>
                            </tr>
                          <%--  <tr>
                                <td rowspan="2" colspan="2"><img src="images/attendence.jpg"
                                                                 height="60" width="60"/></td>
                                <td></td>
                                <td><font color="#520000" style="font-weight:bold;">Attendence</font></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">System automatically maintain attendance of employee.<br><br></td>
                                <td></td>
                            </tr>
--%>
                        </table>
                        <!-- logincontent end-->
                    </td>
                    <!-- userlogin start-------------------------->
                    <td width="35%" align="center">
                        <form:form method="post" commandName="login" name="loginform">

                            <table cellpadding="0" cellspacing="0" style="background-color: #520000;">
                                <tr>
                                    <td width="30" ><img src="images/leftcorner.jpg" width="30" height="30" /></td>
                                    <td width="70"></td>
                                    <td width="10" ></td>
                                    <td width="30" ><img src="images/rightcorner.jpg" width="30" height="30" /></td>
                                </tr>
                                <tr>
                                    <td width="30"></td>
                                    <td width="70" align="left"><font color="#FFFFFF">Username </font></td>
                                    <td width="10"><input type="text" name="username" size="13" maxlength="20"></td>
                                    <td width="30" ></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                <tr>
                                    <td></td>
                                    <td align="left"><font color="#FFFFFF">Password </font></td>
                                    <td><input type="password" name="password" size="13" maxlength="20"></td>
                                </tr>
                                <tr>
                                    <td colspan="4">&nbsp;</td>
                                </tr>
                                <tr>
                                    
                                    <td align="center" colspan="4" ><a style="color:#FFFFFF; text-decoration:none;" href="forgotpassword.htm">Forgot Password?</a></td>
                                </tr>
                                <tr>
                                    <td width="30" ><img src="images/left1corner.jpg" width="30" height="30"/></td>
                                    <td   align="center" colspan="2"><input class="button" type="submit" value="Login"></td>
                                    <td width="30" ><img src="images/right1corner.jpg" width="30" height="30" /></td>
                                </tr>
                            </table>
                            <div style="color:red;font-style:normal;font-weight:bold;padding:2px;" align="center">
                                <c:out value="${msg}" />
                                <c:out value="${blockmsg}" />

                            </div>
                            <div style="color:green;font-style:normal;font-weight:bold;padding:2px;" align="center">
                                <c:out value="${message}" />
                            </div>

                        </form:form>
                        <!-- userlogin end------------------------------------------------------------------->
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>