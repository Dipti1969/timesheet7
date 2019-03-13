<%@page contentType="text/html" pageEncoding="ISO-8859-15"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Date"%>
<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String dt = sdf.format(new Date());
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

        <script>
          
            $(document).ready(function() {
                $('.boxMe').box();
                $('.oxMe').box();
                $('.otherOne, .anotherOne').box();
            });
        </script>
        <script language="javascript">
            function getVisible(){
                document.getElementById('daily_txt').style.visibility = "visible";
                document.getElementById('lbl').style.visibility="visible";
            }

            function generate(){
                var rad1 = document.getElementById('daily').value;
                for(var i=0; i<document.rece_rep.reportType.length; i++){
                    if (document.rece_rep.reportType[i].checked)
                    {
                        var rad2 = document.rece_rep.reportType[i].value;
                    }
                }

                var dt = document.getElementById('daily_txt').value;
                if(rad2 == "excel"){
                    if(rad1 == "daily"){
                        if(dt!=''){
                            document.getElementById('receptionreport').target = "_blank";
                            document.location.href = "dailyExcel.xls?daily_txt="+dt+"&type=daily";
                        }else{
                            alert("Enter Date");
                        }
                    }else{
                        alert("Select Report Type");
                    }
                }else if(rad2 == "pdf"){
                    if(rad1 == "daily"){
                        if(dt!=''){
                            document.forms.rece_rep.submit();
                        }else{
                            alert("Enter Date");
                        }
                    }else{
                        alert("Select Report Type");
                    }
                }else{
                    alert("Select Report Type (pdf/excel)")
                }
            }
            
        </script>
    </head>
    <body>
        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">View Reports</font></center></div>
        <form:form commandName="receptionistreport" id="receptionreport" name="rece_rep">
            <div class="anotherOne bg-white" style="_height:264px; min-height:264px;" >
                <div style="border:1px solid #520000; padding:5px; ">
                    <table cellpadding="2">
                       <tr>
                            <td><input type="radio" id="daily" name="id" value="daily"  title="Daily Report" onclick="getVisible()" >Daily Report</td>
                            <td><input type="text" size="10" id="daily_txt" name="daily_txt" value="<%=dt%>" style="visibility:hidden; border:1px solid #520000;" >&nbsp;<label id="lbl" style="visibility:hidden;">(DD/MM/YYYY)</label></td>
                        </tr>
                        <tr>
                            <td style="color:red; font-size:14px; padding-left:10px;"><c:out value="${msg}" /></td>
                        </tr>                        
                    </table>
                </div>
                <div style="height:5px;"></div>
                <div style="border:1px solid #520000;">
                    <table>
                        <tr>
                            <td><input type="radio" checked name="reportType" value="excel">Excel</td>
                            <td><input type="radio" name="reportType" value="pdf">PDF</td>

                        </tr>
                        <tr>
                            <td style="color:red; font-size:14px; padding-left:10px;"><c:out value="${rmsg}" /></td>
                        </tr>
                    </table>
                </div>
                <table>
                    <tr>
                        <td>
                            <input class="button" type="button" value="submit" onclick="generate()">
                        </td>
                    </tr>
                </table>
            </div>
        </form:form>
    </body>    
    <script type="text/javascript">
        $(function() {
            $('#daily_txt').datepicker({
                changeMonth: true,
                changeYear: true
            });
        });
    </script>
</html>