package by.issoft.kholodok.auth.parser;

import by.issoft.kholodok.auth.model.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserCredentialsParser {

    private static final Logger LOGGER = LogManager.getLogger(UserCredentialsParser.class);

    public UserCredentials parseUserCredentials(HttpServletRequest req) {
        UserCredentials credentials;
        try {
            credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserCredentials.class);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
            credentials = new UserCredentials("", "");
        }
        return credentials;
    }

}
