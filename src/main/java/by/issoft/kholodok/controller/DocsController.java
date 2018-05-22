package by.issoft.kholodok.controller;

import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.EnrolleeDataService;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.service.docs.AdmissionCommitteeDocs;
import by.issoft.kholodok.util.DocsResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@RestController
@RequestMapping(value = "/api/docs")
public class DocsController {

    @Autowired
    @Qualifier("specialty")
    private AdmissionCommitteeDocs specialtyStatService;

    @Autowired
    @Qualifier("userDataStat")
    private AdmissionCommitteeDocs userDataStatService;

    @Autowired
    @Qualifier("enrolleeDocs")
    private AdmissionCommitteeDocs enrolleeDocsService;

    @Autowired
    @Qualifier("univerAdmin")
    private AdmissionCommitteeDocs univerAdminStatService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrolleeDataService enrolleeDataService;

    @GetMapping(value = "/csv/stat")
    public void retrieveUniverStatCsv(HttpServletResponse response) {
        DocsResponseUtil.addCsvHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.csv(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/excel/stat")
    public void retrieveUniverStatExcel(HttpServletResponse response) {
        DocsResponseUtil.addExcelHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.excel(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/pdf/stat")
    public void retrieveUniverStatPdf(HttpServletResponse response) {
        DocsResponseUtil.addPdfHeaders(response, "stat");
        try (ByteArrayOutputStream stream = specialtyStatService.pdf(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/users/{id}/csv")
    public void retrieveUserDataCsv(HttpServletResponse response, @PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addCsvHeaders(response, "my");
            try (ByteArrayOutputStream stream = userDataStatService.csv(user)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/users/{id}/excel")
    public void retrieveUserDataExcel(HttpServletResponse response, @PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addExcelHeaders(response, "my");
            try (ByteArrayOutputStream stream = userDataStatService.excel(user)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/users/{id}/pdf")
    public void retrieveUserDataPdf(HttpServletResponse response, @PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addPdfHeaders(response, "my");
            try (ByteArrayOutputStream stream = userDataStatService.pdf(user)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/enrollees/{id}/csv")
    public void retrieveEnrolleeDocsCsv(HttpServletResponse response, @PathVariable int id) {
        EnrolleeData enrolleeData = enrolleeDataService.findById(id);
        if (enrolleeData == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addCsvHeaders(response, "enrollee-docs");
            try (ByteArrayOutputStream stream = enrolleeDocsService.csv(enrolleeData)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/enrollees/{id}/excel")
    public void retrieveEnrolleeDocsExcel(HttpServletResponse response, @PathVariable int id) {
        EnrolleeData enrolleeData = enrolleeDataService.findById(id);
        if (enrolleeData == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addExcelHeaders(response, "enrollee-docs");
            try (ByteArrayOutputStream stream = enrolleeDocsService.excel(enrolleeData)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/enrollees/{id}/pdf")
    public void retrieveEnrolleeDocsPdf(HttpServletResponse response, @PathVariable int id) {
        EnrolleeData enrolleeData = enrolleeDataService.findById(id);
        if (enrolleeData == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            DocsResponseUtil.addPdfHeaders(response, "enrollee-docs");
            try (ByteArrayOutputStream stream = enrolleeDocsService.pdf(enrolleeData)) {
                response.getOutputStream().write(stream.toByteArray());
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    @GetMapping(value = "/general/csv")
    public void retrieveUniverGeneralDocsCsv(HttpServletResponse response) {
        DocsResponseUtil.addCsvHeaders(response, "general-stat");
        try (ByteArrayOutputStream stream = univerAdminStatService.csv(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/general/excel")
    public void retrieveUniverGeneralDocsExcel(HttpServletResponse response) {
        DocsResponseUtil.addExcelHeaders(response, "general-stat");
        try (ByteArrayOutputStream stream = univerAdminStatService.excel(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping(value = "/general/pdf")
    public void retrieveUniverGeneralDocsPdf(HttpServletResponse response) {
        DocsResponseUtil.addPdfHeaders(response, "general-stat");
        try (ByteArrayOutputStream stream = univerAdminStatService.pdf(null)) {
            response.getOutputStream().write(stream.toByteArray());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
