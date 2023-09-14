package com.play.quiz;

import javax.sql.DataSource;

import com.play.quiz.domain.Account;
import jakarta.validation.constraints.NotNull;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.play.quiz.repository")
@EnableTransactionManagement
@Profile("test")
public class TestAppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Value("${h2.jdbc.username}")
    private String h2username;

    @Value("${h2.jdbc.password}")
    private String h2password;

    @Value("${h2.jdbc.driverClassName}")
    private String h2driverClassName;

    @Value("${h2.jdbc.url}")
    private String h2url;

    @Bean
    @Primary
    public CsrfTokenRequestAttributeHandler xorCsrfTokenRequestAttributeHandler() {
        return new XorCsrfTokenRequestAttributeHandler();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(h2url);
        dataSource.setUsername(h2username);
        dataSource.setPassword(h2password);
        dataSource.setDriverClassName(h2driverClassName);

        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(Account.class.getPackage().getName());
        factoryBean.setPersistenceProvider(hibernatePersistenceProvider);

        return factoryBean;
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        System.getProperties().setProperty("env", "test");
    }
}
