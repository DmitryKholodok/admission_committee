package by.issoft.kholodok.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/17/18
 */

public class ExcelUtil {

    private static final Integer MAX_WIDTH = 200;

    public static void createTable(XSSFSheet spreadsheet, List<Object[]> rowsList, String title, int rowId, int cellId) {
        XSSFRow titleRow = spreadsheet.createRow(rowId);
        Cell titleCell = titleRow.createCell(cellId);
        XSSFCellStyle titleStyle = spreadsheet.getWorkbook().createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        spreadsheet.addMergedRegion(new CellRangeAddress(
                rowId,
                rowId,
                cellId,
                cellId + rowsList.get(0).length - 1
        ));
        rowId += 1;

        for (Object[] row : rowsList) {
            XSSFRow xRow = spreadsheet.createRow(rowId++);

            int currCell = cellId;
            for (Object value : row) {
                Cell cell = xRow.createCell(currCell);
                cell.setCellValue(value.toString());
                spreadsheet.autoSizeColumn(currCell);
                currCell += 1;
            }
        }
    }

    public static void styleTable(XSSFWorkbook workbook, XSSFSheet sheet){
        XSSFCellStyle style = workbook.createCellStyle();
        style.setShrinkToFit(true);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        Integer maxCount = 0;
        for(int i = 0; i <= sheet.getPhysicalNumberOfRows(); i++){
            if(sheet.getRow(i) == null) continue;
            Integer count = sheet.getRow(i).getPhysicalNumberOfCells();
            if(count > maxCount)
                maxCount = count;
        }

        List<Integer> columnMaxWidths = new ArrayList<>();
        for (int i = 0; i <= maxCount; i++)
            columnMaxWidths.add(0);

        for(int i = 0; i <= sheet.getPhysicalNumberOfRows(); i++){
            int maxWidth = 0;
            if(sheet.getRow(i) == null) continue;
            for(int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells(); j++){
                if(sheet.getRow(i).getCell(j) == null) continue;
                XSSFCell cell = sheet.getRow(i).getCell(j);
                cell.getCellStyle().getFont();
                cell.setCellStyle(style);
                Integer width = GetWidth(cell);
                if(columnMaxWidths.get(j) < width)
                    columnMaxWidths.set(j, width);
            }
        }
        for(int i = 0; i < maxCount; i++)
            sheet.setColumnWidth(i, columnMaxWidths.get(i) * 50);
    }

    private static Integer GetWidth(XSSFCell cell){
        XSSFFont xssfFont = cell.getCellStyle().getFont();
        FontFamily family = FontFamily.valueOf((xssfFont).getFamily());
        Font font =  new Font(family.name(), Font.PLAIN, xssfFont.getFontHeightInPoints());
        FontMetrics metrics = new FontMetrics(font) {
        };
        Rectangle2D bounds = metrics.getStringBounds(cell.getStringCellValue() + " ", null);
        int widthInPixels = (int) bounds.getWidth();
        if(widthInPixels > MAX_WIDTH)
            return MAX_WIDTH;
        else
            return widthInPixels;
    }

}
