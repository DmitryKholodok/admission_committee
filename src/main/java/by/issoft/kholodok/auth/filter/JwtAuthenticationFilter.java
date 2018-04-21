package by.issoft.kholodok.auth.filter;

import by.issoft.kholodok.auth.model.UserCredentials;
import by.issoft.kholodok.auth.parser.UserCredentialsParser;
import by.issoft.kholodok.auth.token.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URL = "/login";

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private UserCredentialsParser userCredentialsParser;

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(LOGIN_URL);
        this.authenticationManager = authenticationManager;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        UserCredentials credentials = userCredentialsParser.parseUserCredentials(req);
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                credentials.getUsername(),
                                credentials.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) {

        tokenAuthenticationService.addAuthentication(res, auth);
    }

}