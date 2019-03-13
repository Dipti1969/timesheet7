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

public class EmployeeWiseExcelView extends AbstractExcelView {

    public static final String TRANS_LIST = null;
    protected static final short YEAR_COLUMN = 0;
    protected static final short MONTH_COLUMN = 1;
    protected static final short DATE_COLUMN = 2;
    protected static final short HOURS_COLUMN = 3;
    protected static final short TOTALHOURS_COLUMN = 4;
    protected static final short ASSIGNBY_COLUMN = 5;
    protected static final short PROXY_COLUMN = 6;
    protected static final short PROJECT_COLUMN = 7;
    protected static final short DEPT_COLUMN = 8;
    protected static final short WORK_COLUMN = 9;

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("EmployeeWise Report");
        sheet.setDefaultColumnWidth((short) 15);

        //GETCELL: getCell(SHEET, ROW, COLUMN);
        short currentRow = 0;
        short currentCol = 2;

        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        HSSFCellStyle wrapText = workbook.createCellStyle();
        wrapText.setWrapText(true);

        //WRITE ROW FOR HEADER
        HSSFCell header0 = getCell(sheet, currentRow, YEAR_COLUMN);
        setText(header0, "Year");
        header0.setCellStyle(cellStyle);

        HSSFCell header1 = getCell(sheet, currentRow, MONTH_COLUMN);
        setText(header1, "Month");
        header1.setCellStyle(cellStyle);

        HSSFCell header2 = getCell(sheet, currentRow, DATE_COLUMN);
        setText(header2, "Date");
        header2.setCellStyle(cellStyle);

        HSSFCell header3 = getCell(sheet, currentRow, HOURS_COLUMN);
        setText(header3, "Hours");
        header3.setCellStyle(cellStyle);

        HSSFCell header4 = getCell(sheet, currentRow, TOTALHOURS_COLUMN);
        setText(header4, "TotalHours");
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

        List<EmpTransaction> trans_list = (List<EmpTransaction>) model.get(TRANS_LIST);
        String year = null;
        Time total = new Time(00, 00, 00);
        Time month1 = new Time(00, 00, 00);
        String total1 = null;
        String month2 = null;
        String month = null;
        Time t1;

        String date = null;

        HSSFRow row = sheet.createRow(currentRow);
        for (int i = 0; i < trans_list.size(); i++) {
            currentRow++;
            EmpTransaction et = (EmpTransaction) trans_list.get(i);
            row = sheet.createRow(currentRow);
            if (i == 0 || year.equals(et.getYear())) {
                year = et.getYear();
                if (i == 0 || month.equals(et.getMonth())) {
                    month = et.getMonth();
//                if(i == 0 || date.equals(et.getTrans_date())){
//                    date=et.getTrans_date();
                    row.createCell(YEAR_COLUMN).setCellValue(et.getYear());
                    row.createCell(MONTH_COLUMN).setCellValue(et.getMonth());
                    row.createCell(DATE_COLUMN).setCellValue(et.getTrans_date());
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
                    total1 = total.hour1 + ":" + total.minute1;

                    month1 = t1.addTime(month1, t1);
                    month1.setDoubleDigit();
                    month2 = month1.hour1 + ":" + month1.minute1;

                    row.createCell(ASSIGNBY_COLUMN).setCellValue(et.getAssign_by_name());
                    row.createCell(PROXY_COLUMN).setCellValue(et.getProxy_empid());
                    row.createCell(PROJECT_COLUMN).setCellValue(et.getProj_name());
                    row.createCell(DEPT_COLUMN).setCellValue(et.getDept_name());
                    HSSFCell work_text = row.createCell(WORK_COLUMN);
                    work_text.setCellValue(et.getWork_desc());
                    work_text.setCellStyle(wrapText);
                    sheet.setColumnWidth((short) 9, (short) 20000);
//                }else{
//                        date=et.getTrans_date();
//                        i += -1;
//
//                        row =sheet.createRow(currentRow);
//                        HSSFCell cell_date=row.createCell(currentCol);
//                        cell_date.setCellValue("date");
//                        cell_date.setCellStyle(cellStyle);
//                }

                } else {
                    month = et.getMonth();
                    currentCol += 2;
                    month1.hour = 0;
                    month1.minute = 0;
                    month1.second = 0;
                    row = sheet.createRow(currentRow);
                    HSSFCell cell_month = row.createCell(currentCol);
                    cell_month.setCellValue(month2);
                    cell_month.setCellStyle(cellStyle);
                    i += -1;
                    currentCol = 2;
                }


            } else {
                row = sheet.createRow(currentRow);
                currentCol += 4;
                HSSFCell cell_tot = row.createCell(currentCol);
                cell_tot.setCellValue(total1);
                cell_tot.setCellStyle(cellStyle);
                row = sheet.createRow(currentRow++);


                total.hour = 0;
                total.minute = 0;
                total.second = 0;
                year = et.getYear();
                i += -1;
                currentCol = 2;

            }
        }

        currentRow++;
        HSSFRow row1 = sheet.createRow(currentRow);
        currentCol += 2;
        HSSFCell cell_emp_last = row1.createCell(currentCol);
        cell_emp_last.setCellValue(month2);
        cell_emp_last.setCellStyle(cellStyle);
        ++currentRow;
        HSSFRow row2 = sheet.createRow(currentRow);
        HSSFCell cell_tot = row2.createCell(--currentCol);
        cell_tot.setCellValue("TOTAL");
        currentCol++;
        cell_tot.setCellStyle(cellStyle);
        HSSFCell cell_total_time = row2.createCell(currentCol);
        cell_total_time.setCellValue(total1);
        cell_total_time.setCellStyle(cellStyle);
    }
}
