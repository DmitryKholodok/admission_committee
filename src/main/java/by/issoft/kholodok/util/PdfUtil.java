package by.issoft.kholodok.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;

/**
 * Created by dmitrykholodok on 5/17/18
 */
public class PdfUtil {

    public static final String FONT = "11835.ttf";

    public static String USER_PASSWORD = "password";
    public static String OWNER_PASSWORD = "secured";

    public static Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD,  "UTF-8",false,  14, -1, BaseColor.BLACK);
    public static Font textFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);

    public static void addTitle(Document document, String text) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.BLACK);
        Paragraph title = new Paragraph(text, titleFont);
        title.setSpacingAfter(10f);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    public static void addUnderlinedHeader(Document document, String text, int alignment) throws DocumentException {
        Font underlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.UNDERLINE);

        Chunk patientChunk = new Chunk(text, underlineFont);
        Paragraph header = new Paragraph(patientChunk);
        header.setSpacingAfter(10f);
        header.setAlignment(alignment);
        document.add(header);
    }

    public static void addParagraph(Document document, Chunk ...chunks) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
        paragraph.setSpacingAfter(10f);

        for (Chunk chunk : chunks) {
            paragraph.add(chunk);
        }

        document.add(paragraph);
    }

    public static void addListItem(Document document, Chunk chunk) throws DocumentException {
        Paragraph listItem = new Paragraph();

        Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 8);
        Chunk bullet = new Chunk(String.valueOf((char) 108), zapfdingbats);

        listItem.setSpacingAfter(10f);
        listItem.add(Chunk.TABBING);
        listItem.add(bullet);
        listItem.add("   ");
        listItem.add(chunk);

        document.add(listItem);
    }

    public static void addParagraphsToListItem(Document document, Chunk ...chunks) throws DocumentException {
        for (Chunk chunk : chunks) {
            Paragraph paragraph = new Paragraph();

            BaseFont bf = null;
            try {
                bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Font font = new Font(bf);
            paragraph.setFont(font);
            paragraph.setSpacingAfter(10f);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(chunk);

            document.add(paragraph);
        }
    }

    public static void addTableHeader(PdfPTable table, int horizontalAlignment, String ...headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD)));
            cell.setLeft(0);
            cell.setBorder(0);
            cell.setHorizontalAlignment(horizontalAlignment);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }


    public static PdfPCell CreateCell( int horizontalAlignment, String rowCell){
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf);
        PdfPCell cell = new PdfPCell(new Phrase(
                rowCell,
                font));
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

    public static void addCell(PdfPTable table, int horizontalAlignment, String rowCell) {
        PdfPCell cell = CreateCell(horizontalAlignment, rowCell);
        table.addCell(cell);
    }

    public static void addTableRow(PdfPTable table, int horizontalAlignment, String ...rowCells) {
        for (String cellText : rowCells) {
            PdfPCell cell = CreateCell(horizontalAlignment, cellText);
            table.addCell(cell);
        }
    }

}
