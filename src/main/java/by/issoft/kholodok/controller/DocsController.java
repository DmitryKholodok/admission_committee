package by.issoft.kholodok.controller;

import by.issoft.kholodok.service.docs.AdmissionCommitteeDocs;
import by.issoft.kholodok.util.DocsResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@RestController
@RequestMapping(value = "/docs")
public class DocsController {

    @Autowired
    private AdmissionCommitteeDocs specialtyStatService;

    @GetMapping(value = "/csv/stat")
    public void retrieveEnrolleeStatCsv(HttpServletResponse response) {
        DocsResponseUtil.addCsvHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.csv(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/excel/stat")
    public void retrieveEnrolleeStatExcel(HttpServletResponse response) {
        DocsResponseUtil.addExcelHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.excel(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/pdf/stat")
    public void retrieveEnrolleeStatPdf(HttpServletResponse response) {
        DocsResponseUtil.addPdfHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.pdf(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
