package by.issoft.kholodok.service.docs.impl;

import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.docs.AdmissionCommitteeDocs;
import by.issoft.kholodok.service.docs.ImageBackgroundHelper;
import by.issoft.kholodok.util.ExcelUtil;
import by.issoft.kholodok.util.PdfUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dmitrykholodok on 5/22/18
 */

@Service
@Qualifier("enrolleeDocs")
public class EnrolleeDocsService implements AdmissionCommitteeDocs<EnrolleeData> {

    private static final String[] HEADERS = { "DOC NAME", "DOC ID", "SUBJECT", "DATE OF ISSUE", "POINT", "COUNT" };

    @Override
    public ByteArrayOutputStream csv(EnrolleeData enrolleeData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(HEADERS);
        retrieveTargetRows(enrolleeData).forEach(x -> csvWriter.writeNext(x));
        csvWriter.flush();
        csvWriter.close();
        return outputStream;
    }

    @Override
    public ByteArrayOutputStream pdf(EnrolleeData enrolleeData) throws IOException {
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

            PdfUtil.addTitle(document, "Enrollee документы, которые я забираю:");

            PdfPTable userTable = new PdfPTable(1);
            userTable.setWidths(new int[]{ 1 });
            PdfUtil.addTableHeader(userTable, Element.ALIGN_LEFT, "");
            enrolleeData.getCertificates().forEach(x -> {
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Док name : "  + "Certificate");
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Doc id : " + x.getId());
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Subject : " + x.getSubject().getName());
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Date of issue : " + x.getDateOfIssue());
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Point : " + x.getPoint());
                PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Count : 1" );
                PdfUtil.addTableRow(userTable, Element.ALIGN_LEFT, " ");
            });
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Док name : "  + "Basic Certificate");
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Doc id : " + enrolleeData.getBasicCertificate().getId());
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Subject : - ");
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Date of issue : " + enrolleeData.getBasicCertificate().getDateOfIssue());
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Point : " + enrolleeData.getBasicCertificate().getPoint());
            PdfUtil.addCell(userTable, Element.ALIGN_LEFT, "Count : 1" );
            PdfUtil.addTableRow(userTable, Element.ALIGN_LEFT, " ");

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
    public ByteArrayOutputStream excel(EnrolleeData enrolleeData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet usersInfo = workbook.createSheet("Enrollee Docs");
        java.util.List<Object[]> wardStat = new ArrayList<>();
        wardStat.add(HEADERS);
        retrieveTargetRows(enrolleeData).forEach(x -> wardStat.add(x));
        ExcelUtil.createTable(usersInfo, wardStat, "Enrollee Docs", 1, 1);
        ExcelUtil.styleTable(workbook, usersInfo);
        workbook.write(outputStream);
        return outputStream;
    }

    private List<String[]> retrieveTargetRows(EnrolleeData enrolleeData) {
        List<String[]> rows = new ArrayList<>();
        enrolleeData.getCertificates().forEach(x -> rows.add(new String[] {
                "Certificate",
                Integer.toString(x.getId()),
                x.getSubject().getName(),
                x.getDateOfIssue().toString(),
                Integer.toString(x.getPoint()),
                "1"
        }));
        rows.add(new String[] {
                "Basic Certificate",
                Integer.toString(enrolleeData.getBasicCertificate().getId()),
                "-",
                enrolleeData.getBasicCertificate().getDateOfIssue().toString(),
                Integer.toString(enrolleeData.getBasicCertificate().getPoint()),
                "1"
        });
        return rows;
    }

}
