<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
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
            
            function manageProject(){
                document.location.href = "manageproject.htm";
            }
            function manageDepartment(){
                document.location.href = "managedept.htm";
            }
            function manageDesignation(){
                document.location.href = "managedesi.htm";
            }

            function manageAssignBy(){
                document.location.href = "manageassignby.htm";
            }
            function setFocus(){
                document.getElementById('manageproject').focus();

            }
        </script>
    </head>
    <body onload="setFocus()">

        <div class="anotherOne">
            <center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">Manage Masters</font></center>
        </div>
        <div class="anotherOne bg-white" style="min-height:230px; _height:230px;">
            <table align="center">
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td align="center"><input style="width:150px;" class="button" id="manageproject" type="button" value="Manage Project" onclick="manageProject()" title="Manage Project"></td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td align="center"><input style="width:150px;" class="button" type="button" value="Manage Department" onclick="manageDepartment()" title="Manage Department"></td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td align="center"><input style="width:150px;" class="button" type="button" value="Manage Designation" onclick="manageDesignation()" title="Manage Designation"></td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td align="center"><input style="width:150px;" class="button" type="button" value="Manage AssignBy" onclick="manageAssignBy()" title="Manage AssignBy"></td>
                </tr>
            </table>
        </div>
    </body>
</html>
