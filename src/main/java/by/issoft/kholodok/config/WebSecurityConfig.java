package by.issoft.kholodok.config;

import by.issoft.kholodok.auth.RestAuthenticationEntryPoint;
import by.issoft.kholodok.auth.service.UserDetailsServiceImpl;
import by.issoft.kholodok.auth.filter.JwtAuthenticationFilter;
import by.issoft.kholodok.auth.filter.JwtAuthorizationFilter;

import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.service.RightsValidator;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

        http
                .cors().and()
                .anonymous().and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint()).and()
                .authorizeRequests()
                    .antMatchers("/", "/login", "/signup").permitAll()
                    .antMatchers("/user/**").permitAll() // post request for registration
//                    .antMatchers("/user/**").hasAnyAuthority(
//                            RoleEnum.ENROLLEE.getValue(), RoleEnum.OPERATOR.getValue(), RoleEnum.ADMIN.getValue())
                    .anyRequest().authenticated().and()
                .addFilter(authorizationFilter())
                .addFilterBefore(authenticationFilter(), authorizationFilter().getClass());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // It does not matter what url comes, so I decided to allow all to make requests.
        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RightsValidator rightsValidator() {
        return new RightsValidator();
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
