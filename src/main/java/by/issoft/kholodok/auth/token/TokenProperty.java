package by.issoft.kholodok.auth.token;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/auth/token.properties")
@Getter(value = AccessLevel.PACKAGE)
public class TokenProperty {

    @Value("${token.expiration.time}")
    private long expirationTime;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.prefix}")
    private String prefix;

    @Value("${http.header.name}")
    private String httpHeaderName;

    public TokenProperty() {
    }

}
