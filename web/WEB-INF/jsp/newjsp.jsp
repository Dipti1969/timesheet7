<%@ page import="java.io.InputStream" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@ page import="java.io.*" %>
<%
        try {
    HSSFWorkbook wb = new HSSFWorkbook();
    FileOutputStream fileOut = new FileOutputStream
("C:\\shuchi.xls");
   wb.write(fileOut);
   fileOut.close();
   out.println("Your file has been created succesfully");
       } catch ( Exception ex ) {
    }


%>