package com.timesheet.report;

import com.timesheet.bean.EmpTransaction;
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

public class DateWiseExcelView extends AbstractExcelView {

    public static final String WIDGET_LIST_KEY = null;

    protected static final short WIDGET_EMPNAME_COLUMN = 0;
    protected static final short WIDGET_HOURS_COLUMN = 1;
    protected static final short WIDGET_NEWHOURS_COLUMN = 1;
    protected static final short WIDGET_TOTALHOURS_COLUMN = 2;
    protected static final short WIDGET_ASSIGNBY_COLUMN = 3;
    protected static final short WIDGET_PROXY_COLUMN = 4;
    protected static final short WIDGET_PROJECT_COLUMN = 5;
    protected static final short WIDGET_DEPT_COLUMN = 6;
    protected static final short WIDGET_WORK_COLUMN = 7;
   // protected static final short REPORT_HEADER = 8;

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //CREATE THE SHEET
        HSSFSheet sheet = workbook.createSheet("Report");
        sheet.setDefaultColumnWidth((short) 15);

        //GETCELL: getCell(SHEET, ROW, COLUMN);
        short currentRow = 0;
        short currentCol = 0;

        //WRITE ROW FOR HEADER
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        HSSFCellStyle wrapText = workbook.createCellStyle();
        wrapText.setWrapText(true);

   //     HSSFRow r = sheet.createRow(0);


        HSSFCell header0 = getCell(sheet, currentRow, WIDGET_EMPNAME_COLUMN);
        setText(header0, "EmployeeName");
        header0.setCellStyle(cellStyle);


        HSSFCell header1 = getCell(sheet, currentRow, WIDGET_HOURS_COLUMN);
        setText(header1, "Hours");
        header1.setCellStyle(cellStyle);

        HSSFCell header2 = getCell(sheet, currentRow, WIDGET_TOTALHOURS_COLUMN);
        setText(header2, "Total Hours");
        header2.setCellStyle(cellStyle);


        HSSFCell header3 = getCell(sheet, currentRow, WIDGET_ASSIGNBY_COLUMN);
        setText(header3, "AssignBy");
        header3.setCellStyle(cellStyle);


        HSSFCell header4 = getCell(sheet, currentRow, WIDGET_PROXY_COLUMN);
        setText(header4, "ProxyEmployee");
        header4.setCellStyle(cellStyle);


        HSSFCell header5 = getCell(sheet, currentRow, WIDGET_PROJECT_COLUMN);
        setText(header5, "Project");
        header5.setCellStyle(cellStyle);


        HSSFCell header6 = getCell(sheet, currentRow, WIDGET_DEPT_COLUMN);
        setText(header6, "Department");
        header6.setCellStyle(cellStyle);


        HSSFCell header7 = getCell(sheet, currentRow, WIDGET_WORK_COLUMN);
        setText(header7, "WorkDescription");
        header7.setCellStyle(cellStyle);


        List<EmpTransaction> trans_list = (List) model.get(WIDGET_LIST_KEY);
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
                row.createCell(WIDGET_EMPNAME_COLUMN).setCellValue(et.getEmp_fname());
                row.createCell(WIDGET_HOURS_COLUMN).setCellValue(et.getHour());

                String deltime1 = et.getHour();
                int first = deltime1.toString().indexOf(0) + 1;
                int first1 = deltime1.toString().indexOf(":");

                String h = deltime1.substring(first, first1);
                int second = deltime1.toString().indexOf(":") + 1;
                String m = deltime1.substring(second, deltime1.toString().length());
//                int last = deltime1.toString().indexOf(":", second);
//                String s = deltime1.substring(last + 1, deltime1.toString().length());

                t1 = new Time(Integer.parseInt(h), Integer.parseInt(m), 00);

                total = t1.addTime(total, t1);
                total.setDoubleDigit();

                time = total.hour1 + ":" + total.minute1;
                emptotal = emptotal.addTime(emptotal, t1);
                emptotal.setDoubleDigit();
                emptotal1 = emptotal.hour1 + ":" + emptotal.minute1;

                //System.out.println("TOTAL" + total.hour1);
                row.createCell(WIDGET_TOTALHOURS_COLUMN).setCellValue("");
                row.createCell(WIDGET_ASSIGNBY_COLUMN).setCellValue(et.getAssign_by_name());
                row.createCell(WIDGET_PROXY_COLUMN).setCellValue(et.getProxy_empid());
                row.createCell(WIDGET_PROJECT_COLUMN).setCellValue(et.getProj_name());
                row.createCell(WIDGET_DEPT_COLUMN).setCellValue(et.getDept_name());

                HSSFCell work_text = row.createCell(WIDGET_WORK_COLUMN);
                work_text.setCellValue(et.getWork_desc());
                work_text.setCellStyle(wrapText);

                sheet.setColumnWidth((short) 7, (short) 20000);
            } else {
                str = et.getEmp_fname();
                row = sheet.createRow(currentRow);
                currentCol += 2;
                HSSFCell cell_tot = row.createCell(currentCol);
                emptotal.hour = 0;
                emptotal.minute = 0;
                emptotal.second = 0;
                cell_tot.setCellValue(emptotal1);
                cell_tot.setCellStyle(cellStyle);
                row = sheet.createRow(currentRow++);
                i += -1;
                currentCol = 0;
            }
        }
        currentRow++;
        HSSFRow row1 = sheet.createRow(currentRow);
        currentCol += 2;

        HSSFCell cell_tot_last = row1.createCell(currentCol);
        cell_tot_last.setCellValue(emptotal1);
        cell_tot_last.setCellStyle(cellStyle);
        ++currentRow;
        HSSFRow row2 = sheet.createRow(currentRow);
        HSSFCell cell_total = row2.createCell(--currentCol);
        cell_total.setCellValue("TOTAL");
        currentCol++;
        cell_total.setCellStyle(cellStyle);
        HSSFCell cell_total_time = row2.createCell(currentCol);
        cell_total_time.setCellValue(time);
        cell_total_time.setCellStyle(cellStyle);


    }
}
