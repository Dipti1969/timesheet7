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

public class EmpMonthWiseExcelView extends AbstractExcelView {

    public static final String TRANS_LIST = null;
    protected static final short DATE_COLUMN = 0;
    protected static final short EMPNAME_COLUMN = 1;
    protected static final short HOURS_COLUMN = 2;
    protected static final short TOTALHOURS_COLUMN = 3;
    protected static final short ASSIGNBY_COLUMN = 4;
    protected static final short PROXY_COLUMN = 5;
    protected static final short PROJECT_COLUMN = 6;
    protected static final short DEPT_COLUMN = 7;
    protected static final short WORK_COLUMN = 8;

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("MonthWise Report");
        sheet.setDefaultColumnWidth((short) 12);

        //GETCELL: getCell(SHEET, ROW, COLUMN);
        short currentRow = 0;
        short currentCol = 1;

        //WRITE ROW FOR HEADER

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

        HSSFCell header2 = getCell(sheet, currentRow, HOURS_COLUMN);
        setText(header2, "Hours");
        header2.setCellStyle(cellStyle);

        HSSFCell header3 = getCell(sheet, currentRow, TOTALHOURS_COLUMN);
        setText(header3, "Total Hours");
        header3.setCellStyle(cellStyle);


        HSSFCell header4 = getCell(sheet, currentRow, ASSIGNBY_COLUMN);
        setText(header4, "AssignBy");
        header4.setCellStyle(cellStyle);


        HSSFCell header5 = getCell(sheet, currentRow, PROXY_COLUMN);
        setText(header5, "ProxyEmployee");
        header5.setCellStyle(cellStyle);


        HSSFCell header6 = getCell(sheet, currentRow, PROJECT_COLUMN);
        setText(header6, "Project");
        header6.setCellStyle(cellStyle);


        HSSFCell header7 = getCell(sheet, currentRow, DEPT_COLUMN);
        setText(header7, "Department");
        header7.setCellStyle(cellStyle);


        HSSFCell header8 = getCell(sheet, currentRow, WORK_COLUMN);
        setText(header8, "WorkDescription");
        header8.setCellStyle(cellStyle);

        List<EmpTransaction> trans_list = (List<EmpTransaction>) model.get(TRANS_LIST);

        Time total = new Time(00, 00, 00);

        String str = null;
        Time t1 = null;
        Time datetotal = new Time(00, 00, 00);
        String time = null;
        String datetotal1 = null;

        HSSFRow row = sheet.createRow(currentRow);
        for (int i = 0; i < trans_list.size(); i++) {
            currentRow++;
            EmpTransaction et = (EmpTransaction) trans_list.get(i);
            row = sheet.createRow(currentRow);

            if (i == 0 || (str.equals(et.getTrans_date()))) {
                str = et.getTrans_date();
                row.createCell(DATE_COLUMN).setCellValue(et.getTrans_date());
                row.createCell(EMPNAME_COLUMN).setCellValue(et.getEmp_fname());
                row.createCell(HOURS_COLUMN).setCellValue(et.getHour());
                row.createCell(TOTALHOURS_COLUMN).setCellValue("");
                String deltime1 = et.getHour();
                int first = deltime1.toString().indexOf(0) + 1;
                int first1 = deltime1.toString().indexOf(":");

                String h = deltime1.substring(first, first1);
                int second = deltime1.toString().indexOf(":") + 1;
                String m = deltime1.substring(second, deltime1.toString().length());

                t1 = new Time(Integer.parseInt(h), Integer.parseInt(m), 00);

                total = t1.addTime(total, t1);
                total.setDoubleDigit();

                time = total.hour1 + ":" + total.minute1;
                datetotal = datetotal.addTime(datetotal, t1);
                datetotal.setDoubleDigit();
                datetotal1 = datetotal.hour1 + ":" + datetotal.minute1;

                row.createCell(ASSIGNBY_COLUMN).setCellValue(et.getAssign_by_name());
                row.createCell(PROXY_COLUMN).setCellValue(et.getProxy_empid());
                row.createCell(PROJECT_COLUMN).setCellValue(et.getProj_name());
                row.createCell(DEPT_COLUMN).setCellValue(et.getDept_name());
                HSSFCell work_text = row.createCell(WORK_COLUMN);
                work_text.setCellValue(et.getWork_desc());
                work_text.setCellStyle(wrapText);
                sheet.setColumnWidth((short) 8, (short) 20000);
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
                currentCol = 1;
            }
        }
        currentRow++;
        HSSFRow row1 = sheet.createRow(currentRow);
        currentCol += 2;
        HSSFCell cell_tot_last = row1.createCell(currentCol);
        cell_tot_last.setCellValue(datetotal1);
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
