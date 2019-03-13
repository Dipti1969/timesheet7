<%@ page language="java"
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.timesheet.bean.EmpTransaction" %>
<%@page import="com.timesheet.bean.Employee" %>
<%@page import="com.timesheet.bean.Project" %>
<%@page import="com.timesheet.bean.AssignBy" %>
<%@page import="java.lang.Object"%>
<%@page import="java.util.*"%>

<%@page import ="javax.servlet.http.HttpSession;"%>

<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

    TimeZone istTime = TimeZone.getTimeZone("IST");
    sdf.setTimeZone(istTime);
    String dt = request.getParameter("dt1") == null ? sdf.format(new Date()) : request.getParameter("dt1").toString();
%>
<%  java.util.ArrayList<String> yearList = new java.util.ArrayList<String>();
    java.util.ArrayList<Employee> employeeList = new java.util.ArrayList<Employee>();

    yearList = (java.util.ArrayList<String>) request.getSession().getAttribute("YEAR_LIST");
    employeeList = (java.util.ArrayList<Employee>) request.getSession().getAttribute("EMPLIST");

    java.util.ArrayList<AssignBy> assignbyList = new java.util.ArrayList<AssignBy>();

    java.util.ArrayList<Project> projectList = new java.util.ArrayList<Project>();

    assignbyList = (java.util.ArrayList) request.getSession().getAttribute("assign_by_list");

    projectList = (java.util.ArrayList) request.getSession().getAttribute("project_list");
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
        <%--
              <% String sessionID=session1.getId(); %>--%>
        <script type="text/javascript" language="javascript">
            function check(type){

                 
                                        

                for(var i=0; i<document.adminReportFrm.id.length; i++){
                    if (document.adminReportFrm.id[i].checked)
                    {
                        var rad1 = document.adminReportFrm.id[i].value;
  

                          
                    }
                }
                for(var i=0; i<document.adminReportFrm.reportType.length; i++){
                    if (document.adminReportFrm.reportType[i].checked)
                    {
                        var rad2 = document.adminReportFrm.reportType[i].value;
                    }
                }
                if(rad2 == "html"){
                    if(rad1=="datewise"){
                        var dt = document.getElementById('datewise_txt').value;
                        if(dt!=''){
                            document.getElementById('adminreport').target = "_top";
                            document.location.href = "datewisehtml.htm?datewise_txt="+dt+"&type=datewise";
                        }else{
                            alert("Enter Date");
                        }
                    }else if(rad1=="daily"){
                        var dt = document.getElementById('daily_txt').value;
                        if(dt!=''){
                            document.getElementById('adminreport').target = "_blank";
                            document.location.href = "dailyhtml.htm?daily_txt="+dt+"&type=daily";
                        }else{
                            alert("Enter Date");
                        }
                    }else if(rad1=="customdate"){
                        var dt1 = document.getElementById('customdate_txt1').value;;
                        var dt2 = document.getElementById('customdate_txt2').value;
                        if(dt1!='' && dt2!=''){
                            document.getElementById('adminreport').target = "_blank";
                            document.location.href = "customdatewisehtml.htm?customdate_txt1="+dt1+"&customdate_txt2="+dt2+"&type=customdate";
                        }else{
                            alert("Enter Date")
                        }
                    }else if(rad1=="monthwise"){
                        var month = document.getElementById('monthlist').value;
                        var year =  document.getElementById('yearlist').value;
                        if(month!=0 && year!=0){
                            document.location.href = "monthwisehtml.htm?month="+month+"&year="+year+"&type=monthwise";
                        }else{
                            alert("Select Month and Year");
                        }
                    }<%--else if(rad1=="newmonthwise"){
                        var month = document.getElementById('monthlist2').value;
                        var year =  document.getElementById('yearlist2').value;
                        if(month!=0 && year!=0){
                            document.getElementById('adminreport').target = "_blank";
                            document.location.href = "newmonthwisehtml.htm?month="+month+"&year="+year+"&type=newmonthwise";
                        }else{
                            alert("Select Month and Year");
                        }
                    }--%>else if(rad1 == "yearwise"){
                                var year = document.getElementById('yearlist1').value;
                                if(year!=0){
                                    document.getElementById('adminreport').target = "_blank";
                                    document.location.href = "yearwisehtml.htm?year="+year+"&type=yearwise";
                                }else{
                                    alert("Select Year");
                                }

                            }else if(rad1 == "employeewise"){
                                var emp = document.getElementById('emplist').value;

                                if(emp!=''){
                                    document.getElementById('adminreport').target = "_blank";

                                    document.location.href = "employeewisehtml.htm?emp="+emp+"&type=employeewise";
                                }else{
                                    alert("Select Employee");
                                }
                            }else if(rad1 == "criteria"){
                                var start_date = document.getElementById('criteria_txt1').value;
                                var end_date = document.getElementById('criteria_txt2').value;
                                var emp_id = document.getElementById('emplist1').value;
                                var assign_by_id = document.getElementById('assign_by_id').value;
                                var proj_id = document.getElementById('proj_id').value;

                                if(start_date != "" && end_date != ""){
                                    if(emp_id != ""){
                                        if(assign_by_id != ""){
                                            if(proj_id != ""){
                                                if(emp_id == "all" && assign_by_id == "0" && proj_id == "0"){
                                                    // alert("1");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report1html.htm?start_date="+start_date+"&end_date="+end_date+"&type=criteria&cat=report1";
                                                }else if(assign_by_id == "0" && proj_id == "0" ){
                                                    //alert("2");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report2html.htm?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&type=criteria&cat=report2";
                                                }else if(emp_id == "all" && proj_id == "0" ){
                                                    //alert("3");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "assigncatagoryhtml.htm?start_date="+start_date+"&end_date="+end_date+"&assign_by_id="+assign_by_id+"&type=criteria&cat=assign";
                                                }else if(emp_id == "all" && assign_by_id == "0"){
                                                    // alert("4");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "projectcatagoryhtml.htm?start_date="+start_date+"&end_date="+end_date+"&proj_id="+proj_id+"&type=catagory&cat=project";
                                                }else if(proj_id == "0" ){
                                                    //alert("5");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report5html.htm?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&assign_by_id="+assign_by_id+"&cat=report5&type=catagory";
                                                }else if(assign_by_id == "0" ){
                                                    // alert("6");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report6html.htm?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&proj_id="+proj_id+"&cat=report6&type=catagory";
                                                }else if(emp_id == "all"){
                                                    //alert("7");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report7html.htm?start_date="+start_date+"&end_date="+end_date+"&assign_by_id="+assign_by_id+"&proj_id="+proj_id+"&cat=report7&type=catagory";
                                                } else{
                                                    //  alert("8");
                                                    document.getElementById('adminreport').target = "_blank";
                                                    document.location.href = "report8html.htm?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&proj_id="+proj_id+"&cat=report8"+"&assign_by_id="+assign_by_id+"&type=catagory";
                                                }
                                            }else{
                                                alert("Select Project");
                                                document.getElementById('proj_id').focus();
                                            }
                                        }else{
                                            alert("Select Assign By");
                                            document.getElementById('assign_by_id').focus();
                                        }
                                    }else{
                                        alert("Select Employee");
                                        document.getElementById('emplist1').focus();
                                    }
                                }else{
                                    alert("Select Start Date and End Date");
                                }
                            }
                            else{
                                alert("Select Report Type");
                            }
                        } else if(rad2 == "excel"){
                            if(rad1=="datewise"){
                                var dt = document.getElementById('datewise_txt').value;
                                if(dt!=''){
                                    document.getElementById('adminreport').target = "_blank";
                                    document.location.href = "datewiseExcel.xls?datewise_txt="+dt+"&type=datewise";
                                }else{
                                    alert("Enter Date");
                                }
                            }else if(rad1=="daily"){
                                var dt = document.getElementById('daily_txt').value;
                                if(dt!=''){
                                    document.getElementById('adminreport').target = "_blank";
                                    document.location.href = "dailyExcel.xls?daily_txt="+dt+"&type=daily";
                                }else{
                                    alert("Enter Date");
                                }
                            }else if(rad1=="customdate"){
                                var dt1 = document.getElementById('customdate_txt1').value;;
                                var dt2 = document.getElementById('customdate_txt2').value;
                                if(dt1!='' && dt2!=''){
                                    document.getElementById('adminreport').target = "_blank";
                                    document.location.href = "customdateExcel.xls?customdate_txt1="+dt1+"&customdate_txt2="+dt2+"&type=customdate";
                                }else{
                                    alert("Enter Date")
                                }
                            }else if(rad1=="monthwise"){
                                var month = document.getElementById('monthlist').value;
                                var year =  document.getElementById('yearlist').value;
                                if(month!=0 && year!=0){
                                    document.getElementById('adminreport').target = "_blank";
                                    document.location.href = "monthwiseExcel.xls?month="+month+"&year="+year+"&type=monthwise";
                                }else{
                                    alert("Select Month and Year");
                                }
                            }<%--else if(rad1=="newmonthwise"){
                        var month = document.getElementById('monthlist2').value;
                        var year =  document.getElementById('yearlist2').value;
                        if(month!=0 && year!=0){
                            document.getElementById('adminreport').target = "_blank";
                            document.location.href = "newmonthwiseExcel.xls?month="+month+"&year="+year+"&type=newmonthwise";
                        }else{
                            alert("Select Month and Year");
                        }
                    }--%>else if(rad1 == "yearwise"){
                            var year = document.getElementById('yearlist1').value;
                            if(year!=0){
                                document.getElementById('adminreport').target = "_blank";
                                document.location.href = "yearwiseExcel.xls?year="+year+"&type=yearwise";
                            }else{
                                alert("Select Year");
                            }

                        }else if(rad1 == "employeewise"){
                            var emp = document.getElementById('emplist').value;

                            if(emp!=''){
                                document.getElementById('adminreport').target = "_blank";

                                document.location.href = "employeewiseExcel.xls?emp="+emp+"&type=employeewise";
                            }else{
                                alert("Select Employee");
                            }
                        }else if(rad1 == "criteria"){
                            var start_date = document.getElementById('criteria_txt1').value;
                            var end_date = document.getElementById('criteria_txt2').value;
                            var emp_id = document.getElementById('emplist1').value;
                            var assign_by_id = document.getElementById('assign_by_id').value;
                            var proj_id = document.getElementById('proj_id').value;

                            if(start_date != "" && end_date != ""){
                                if(emp_id != ""){
                                    if(assign_by_id != ""){
                                        if(proj_id != ""){
                                            if(emp_id == "all" && assign_by_id == "0" && proj_id == "0"){
                                                // alert("1");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "customdateExcel.xls?customdate_txt1="+start_date+"&customdate_txt2="+end_date+"&type=customdate";
                                            }else if(assign_by_id == "0" && proj_id == "0" ){
                                                //alert("2");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "report2Excel.xls?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&type=catagory&cat=report2";
                                            }else if(emp_id == "all" && proj_id == "0" ){
                                                //alert("3");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "assigncatagoryExcel.xls?start_date="+start_date+"&end_date="+end_date+"&assign_by_id="+assign_by_id+"&type=catagory&cat=assign";
                                            }else if(emp_id == "all" && assign_by_id == "0"){
                                                // alert("4");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "projectcatagoryExcel.xls?start_date="+start_date+"&end_date="+end_date+"&proj_id="+proj_id+"&type=catagory&cat=project";
                                            }else if(proj_id == "0" ){
                                                //alert("5");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "report5Excel.xls?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&assign_by_id="+assign_by_id+"&cat=report5&type=catagory";
                                            }else if(assign_by_id == "0" ){
                                                // alert("6");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "report6Excel.xls?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&proj_id="+proj_id+"&cat=report6&type=catagory";
                                            }else if(emp_id == "all"){
                                                //alert("7");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "report7Excel.xls?start_date="+start_date+"&end_date="+end_date+"&assign_by_id="+assign_by_id+"&proj_id="+proj_id+"&cat=report7&type=catagory";
                                            }else{
                                                //  alert("8");
                                                document.getElementById('adminreport').target = "_blank";
                                                document.location.href = "report8Excel.xls?start_date="+start_date+"&end_date="+end_date+"&emp_id="+emp_id+"&proj_id="+proj_id+"&cat=report8"+"&assign_by_id="+assign_by_id+"&type=catagory";
                                            }
                                        }else{
                                            alert("Select Project");
                                            document.getElementById('proj_id').focus();
                                        }
                                    }else{
                                        alert("Select Assign By");
                                        document.getElementById('assign_by_id').focus();
                                    }
                                }else{
                                    alert("Select Employee");
                                    document.getElementById('emplist1').focus();
                                }
                            }else{
                                alert("Select Start Date and End Date");
                            }
                        }else{
                            alert("Select Report Type");
                        }
                    }else if(rad2 == "pdf"){
                        if(rad1=="datewise"){
                            var dt = document.getElementById('datewise_txt').value;
                            if(dt!=''){
                                //   document.getElementById('adminreport').target = "_blank";
                                document.getElementById('adminreport').target = "_top";
                                document.forms.adminReportFrm.submit();
                            }else{
                                alert("Enter Date");
                            }
                        }else if(rad1=="daily"){
                            alert('Not supoort for PDF Format');
                        }else if(rad1=="customdate"){
                            var dt1 = document.getElementById('customdate_txt1').value;;
                            var dt2 = document.getElementById('customdate_txt2').value;
                            if(dt1!='' && dt2!=''){
                                document.getElementById('adminreport').target = "_top";
                                //  document.getElementById('adminreport').target = "_blank";
                                // document.location.href = "customdateExcel.htm?customdate_txt1="+dt1+"&customdate_txt2="+dt2+"&type=customdate";
                                document.forms.adminReportFrm.submit();
                            }else{
                                alert("Enter Date")
                            }
                        }else if(rad1=="monthwise"){
                            var month = document.getElementById('monthlist').value;
                            var year =  document.getElementById('yearlist').value;
                            if(month!=0 && year!=0){
                                document.getElementById('adminreport').target = "_top";
                                // document.getElementById('adminreport').target = "_blank";
                                //document.location.href = "monthwiseExcel.htm?month="+month+"&year="+year+"&type=monthwise";
                                document.forms.adminReportFrm.submit();
                            }else{
                                alert("Select Month and Year");
                            }
                        }<%--else if(rad1=="newmonthwise"){
                        var month = document.getElementById('monthlist2').value;
                        var year =  document.getElementById('yearlist2').value;
                        if(month!=0 && year!=0){
                            document.getElementById('adminreport').target = "_top";
                            // document.getElementById('adminreport').target = "_blank";
                            //document.location.href = "monthwiseExcel.htm?month="+month+"&year="+year+"&type=monthwise";
                            document.forms.adminReportFrm.submit();
                        }else{
                            alert("Select Month and Year");
                        }
                    }--%>else if(rad1 == "yearwise"){
                        var year = document.getElementById('yearlist1').value;
                        if(year!=0){
                            document.getElementById('adminreport').target = "_top";
                            //  document.getElementById('adminreport').target = "_blank";
                            // document.location.href = "yearwiseExcel.htm?year="+year+"&type=yearwise";
                            document.forms.adminReportFrm.submit();
                        }else{
                            alert("Select Year");
                        }

                    }else if(rad1 == "employeewise"){
                        var emp = document.getElementById('emplist').value;
                        if(emp!=''){
                            document.getElementById('adminreport').target = "_top";
                            // document.getElementById('adminreport').target = "_blank";
                            //document.location.href = "employeewiseExcel.htm?emp="+emp+"&type=employeewise";
                            document.forms.adminReportFrm.submit();
                        }else{
                            alert("Select Employee");
                        }
                    }else if(rad1 == "criteria"){
                        var start_date1 = document.getElementById('criteria_txt1').value;
                        var end_date1 = document.getElementById('criteria_txt2').value;
                        var emp_id = document.getElementById('emplist1').value;
                        var assign_by_id = document.getElementById('assign_by_id').value;
                        var proj_id = document.getElementById('proj_id').value;

                        if(start_date1!="" && end_date1!=""){
                            if(emp_id!=""){
                                if(assign_by_id!=""){
                                    if(proj_id!=""){
                                        //     document.getElementById('adminreport').target = "_blank";
                                        document.getElementById('adminreport').target = "_top";
                                        document.forms.adminReportFrm.submit();
                                    }else{
                                        alert("Select Project");
                                        document.getElementById('proj_id').focus();
                                    }
                                }else{
                                    alert("Select AssignBy");
                                    document.getElementById('assign_by_id').focus();
                                }
                            }else{
                                alert("Select Employee");
                                document.getElementById('emplist1').focus();
                            }
                        }else{
                            alert("Select Start Date and End Date");
                        }
                    }else{
                        alert("Select report type");
                    }
                }else{
                    alert("Select Report Type (PDF/EXCEL)");
                }
            }
            function getVisible1(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "visible";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('lbl1').style.visibility="visible";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";

                //   document.getElementById('pdftype').style.visibility = "visible";
            }
            function getVisible7(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('daily_txt').style.visibility = "visible";
                document.getElementById('lbl7').style.visibility="visible";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";

                //   document.getElementById('pdftype').style.visibility = "visible";
            }
            function getVisible2(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.visibility = "visible";
                document.getElementById('customdate_txt2').style.visibility = "visible";
                document.getElementById('lbl2').style.visibility="visible";
                document.getElementById('date_txt').style.display="";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //    document.getElementById('pdftype').style.visibility = "visible";
            }
            function getVisible3(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "visible";
                document.getElementById('yearlist').style.visibility = "visible";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //     document.getElementById('pdftype').style.visibility = "visible";
            }
            function getVisible4(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "visible";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //   document.getElementById('pdftype').style.visibility = "visible";
            }

            function getVisible5(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "visible";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //    document.getElementById('pdftype').style.visibility = "visible";
            }

            function getVisible6(){
                document.getElementById('criteria_td').style.visibility = "visible";
                document.getElementById('lbl3').style.display="";
                document.getElementById('date_txt1').style.display="";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.display = "";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[0].selected = "1";
                document.forms.adminReportFrm.proj_id[0].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //   document.getElementById('pdftype').style.visibility = "hidden";
            }
            <%--function getVisible8(){
                document.getElementById('criteria_td').style.visibility = "hidden";
                document.getElementById('lbl3').style.display="none";
                document.getElementById('date_txt1').style.display="none";
                document.getElementById('datewise_txt').style.visibility = "hidden";
                document.getElementById('lbl1').style.visibility="hidden";
                document.getElementById('daily_txt').style.visibility = "hidden";
                document.getElementById('lbl7').style.visibility="hidden";
                document.getElementById('customdate_txt1').style.visibility = "hidden";
                document.getElementById('customdate_txt2').style.visibility = "hidden";
                document.getElementById('lbl2').style.visibility="hidden";
                document.getElementById('date_txt').style.display="none";
                document.getElementById('monthlist').style.visibility = "hidden";
                 document.getElementById('monthlist2').style.visibility = "visible";
                document.getElementById('yearlist').style.visibility = "hidden";
                document.getElementById('yearlist1').style.visibility = "hidden";
                 document.getElementById('yearlist2').style.visibility = "visible";
                document.getElementById('emplist').style.visibility = "hidden";
                document.forms.adminReportFrm.assign_by_id[1].selected = "1";
                document.forms.adminReportFrm.proj_id[1].selected = "1";
                document.getElementById('msg').style.visibility = "hidden";
                //     document.getElementById('pdftype').style.visibility = "visible";
            }--%>
        </script>
    </head>
    <body>


        <div class="anotherOne"><center><font face="arial" style="font-size:16px;font-weight:bold;" color="#FFFFFF">View Reports</font></center></div>
        <form:form name="adminReportFrm" id="adminreport" action="adminreport.htm" method="post">

            <div class="anotherOne bg-white" style="min-height:245px;">

                <div style="border:1px solid #520000;">
                    <div>
                        <div style="margin-left:5px;">
                            <div style="height:1em;"></div>
                            <div>

                                <div style="width:150px;float:left;"> <input type="radio" id="datewise" name="id" value="datewise"  title="Date-Wise Report" onclick="getVisible1()" >Date-Wise</div>
                                <div><input type="text" size="10" id="datewise_txt" name="datewise_txt" value="<%=dt%>" style="visibility:hidden; border:1px solid #520000;" >&nbsp;<label id="lbl1" style="visibility:hidden;">(DD/MM/YYYY)</label></div>


                            </div>
                            <div style="height:1em;"></div>

                            <div>
                                <div style="width:150px;float:left"><input type="radio" id="daily" name="id" value="daily"  title="Daily Report" onclick="getVisible7()" >Daily Report</div>
                                <div> <input type="text" size="10" id="daily_txt" name="daily_txt" value="<%=dt%>" style="visibility:hidden; border:1px solid #520000;" >&nbsp;<label id="lbl7" style="visibility:hidden;">(DD/MM/YYYY)</label></div>
                            </div>
                            <div style="height:1em;"></div>
                            <div>
                                <div style="width:150px;float:left"><input type="radio" id="customdate" name="id" value="customdate"  title="CustomDate-Wise Report" onclick="getVisible2()" >Custom Date-Wise</div>

                                <input type="text" size="10" id="customdate_txt1" name="customdate_txt1" style="visibility:hidden; border:1px solid #520000;" value="<%=dt%>" >
                                &nbsp;&nbsp;
                                <input type="text" size="10" id="customdate_txt2" name="customdate_txt2" style="visibility:hidden; border:1px solid #520000;" value="<%=dt%>" >
                                &nbsp;&nbsp;
                                <label id="lbl2" style="visibility:hidden;">(DD/MM/YYYY)</label>
                            </div>
                            <div style="display:none;padding-left:150px;" id="date_txt">

                                Start Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End Date
                            </div>
                            <div style="height:1em;"></div>
                            <div>
                                <div style="width:150px;float:left"><input type="radio" id="monthwise" name="id" value="monthwise"  title="Month-Wise Report" onclick="getVisible3()" >Month-Wise</div>

                                <select id="monthlist" name="monthlist" style="border:1px solid #520000; visibility:hidden;" >
                                    <option value="0">Select Month</option>
                                    <option value="01">January</option>
                                    <option value="02">February</option>
                                    <option value="03">March</option>
                                    <option value="04">April</option>
                                    <option value="05">May</option>
                                    <option value="06">June</option>
                                    <option value="07">July</option>
                                    <option value="08">August</option>
                                    <option value="09">September</option>
                                    <option value="10">October</option>
                                    <option value="11">November</option>
                                    <option value="12">December</option>
                                </select>
                                <select id="yearlist" style="border:1px solid #520000; visibility:hidden;" name="yearlist">
                                    <option value="0">Select Year</option>
                                    <% for (int i = 0; i < yearList.size(); i++) {
                                            Object year = (Object) yearList.get(i);
                                    %>
                                    <option value="<%=year.toString().substring(3, 7)%>"><%=year.toString().substring(3, 7)%></option>
                                    <%}%>
                                </select>

                            </div>
                            <%-- <div style="height:1em;"></div>
                              <div>
                                 <div style="width:150px;float:left"> <input type="radio" id="newmonthwise" name="id" value="newmonthwise"  title="Month-Wise Report" onclick="getVisible8()" >Month-Wise-New</div>
                                 
                                      <select id="monthlist2" name="monthlist2" style="border:1px solid #520000; visibility:hidden;" >
                                          <option value="0">Select Month</option>
                                          <option value="01">January</option>
                                          <option value="02">February</option>
                                          <option value="03">March</option>
                                          <option value="04">April</option>
                                          <option value="05">May</option>
                                          <option value="06">June</option>
                                          <option value="07">July</option>
                                          <option value="08">August</option>
                                          <option value="09">September</option>
                                          <option value="10">October</option>
                                          <option value="11">November</option>
                                          <option value="12">December</option>
                                      </select>
                                      <select id="yearlist2" style="border:1px solid #520000; visibility:hidden;" name="yearlist2">
                                          <option value="0">Select Year</option>
                                          <% for (int i = 0; i < yearList.size(); i++) {
                      Object year = (Object) yearList.get(i);
                                          %>
                                          <option value="<%=year.toString().substring(3, 7)%>"><%=year.toString().substring(3, 7)%></option>
                                          <%}%>
                                      </select>
                                  
                               </div>--%>
                            <div style="height:1em;"></div>
                            <div>
                                <div style="width:150px;float:left"><input type="radio" id="yearwise" name="id" value="yearwise"  title="Year-Wise Report" onclick="getVisible4()" >Year-Wise</div>

                                <select id="yearlist1" style="border:1px solid #520000; width:90px;visibility:hidden;" name="yearlist1" >
                                    <option value="0">Select Year</option>
                                    <% for (int i = 0; i < yearList.size(); i++) {
                                            Object year1 = (Object) yearList.get(i);
                                    %>
                                    <option value="<%=year1.toString().substring(3, 7)%>"><%=year1.toString().substring(3, 7)%></option>
                                    <%}%>
                                </select>

                            </div>
                            <div style="height:1em;"></div>
                            <div>
                                <div style="width:150px;float:left"> <input type="radio" id="employeewise" name="id" value="employeewise"  title="Employee-Wise Report" onclick="getVisible5()" >Employee-Wise</div>

                                <select style="border:1px solid #520000;visibility:hidden;" id="emplist" name="emplist">
                                    <option value="" >--Select--</option>
                                    <%for (int i = 1; i < employeeList.size(); i++) {
                                            Employee emp = (Employee) employeeList.get(i);

                                    %>
                                    <c:set var="roll" value="<%=emp.getRole_id()%>"/>
                                    <c:set var="rollid" value="3"/>

                                    <c:if test="${roll ne rollid}">
                                        <option value="<%=emp.getEmp_id()%>"><%=emp.getEmp_fname()%><%=" "%> <%= emp.getEmp_lname()%></option>
                                    </c:if>
                                    <%}%>
                                </select>


                            </div>
                            <div style="height:1em;"></div>
                            <div id="lbl3" style="display:none;padding-left:150px;">
                                <label>(DD/MM/YYYY)</label>
                            </div>
                            <!-- criteria wise -->
                            <div>
                                <div style="width:150px;float:left"> <input type="radio" id="criteria" name="id" value="criteria"  title="Criteria-Wise Report" onclick="getVisible6()" style="float:left;"float:left>Criteria-Wise</div>
                                <div id="criteria_td" style="visibility:hidden;">
                                    <input style="border:1px solid #520000;" size="8" type="text" id="criteria_txt1" name="criteria_txt1" value="<%=dt%>" />
                                    <input style="border:1px solid #520000;" size="8" type="text" id="criteria_txt2" name="criteria_txt2" value="<%=dt%>" />

                                    <select style="border:1px solid #520000;" id="emplist1" name="emplist1">
                                        <option value="" >--Employee--</option>
                                        <option value="all" >All</option>
                                        <%for (int i = 1; i < employeeList.size(); i++) {
                                                Employee emp11 = (Employee) employeeList.get(i);
                                        %>
                                        <c:set var="roll11" value="<%=emp11.getRole_id()%>"/>
                                        <c:set var="rollid11" value="3"/>

                                        <c:if test="${roll11 ne rollid11}">
                                            <option value="<%=emp11.getEmp_id()%>" ><%=emp11.getEmp_fname()%><%=" "%><%= emp11.getEmp_lname()%></option>
                                        </c:if>
                                        <%}%>
                                    </select>

                                    <select style="border:1px solid #520000;" id="assign_by_id" name="assign_by_id" >
                                        <option value="" >--AssignBy--</option>
                                        <option value="0" >All</option>
                                        <%for (int j = 0; j < assignbyList.size(); j++) {
                                                AssignBy ab1 = (AssignBy) assignbyList.get(j);
                                        %>
                                        <option value="<%=ab1.getAssign_by_id()%>"><%=ab1.getAssign_by_name()%></option>
                                        <%}%>
                                    </select>

                                    <select style="border:1px solid #520000;" id="proj_id" name="proj_id" >
                                        <option value="">--Project--</option>
                                        <option value="0">All</option>
                                        <%for (int k = 0; k < projectList.size(); k++) {
                                                Project proj1 = (Project) projectList.get(k);
                                        %>
                                        <option value="<%=proj1.getProj_id()%>"><%=proj1.getProj_name()%></option>
                                        <%}%>
                                    </select>
                                </div>

                                <div style="display:none;padding-left:150px;" id="date_txt1">Start Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End Date</div>
                                <div style="height:1em;"></div>
                                <!-- -->
                                <div>
                                    <div id="msg" colspan="2" style="color:red; font-size:14px; padding-left:10px;">
                                        <c:out value="${msg}" />

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div style="margin:2px;border:1px solid #520000;">
                            <table>
                                <tr>
                                    <td><input type="radio" checked name="reportType" value="html">Html</td>
                                    <td><input type="radio" name="reportType" value="excel">Excel</td>
                                    <td><input type="radio" name="reportType" value="pdf">PDF</td>

                                </tr>
                            </table>
                        </div>
                        <table>
                            <tr>
                                <td>
                                    <input class="button" type="button" value="submit" onclick="check('${session1}')" title="Submit">
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </form:form>
    </body>
    <script type="text/javascript">

$(function() {
$('#datewise_txt').datepicker({
    changeMonth: true,
    changeYear: true
});
$('#daily_txt').datepicker({
    changeMonth: true,
    changeYear: true
});
$('#customdate_txt1').datepicker({
    changeMonth: true,
    changeYear: true
});
$('#customdate_txt2').datepicker({
    changeMonth: true,
    changeYear: true
});
$('#criteria_txt1').datepicker({
    changeMonth: true,
    changeYear: true
});
$('#criteria_txt2').datepicker({
    changeMonth: true,
    changeYear: true
});
});






for(var i=0; i<document.adminReportFrm.id.length; i++){
if (document.adminReportFrm.id[i].value ==  <%=request.getParameter("type")%>)
{
    document.adminReportFrm.id[i].checked = true;
    switch(<%=request.getParameter("type")%>)
                      
    {
        case 'datewise':
            document.getElementById('datewise_txt').style.visibility = "visible";
   
            document.getElementById('lbl1').style.visibility="visible";
            break;


        case 'customdate':
 
     
            document.getElementById('customdate_txt1').style.visibility = "visible";
            document.getElementById('customdate_txt2').style.visibility = "visible";
            document.getElementById('lbl2').style.visibility="visible";
            break;

        case 'daily':
            alert( document.getElementById('daily_txt').value);
            document.getElementById('daily_txt').style.visibility = "visible";
            document.getElementById('lbl7').style.visibility="visible";
            break;
  
        case 'monthwise':
            document.getElementById('monthlist').style.visibility = "visible";

            document.getElementById('yearlist').style.visibility = "visible";
            if(document.getElementById('monthlist').value == "0" & document.getElementById('yearlist').value == "0"){

                document.getElementById('monthlist').value = <%=request.getParameter("omonth")%>;
                document.getElementById('yearlist').value = <%=request.getParameter("oyear")%>;
            }
 
            break;

                
        <%--case 'newmonthwise':
         document.getElementById('monthlist2').style.visibility = "visible";

                 document.getElementById('yearlist2').style.visibility = "visible";
                  if(document.getElementById('monthlist2').value == "0" & document.getElementById('yearlist2').value == "0"){
                    
                   document.getElementById('monthlist2').value = <%=request.getParameter("nmonth")%>;
                   document.getElementById('yearlist2').value = <%=request.getParameter("nyear")%>;
                 }

  break;--%>
          case 'yearwise':
              document.getElementById('yearlist1').style.visibility = "visible";
    

              if(document.getElementById('yearlist1').value == "0"){
                  //alert("yes");
                  document.getElementById('yearlist1').value = <%=request.getParameter("year1")%>;

              }
     




              break;
  
          case 'employeewise':

   
              if(document.getElementById('emplist').value == ""){
                  document.getElementById('emplist').value = <%=request.getParameter("empr")%>;
              }

   
              break;
  
          case 'criteria':



 
              document.getElementById('criteria_td').style.visibility = "visible";

        <%--switch(<%=request.getParameter("cat1")%>)
        {
          case 'report1':
            if(document.forms.adminReportFrm.proj_id[1].value == "0" & document.forms.adminReportFrm.assign_by_id[1].value == "0" & document.forms.adminReportFrm.emplist1[1].value == "all"){

   document.forms.adminReportFrm.proj_id[1].value = "All";
   document.forms.adminReportFrm.proj_id[1].selected = "1"
    document.forms.adminReportFrm.assign_by_id[1].value = "All";
    document.forms.adminReportFrm.assign_by_id[1].selected = "1"
    document.forms.adminReportFrm.emplist1[1].selected = "1"

             }

case 'report2':

     if(document.forms.adminReportFrm.proj_id[1].value == "0" & document.forms.adminReportFrm.assign_by_id[1].value == "0" & document.forms.adminReportFrm.emplist1[1].value == "all"){

   document.forms.adminReportFrm.proj_id[1].value = "All";
   document.forms.adminReportFrm.proj_id[1].selected = "1"
     document.forms.adminReportFrm.assign_by_id[1].value = "All";
    document.forms.adminReportFrm.assign_by_id[1].selected = "1"

    document.forms.adminReportFrm.emplist1[1].value = <%=request.getParameter("emp_id")%>
 
             }


  case 'assign':
 
     if(document.forms.adminReportFrm.proj_id[1].value == "0" & document.forms.adminReportFrm.assign_by_id[1].value == "0" & document.forms.adminReportFrm.emplist1[1].value == "all"){

   document.forms.adminReportFrm.proj_id[1].value = "All";
   document.forms.adminReportFrm.proj_id[1].selected = "1"
     document.forms.adminReportFrm.emplist1[1].value = <%=request.getParameter("asgn")%>
     document.forms.adminReportFrm.assign_by_id[1].selected = "1"
    document.forms.adminReportFrm.emplist1[1].selected = "1"


             }


  }

        --%>
  

               
                    break;
                }
                      

            }
        }


        

    </script>

</html>