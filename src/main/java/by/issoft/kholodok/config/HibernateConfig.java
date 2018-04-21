package by.issoft.kholodok.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("by.issoft.kholodok.config")
@PropertySource("classpath:db.properties")
public class HibernateConfig {

    private static final String HIBERNATE_CONFIG_FILENAME = "hibernate.properties";

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean hibernateSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        try {
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan(new String[] {"by.issoft.kholodok.model"});
            sessionFactory.setHibernateProperties(loadHibernateProperties());
        } catch (IOException e) {
            throw new RuntimeException("File " + HIBERNATE_CONFIG_FILENAME + " was not found in classpath!", e);
        }

        return sessionFactory;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("ds.driver-name"));
        dataSource.setUrl(env.getRequiredProperty("ds.url"));
        dataSource.setUsername(env.getRequiredProperty("ds.username"));
        dataSource.setPassword(env.getRequiredProperty("ds.password"));
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    private Properties loadHibernateProperties() throws IOException {
        Properties properties = new Properties();
        InputStream fis = getClass().getClassLoader().getResourceAsStream(HIBERNATE_CONFIG_FILENAME);
        properties.load(fis);
        return properties;
    }

}
