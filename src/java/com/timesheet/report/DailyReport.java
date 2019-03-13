
package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
import com.timesheet.bean.Employee;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class DailyReport extends AbstractExcelView {

    public static final String WIDGET_LIST_KEY = null;
    public static final String WIDGET_EMPNAME_KEY = null;
    protected static final short WIDGET_EMPNAME_COLUMN = 0;
    protected static final short WIDGET_HOURS_COLUMN = 1;
    protected static final short WIDGET_ASSIGNBY_COLUMN = 2;
    protected static final short WIDGET_PROJECT_COLUMN = 3;
    protected static final short WIDGET_DEPT_COLUMN = 4;
    protected static final short WIDGET_WORK_COLUMN = 5;
    
 
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //CREATE THE SHEET
        HSSFSheet sheet = workbook.createSheet("Report");
        sheet.setGridsPrinted(true);

        HSSFPrintSetup ps = sheet.getPrintSetup();
        sheet.setAutobreaks(true);
        ps.setFitHeight((short)2);
        ps.setFitWidth((short)1);

        sheet.setDefaultColumnWidth((short) 12);
        sheet.setColumnWidth((short) 1, (short) 2000);
        sheet.setColumnWidth((short) 2, (short) 4000);
        sheet.setColumnWidth((short) 4, (short) 2500);
        sheet.setColumnWidth((short) 6, (short) 8000);
        sheet.setMargin(sheet.LeftMargin,0.50);
        sheet.setMargin(sheet.RightMargin,0.50);
        //GETCELL: getCell(SHEET, ROW, COLUMN);

        short currentRow = 0;
        short currentCol = 0;



       String dt = request.getParameter("daily_txt");
            String date = Insert_fmt_date(dt);

        HSSFRow datetype = sheet.createRow(currentRow);
        HSSFCell timesheet = datetype.createCell(currentCol);
        timesheet.setCellValue("Date:");
           //System.out.println("shshshshsh" +date);
        HSSFCell fordate = datetype.createCell(++currentCol);
       fordate.setCellValue(date);
      
        currentRow = 2;
        currentCol = 0;
        //WRITE ROW FOR HEADER
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);
        cellStyle.setWrapText(true);

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");


        HSSFCellStyle wrapText = workbook.createCellStyle();
        wrapText.setWrapText(true);
        wrapText.setFont(font);
        wrapText.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        //     HSSFRow r = sheet.createRow(0);

        HSSFCell header1 = getCell(sheet, currentRow, WIDGET_HOURS_COLUMN);
        setText(header1, "Hours");
        header1.setCellStyle(cellStyle);

    
        HSSFCell header2 = getCell(sheet, currentRow, WIDGET_ASSIGNBY_COLUMN);
        setText(header2, "AssignBy");
        header2.setCellStyle(cellStyle);


        HSSFCell header3 = getCell(sheet, currentRow, WIDGET_PROJECT_COLUMN);
        setText(header3, "Project");
        header3.setCellStyle(cellStyle);


        HSSFCell header4 = getCell(sheet, currentRow, WIDGET_DEPT_COLUMN);
        setText(header4, "Dept.");
        header4.setCellStyle(cellStyle);


        HSSFCell header5 = getCell(sheet, currentRow, WIDGET_WORK_COLUMN);
        setText(header5, "Work Description");
        header5.setCellStyle(cellStyle);


        List<EmpTransaction> trans_list = (List) model.get(WIDGET_LIST_KEY);
         List<Employee> empname = (List) model.get("emplist");
        Time total = new Time(00, 00, 00);
        Time emptotal = new Time(00, 00, 00);



        String str = null;
        Time t1 = null;
        String time = null;
        String emptotal1 = null;

        HSSFRow row = sheet.createRow(currentRow);

        for (int i = 0; i < trans_list.size(); i++) {
            currentRow++;
           EmpTransaction et = (EmpTransaction) trans_list.get(i);
         
            if (i == 0 || (str.equals(et.getEmp_fname()))) {
                str = et.getEmp_fname();
       
                row = sheet.createRow(currentRow);
                HSSFCell emp_name = row.createCell(WIDGET_EMPNAME_COLUMN);
                emp_name.setCellValue(et.getEmp_fname());
                emp_name.setCellStyle(wrapText);
                HSSFCell hours = row.createCell(WIDGET_HOURS_COLUMN);
                hours.setCellValue(et.getHour());
                hours.setCellStyle(wrapText);
              
                String deltime1 = et.getHour();
                int first = deltime1.toString().indexOf(0) + 1;
                int first1 = deltime1.toString().indexOf(":");

                String h = deltime1.substring(first, first1);
               int second = deltime1.toString().indexOf(":") + 1;
               String m = deltime1.substring(second, deltime1.toString().length());
                int last = deltime1.toString().indexOf(":", second);
                String s = deltime1.substring(last + 1, deltime1.toString().length());

                t1 = new Time(Integer.parseInt(h), Integer.parseInt(m), 00);

                total = t1.addTime(total, t1);
                total.setDoubleDigit();

                time = total.hour1 + ":" + total.minute1;
                emptotal = emptotal.addTime(emptotal, t1);
                emptotal.setDoubleDigit();
                emptotal1 = emptotal.hour1 + ":" + emptotal.minute1;

                //System.out.println("TOTAL" + total.hour1);
                //   row.createCell(WIDGET_TOTALHOURS_COLUMN).setCellValue("");

                HSSFCell assignby = row.createCell(WIDGET_ASSIGNBY_COLUMN);
                assignby.setCellValue(et.getAssign_by_name());
                assignby.setCellStyle(wrapText);

//                HSSFCell Proxy_emp = row.createCell(WIDGET_PROXY_COLUMN);
//                Proxy_emp.setCellValue(et.getProxy_empid());
//                Proxy_emp.setCellStyle(wrapText);

                HSSFCell Project = row.createCell(WIDGET_PROJECT_COLUMN);
                Project.setCellValue(et.getProj_name());
                Project.setCellStyle(wrapText);

                HSSFCell DepartName = row.createCell(WIDGET_DEPT_COLUMN);
                DepartName.setCellValue(et.getDept_name());
                DepartName.setCellStyle(wrapText);

                 

                HSSFCell work_text = row.createCell(WIDGET_WORK_COLUMN);
                work_text.setCellValue(et.getWork_desc());
                work_text.setCellStyle(wrapText);

                sheet.setColumnWidth((short) 5, (short) 10000);
            } else {
                str = et.getEmp_fname();
                row = sheet.createRow(currentRow--);
                currentCol += 2;
                 HSSFCell cell_tot = row.createCell(currentCol);
                emptotal.hour = 0;
                emptotal.minute = 0;
                emptotal.second = 0;
                //     cell_tot.setCellValue(emptotal1);
                //  cell_tot.setCellStyle(cellStyle);
                row = sheet.createRow(currentRow++);
               
                i += -1;
                currentCol = 0;
            }
        }
        currentRow++;
        HSSFRow row1 = sheet.createRow(currentRow);
        currentCol += 1;

        //  HSSFCell cell_tot_last = row1.createCell(currentCol);
        // cell_tot_last.setCellValue(emptotal1);
        // cell_tot_last.setCellStyle(cellStyle);
        ++currentRow;
        HSSFRow row2 = sheet.createRow(currentRow++);
        HSSFCell cell_total = row2.createCell(--currentCol);
        cell_total.setCellValue("TOTAL");
        currentCol++;
        cell_total.setCellStyle(cellStyle);
        HSSFCell cell_total_time = row2.createCell(currentCol);
        cell_total_time.setCellValue(time);
        cell_total_time.setCellStyle(cellStyle);
        //currentRow += 5;
        
        HSSFRow row3 = sheet.createRow(++currentRow);
        HSSFCell employeename = row3.createCell(--currentCol);
        employeename.setCellValue("NAME");
        employeename.setCellStyle(cellStyle);
        currentCol += 3;
        HSSFCell sign = row3.createCell(currentCol);
        sign.setCellValue("SIGN");
        sign.setCellStyle(cellStyle);
        currentCol = 0;
        currentRow+=2;
        for (int i = 0; i < empname.size(); i++) {
            Employee e = empname.get(i);
            row3 = sheet.createRow(currentRow);
            HSSFCell name = row3.createCell(currentCol);
            name.setCellValue(e.getEmp_fname() + " " + e.getEmp_lname());
            currentRow += 2;
        }

    }
public String Insert_fmt_date(String dat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdf.parse(dat);
            //System.out.println("Date in Insert_fmt_date: " + dt);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            String fmt_date = sdf1.format(dt);
            //System.out.println("fmt_date: " + fmt_date);
            return fmt_date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;

        }
    }


 }

