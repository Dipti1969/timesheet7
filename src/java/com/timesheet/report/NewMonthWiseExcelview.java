package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class NewMonthWiseExcelview extends AbstractExcelView {

    public static final String TRANS_LIST = null;
    protected static final short DATE_COLUMN = 0;
    protected static final short DAY_COLUMN = 1;
    protected static final short EMPNAME_COLUMN = 2;
    protected static final double HOURS_COLUMN = 3;
    protected static final short TOTALHOURS_COLUMN = 4;
    protected static final short ASSIGNBY_COLUMN = 5;
    protected static final short PROXY_COLUMN = 6;
    protected static final short PROJECT_COLUMN = 7;
    protected static final short DEPT_COLUMN = 8;
    protected static final short WORK_COLUMN = 9;
protected static final short WORK1_COLUMN = 10;
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("MonthWise Report");
       sheet.setDefaultColumnWidth((short) 12);
        sheet.setDefaultRowHeight((short) 12);
        
        //GETCELL: getCell(SHEET, ROW, COLUMN);
        short currentRow = 0;
        short currentCol = 2;
//    
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        HSSFCellStyle wrapText = workbook.createCellStyle();
        wrapText.setWrapText(true);

        HSSFCell header0 = getCell(sheet, currentRow, EMPNAME_COLUMN);
        setText(header0, "EmployeeName");
        header0.setCellStyle(cellStyle);

        HSSFCell header1 = getCell(sheet, currentRow, DATE_COLUMN);
        setText(header1, "Date");
        header1.setCellStyle(cellStyle);

        HSSFCell header2 = getCell(sheet, currentRow, DAY_COLUMN);
        setText(header2, "Day");
        header2.setCellStyle(cellStyle);

        HSSFCell header3 = getCell(sheet, currentRow,(int) HOURS_COLUMN);
        setText(header3, "Hours");
        header3.setCellStyle(cellStyle);

        HSSFCell header4 = getCell(sheet, currentRow, TOTALHOURS_COLUMN);
        setText(header4, "Total Hours");
        header4.setCellStyle(cellStyle);

        HSSFCell header5 = getCell(sheet, currentRow, ASSIGNBY_COLUMN);
        setText(header5, "AssignBy");
        header5.setCellStyle(cellStyle);

        HSSFCell header6 = getCell(sheet, currentRow, PROXY_COLUMN);
        setText(header6, "ProxyEmployee");
        header6.setCellStyle(cellStyle);

        HSSFCell header7 = getCell(sheet, currentRow, PROJECT_COLUMN);
        setText(header7, "Project");
        header7.setCellStyle(cellStyle);

        HSSFCell header8 = getCell(sheet, currentRow, DEPT_COLUMN);
        setText(header8, "Department");
        header8.setCellStyle(cellStyle);

        HSSFCell header9 = getCell(sheet, currentRow, WORK_COLUMN);
        setText(header9, "WorkDescription");
        header9.setCellStyle(cellStyle);

        HSSFCell header10 = getCell(sheet, currentRow, WORK_COLUMN);
        setText(header10, "");
        header10.setCellStyle(cellStyle);

        List<EmpTransaction> trans_list = (List<EmpTransaction>) model.get(TRANS_LIST);

        Time total = new Time(00, 00, 00);

        String str = null;
        Time t1 = null;
        Time datetotal = new Time(00, 00, 00);
        String time = null;
        String datetotal1 = null;
         String dayname = null;

        HSSFRow row = sheet.createRow(currentRow);

        for (int i = 0; i < trans_list.size(); i++) {
            currentRow++;
            EmpTransaction et = (EmpTransaction) trans_list.get(i);
            row = sheet.createRow(currentRow);

            if (i == 0 || (str.equals(et.getTrans_date()))) {
                str = et.getTrans_date();

              row.createCell(DATE_COLUMN).setCellValue(et.getTrans_date());

               String day = et.getTrans_date();
               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
               Date myDate = formatter.parse(day);
               
            Calendar cal = Calendar.getInstance();
             cal.setTime(myDate);

               int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
               switch (dayOfWeek){
        case 1:
           dayname = "Sunday";
           break;
        case 2:
          dayname = "Monday";
          break;
        case 3:
          dayname = "Tuesday";
          break;
        case 4:
          dayname = "Wednesday";
          break;
          case 5:
          dayname = "Thursday";
          break;
          case 6:
          dayname = "Friday";
          break;
          case 7:
          dayname = "Saturday";
          break;
        default:
          //System.out.println("Invalid Entry!");
      }

               row.createCell(DAY_COLUMN).setCellValue(dayname);

                row.createCell(EMPNAME_COLUMN).setCellValue(et.getEmp_fname());
                
                String deltime1 = et.getHour();
                int first = deltime1.toString().indexOf(0) + 1;

                int first1 = deltime1.toString().indexOf(":");

                String h1 = deltime1.substring(first, first1);
                int second = deltime1.toString().indexOf(":") + 1;
                String m1 = deltime1.substring(second, deltime1.toString().length());
                int sh = Integer.parseInt(m1);
                int h = Integer.parseInt(h1);
               int m = ((sh*100)/60);
String m2;
  //  String newI = null;
        switch (m) {
            case 0:
                m2 = "00";
                break;
            case 1:
                m2 = "01";
                break;
            case 2:
                m2 = "02";
                break;
            case 3:
                m2 = "03";
                break;
            case 4:
                m2 = "04";
                break;
            case 5:
                
                m2 = "05";
                //System.out.println("shuchi" +m2);
                break;
            case 6:
               m2 = "06";
                break;
            case 7:
                m2 = "07";
                break;
            case 8:
                m2 = "08";
                break;
           case 9:
                m2 = "09";
                break;
            default:
                m2 = Integer.toString(m);
        }
      Integer m3 =Integer.parseInt(m2);
    
//Time m6 = new Time(00, m, 00);
               // String aString = Integer.toString(m);
               //double m5 = Double.parseDouble(+ h + "." + m);
             //String myString = Integer.toString(m);
             //myString.setDoubleDigit();
               double m5 = Double.parseDouble(+ h + "." + m2);
              // m5.setDoubleDigit1();
               //Time m6 = new Time(h, m, 00);
       // String myString = Double.toString(m5);
        // myString = new Time();
       // m5.setDoubleDigit();
               row.createCell((short) HOURS_COLUMN).setCellValue(m5);
              
              //  double atedsh = Double.parseDouble(+ h + "." + m);
               //System.out.println(+ h + "." + m5);

                 t1 = new Time(h, m3, 00);

                total = t1.addTime1(total, t1);
                total.setDoubleDigit();

                time = total.hour1 + ":" + total.minute1;
                datetotal = datetotal.addTime1(datetotal, t1);
                datetotal.setDoubleDigit();
               datetotal1 = datetotal.hour1 + "." + datetotal.minute1;
                row.createCell(TOTALHOURS_COLUMN).setCellValue("");
                
                row.createCell(ASSIGNBY_COLUMN).setCellValue(et.getAssign_by_name());
                row.createCell(PROXY_COLUMN).setCellValue(et.getProxy_empid());
                row.createCell(PROJECT_COLUMN).setCellValue(et.getProj_name());
                row.createCell(DEPT_COLUMN).setCellValue(et.getDept_name());
                HSSFCell work_text = row.createCell(WORK_COLUMN);
                work_text.setCellValue(et.getWork_desc());
                //work_text.setCellStyle(wrapText);
              sheet.setColumnWidth((short) 9, (short) 45000);
         
            } else {
                str = et.getTrans_date();
                row = sheet.createRow(currentRow);
                currentCol += 2;
                HSSFCell cell_tot = row.createCell(currentCol);
                datetotal.hour = 0;
                datetotal.minute = 0;
                datetotal.second = 0;
                cell_tot.setCellValue(datetotal1);
                cell_tot.setCellStyle(cellStyle);
                row = sheet.createRow(currentRow++);
                i += -1;
                currentCol = 2;
            }
        }
        currentRow++;
        HSSFRow row1 = sheet.createRow(currentRow += 1);
        //currentCol += 2;
        currentCol += 2;
        HSSFCell cell_tot_last = row1.createCell(currentCol);
        cell_tot_last.setCellValue(datetotal1);
        cell_tot_last.setCellStyle(cellStyle);
        ++currentRow;
        HSSFRow row2 = sheet.createRow(currentRow);
        HSSFCell cell_total = row2.createCell(currentCol += -2);
        cell_total.setCellValue("TOTAL");
        currentCol++;
        cell_total.setCellStyle(cellStyle);
        HSSFCell cell_total_time = row2.createCell(currentCol);
        cell_total_time.setCellValue(time);
        cell_total_time.setCellStyle(cellStyle);
    }   
    
}
