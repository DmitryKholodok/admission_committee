package by.issoft.kholodok.config;

import by.issoft.kholodok.auth.RestAuthenticationEntryPoint;
import by.issoft.kholodok.auth.service.UserDetailsServiceImpl;
import by.issoft.kholodok.auth.filter.JwtAuthenticationFilter;
import by.issoft.kholodok.auth.filter.JwtAuthorizationFilter;
import by.issoft.kholodok.model.role.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http
                .addFilterBefore(filter,CsrfFilter.class);

        http
                .cors().and()
                .anonymous().and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint()).and()
                .authorizeRequests()
                    .antMatchers("/**", "/api/login").permitAll()
                    .antMatchers("/users").permitAll()
                    .antMatchers("/users/**", "/auth/**")
                        .hasAnyAuthority(
                                RoleEnum.ENROLLEE.getValue(),
                                RoleEnum.OPERATOR.getValue(),
                                RoleEnum.ADMIN.getValue())
                    .antMatchers("/enrollees/**")
                        .hasAnyAuthority(
                                RoleEnum.OPERATOR.getValue(),
                                RoleEnum.ADMIN.getValue())
                    .antMatchers("/api/operator/**")
                        .hasAnyAuthority(
                                RoleEnum.OPERATOR.getValue(),
                                RoleEnum.ADMIN.getValue())
                    .antMatchers("/api/admin/**")
                        .hasAnyAuthority(
                                RoleEnum.ADMIN.getValue())
                    .anyRequest().authenticated().and()
                .addFilter(authorizationFilter())
                .addFilterBefore(authenticationFilter(), authorizationFilter().getClass());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // It does not matter what url comes, so I decided to allow all to make requests.
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Access-Control-Expose-Headers"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AbstractAuthenticationProcessingFilter authenticationFilter() {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    public BasicAuthenticationFilter authorizationFilter() {
        return new JwtAuthorizationFilter(authenticationManager());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providerList = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        providerList.add(daoAuthenticationProvider);
        return new ProviderManager(providerList);
    }

}
