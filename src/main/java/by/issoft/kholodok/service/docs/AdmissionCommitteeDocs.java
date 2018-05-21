package by.issoft.kholodok.service.docs;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by dmitrykholodok on 5/13/18
 */

public interface AdmissionCommitteeDocs<T> {

    ByteArrayOutputStream csv(T key) throws IOException;
    ByteArrayOutputStream pdf(T key) throws IOException;
    ByteArrayOutputStream excel(T key) throws IOException;

}
