package by.issoft.kholodok.service.docs.impl;

import by.issoft.kholodok.model.Certificate;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.EnrolleeDataService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by dmitrykholodok on 5/21/18
 */

@Service
@Qualifier(value = "userDataStat")
public class UserDataStatService implements AdmissionCommitteeDocs<User> {

    @Autowired
    private EnrolleeDataService enrolleeDataService;

    private static final String[] HEADERS = { "NAME", "SURNAME", "TEL", "DATE OF BIRTH", "POINT" };

    @Override
    public ByteArrayOutputStream csv(User key) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(HEADERS);
        csvWriter.writeNext(retrieveTargetRows(key));
        csvWriter.flush();
        csvWriter.close();
        return outputStream;
    }

    @Override
    public ByteArrayOutputStream pdf(User user) throws IOException {
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

            PdfUtil.addTitle(document, "Данные enrollee:");

            PdfPTable userTable = new PdfPTable(1);
            userTable.setWidths(new int[]{ 1 });
            PdfUtil.addTableHeader(userTable, Element.ALIGN_CENTER, "");

            PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Имя : "  + user.getName());
            PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Фамилия : " + user.getSurname());
            PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Tel : " + user.getTel());
            PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Date of birth : " + date(user.getDateOfBirth()));
            PdfUtil.addCell(userTable, Element.ALIGN_CENTER, "Point : " + calcEnrolleePoint(user));
            PdfUtil.addTableRow(userTable, Element.ALIGN_CENTER, " ");

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
    public ByteArrayOutputStream excel(User user) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet usersInfo = workbook.createSheet("User data");
        java.util.List<Object[]> wardStat = new ArrayList<>();
        wardStat.add(HEADERS);
        wardStat.add( retrieveTargetRows(user));
        ExcelUtil.createTable(usersInfo, wardStat, "User data", 1, 1);
        ExcelUtil.styleTable(workbook, usersInfo);
        workbook.write(outputStream);
        return outputStream;
    }

    private String[] retrieveTargetRows(User user) {
        String userPoint = calcEnrolleePoint(user);
        return new String[] {
                        user.getName(),
                        user.getSurname(),
                        user.getTel(),
                        date(user.getDateOfBirth()),
                        userPoint
                };
    }

    private String calcEnrolleePoint(User user) {
        EnrolleeData enrolleeData = enrolleeDataService.findById(user.getId());
        if (enrolleeData != null) {
            int p = 0;
            Iterator<Certificate> certificateIterator = enrolleeData.getCertificates().iterator();
            while(certificateIterator.hasNext()) {
                p += certificateIterator.next().getPoint();
            }
            p += enrolleeData.getBasicCertificate().getPoint();
            return Integer.toString(p);
        }
        return "You are not абитуриент!";

    }

    private String date(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Set time fields to zero
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().toString();

    }
}
