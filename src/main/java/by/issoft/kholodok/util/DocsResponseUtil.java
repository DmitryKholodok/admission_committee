package by.issoft.kholodok.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dmitrykholodok on 5/16/18
 */

public class DocsResponseUtil {

    public static void addCsvHeaders(HttpServletResponse response, String filename) {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
    }

    public static void addExcelHeaders(HttpServletResponse response, String filename) {
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
    }

    public static void addPdfHeaders(HttpServletResponse response, String filename) {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
    }

}
