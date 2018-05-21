package by.issoft.kholodok.service.docs.impl;

import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.service.UniverService;
import by.issoft.kholodok.service.docs.AdmissionCommitteeDocs;
import by.issoft.kholodok.service.docs.ImageBackgroundHelper;
import by.issoft.kholodok.util.ExcelUtil;
import by.issoft.kholodok.util.PdfUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by dmitrykholodok on 5/13/18
 */

@Service
@Transactional
public class SpecialtyStatService implements AdmissionCommitteeDocs {

    @Autowired
    private UniverService univerService;

    private static final String[] HEADERS = { "SPECIALTY", "FACULTY_РУССКИЙ", "BUDGETARY PLACE COUNT", "CHARGEABLE PLACE COUNT" };

    @Override
    public ByteArrayOutputStream csv(Object key) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(HEADERS);
        retrieveTargetRows().forEach(x -> csvWriter.writeNext(x));
        csvWriter.flush();
        csvWriter.close();
        return outputStream;
    }

    @Override
    public ByteArrayOutputStream pdf(Object key) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Security.addProvider(new BouncyCastleProvider());
            writer.setEncryption(null, PdfUtil.OWNER_PASSWORD.getBytes(), 0 , PdfWriter.STANDARD_ENCRYPTION_128);

            document.open();

            Image image = Image.getInstance(Objects.requireNonNull(this.getClass().getClassLoader().getResource("background.png")));
            image.scaleAbsolute(PageSize.A4);
            image.setAbsolutePosition(0, 0);
            writer.setPageEvent(new ImageBackgroundHelper(image));

            PdfUtil.addTitle(document, "BSUIR-2018");

            PdfPTable userTable = new PdfPTable(1);
            userTable.setWidths(new int[]{ 1 });
            PdfUtil.addTableHeader(userTable, Element.ALIGN_LEFT, "");
            List<Faculty> faculties = univerService.retrieveFaculties();
            faculties.forEach(x -> {
                x.getSpecialtySet().forEach(y -> {
                    PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Faculty Факультет: "  + x.getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Specialty:" + y.getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Budgetary place count: " + Integer.toString(y.getBudgetaryPlaceCount()));
                    PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Chargeable place count: " + Integer.toString(y.getChargeablePlaceCount()));
                    PdfUtil.addTableRow(userTable, Element.ALIGN_LEFT, " ");
                    });
                });
            document.add(userTable);
            document.close();
            Security.addProvider(new BouncyCastleProvider());
            writer.setEncryption(PdfUtil.USER_PASSWORD.getBytes(), PdfUtil.OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream;
    }

    @Override
    public ByteArrayOutputStream excel(Object key) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet usersInfo = workbook.createSheet("РУССКИйStatistics-2018");
        java.util.List<Object[]> wardStat = new ArrayList<>();
        wardStat.add(HEADERS);
        retrieveTargetRows().forEach(x -> wardStat.add(x));
        ExcelUtil.createTable(usersInfo, wardStat, "Statistics-2018", 1, 1);
        ExcelUtil.styleTable(workbook, usersInfo);
        workbook.write(outputStream);
        return outputStream;
    }

    private List<String[]> retrieveTargetRows() {
            List<String[]> result = new ArrayList<>();
            List<Faculty> faculties = univerService.retrieveFaculties();
            faculties.forEach(x -> {
                x.getSpecialtySet().forEach(y -> {
                    result.add(new String[] {
                            y.getName(),
                            x.getName() + " РУССКИЙСРКИСЙКЙЦКЫФАЫВ111111123123123123123123123123123123123123123123123111111123123123123123123123123123123123123123123123111111123123123123123123123123123123123123123123123111111123123123123123123123123123123123123123123123111111123123123123123123123123123123123123123123123111111123123123123123123123123123123123123123123123",
                            Integer.toString(y.getBudgetaryPlaceCount()),
                            Integer.toString(y.getChargeablePlaceCount()),
                    });
                });
            });
            return result;
        }
}

