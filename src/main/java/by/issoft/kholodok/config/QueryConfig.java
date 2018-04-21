package by.issoft.kholodok.config;

import by.issoft.kholodok.dao.query.RoleQueryProvider;
import by.issoft.kholodok.dao.query.UserAuthDataQueryProvider;
import by.issoft.kholodok.dao.query.UserQueryProvider;
import by.issoft.kholodok.dao.query.impl.HqlRoleQueryProvider;
import by.issoft.kholodok.dao.query.impl.HqlUserAuthDataQueryProvider;
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
    public UserAuthDataQueryProvider getUserAuthDataRequestProvider() {
        return new HqlUserAuthDataQueryProvider();
    }

}
