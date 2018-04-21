package by.issoft.kholodok.auth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {

    @Autowired
    private TokenProperty tokenProperty;

    @Autowired
    private TokenUtil tokenUtil;

    public TokenAuthenticationService() {
    }

    public void addAuthentication(HttpServletResponse res, Authentication authentication) {
        res.addHeader(tokenProperty.getHttpHeaderName(),
                tokenProperty.getPrefix() + " " + tokenUtil.createTokenForUser(authentication));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(tokenProperty.getHttpHeaderName());
        if (token != null && token.startsWith(tokenProperty.getPrefix())) {
            token = token.substring(tokenProperty.getPrefix().length() + 1); // 1 - the length of a single gap
            UserDetails userDetails = tokenUtil.parseUserFromToken(token);
            return userDetails != null ?
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) :
                    null;
        }
        return null;
    }

}
