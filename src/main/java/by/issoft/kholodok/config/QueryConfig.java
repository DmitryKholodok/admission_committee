package by.issoft.kholodok.config;

import by.issoft.kholodok.dao.query.RoleQueryProvider;
import by.issoft.kholodok.dao.query.UserAuthQueryProvider;
import by.issoft.kholodok.dao.query.UserQueryProvider;
import by.issoft.kholodok.dao.query.impl.HqlRoleQueryProvider;
import by.issoft.kholodok.dao.query.impl.HqlUserAuthQueryProvider;
import by.issoft.kholodok.dao.query.impl.HqlUserQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {

    @Bean
    public UserQueryProvider getUserRequestProvider() {
        return new HqlUserQueryProvider();
    }

    @Bean
    public RoleQueryProvider getRoleRequestProvider() {
        return new HqlRoleQueryProvider();
    }

    @Bean
    public UserAuthQueryProvider getUserAuthDataRequestProvider() {
        return new HqlUserAuthQueryProvider();
    }

}
