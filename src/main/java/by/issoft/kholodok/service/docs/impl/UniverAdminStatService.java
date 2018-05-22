package by.issoft.kholodok.service.docs.impl;

import by.issoft.kholodok.model.Certificate;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.service.EnrolleeDataService;
import by.issoft.kholodok.service.UserService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by dmitrykholodok on 5/22/18
 */

@Service
@Qualifier("univerAdmin")
public class UniverAdminStatService implements AdmissionCommitteeDocs {

    private static final String[] HEADERS = { "Факультет", "Специальность", "Имя", "Фамилия", "Балл", "Role" };

    @Autowired
    private UserService userService;

    @Autowired
    private EnrolleeDataService enrolleeDataService;

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

            PdfUtil.addTitle(document, "Состояние на " + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + " :");

            PdfPTable userTable = new PdfPTable(1);
            userTable.setWidths(new int[]{ 1 });
            PdfUtil.addTableHeader(userTable, Element.ALIGN_CENTER, "");
            userService.findAll().forEach(x -> {
                EnrolleeData enrolleeData = enrolleeDataService.findById(x.getId());
                if (enrolleeData == null) {
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Имя : " + x.getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Фамилия : " + x.getSurname());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Role : " + x.getUserAuth().getRoleSet().stream().findFirst().get().getName());
                    PdfUtil.addTableRow(userTable, Element.ALIGN_CENTER, " ");
                } else {
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Факультет : "  + enrolleeData.getSpecialtyEnrollee().getSpecialty().getFaculty().getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Специальность : " + enrolleeData.getSpecialtyEnrollee().getSpecialty().getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Имя : " + x.getName());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Фамилия : " + x.getSurname());
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Балл : " + calcEnrolleePoint(enrolleeData));
                    PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Role : " + x.getUserAuth().getRoleSet().stream().findFirst().get().getName());
                    PdfUtil.addTableRow(userTable, Element.ALIGN_CENTER, " ");
                }

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
        XSSFSheet usersInfo = workbook.createSheet("Users");
        java.util.List<Object[]> wardStat = new ArrayList<>();
        wardStat.add(HEADERS);
        retrieveTargetRows().forEach(x -> wardStat.add(x));
        ExcelUtil.createTable(usersInfo, wardStat, "Users", 1, 1);
        ExcelUtil.styleTable(workbook, usersInfo);
        workbook.write(outputStream);
        return outputStream;
    }

    private String calcEnrolleePoint(EnrolleeData enrolleeData) {
        int p = 0;
        Iterator<Certificate> certificateIterator = enrolleeData.getCertificates().iterator();
        while(certificateIterator.hasNext()) {
            p += certificateIterator.next().getPoint();
        }
        p += enrolleeData.getBasicCertificate().getPoint();
        return Integer.toString(p);
    }

    private List<String[]> retrieveTargetRows() {
        List<String[]> rows = new ArrayList<>();
        userService.findAll().forEach(x -> {
            EnrolleeData enrolleeData = enrolleeDataService.findById(x.getId());
            if (enrolleeData == null) {
                rows.add(new String[] {
                        "-",
                        "-",
                        x.getName(),
                        x.getSurname(),
                        "-",
                        x.getUserAuth().getRoleSet().stream().findFirst().get().getName()
                });
            } else {
                rows.add(new String[] {
                        enrolleeData.getSpecialtyEnrollee().getSpecialty().getFaculty().getName(),
                        enrolleeData.getSpecialtyEnrollee().getSpecialty().getName(),
                        x.getName(),
                        x.getSurname(),
                        calcEnrolleePoint(enrolleeData),
                        x.getUserAuth().getRoleSet().stream().findFirst().get().getName()
                });
            }

        });
        return rows;
    }

}
