package by.issoft.kholodok.auth.token;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProperty tokenProperty;

    TokenUtil() {
    }

    UserDetails parseUserFromToken(String token) {
        String username;
        try {
            username = Jwts.parser()
                    .setSigningKey(tokenProperty.getSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException ex) {
            ex.printStackTrace();
            return null;
        }

        return userDetailsService.loadUserByUsername(username);
    }

    String createTokenForUser(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + tokenProperty.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, tokenProperty.getSecret())
                .compact();
    }

}
