package by.issoft.kholodok.service.docs;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by dmitrykholodok on 5/17/18
 */

public class ImageBackgroundHelper extends PdfPageEventHelper {

    private static final Logger LOGGER = LogManager.getLogger(ImageBackgroundHelper.class);

    private Image img;

    public ImageBackgroundHelper(Image img) {
        this.img = img;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            writer.getDirectContentUnder().addImage(img);
        } catch (DocumentException e) {
            LOGGER.error(e);
        }
    }
}
