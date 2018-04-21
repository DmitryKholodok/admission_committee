package by.issoft.kholodok.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ApiController {

    private final static Logger LOGGER = LogManager.getLogger(ApiController.class);

    @Value(value = "classpath:api.json")
    private Resource apiJsonResource;

    @GetMapping(value = "/api")
    public void getApi(HttpServletResponse response) {
        try {
            LOGGER.log(Level.DEBUG, "Sending api to a client. . .");
            Files.copy(apiJsonResource.getFile().toPath(), response.getOutputStream());
            response.setContentType("application/json");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

}
