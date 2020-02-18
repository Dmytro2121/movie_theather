package com.dev.cinema.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"com.dev.cinema.dao",
        "com.dev.cinema.service"})
public class AppConfig {
    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties properties = new Properties();
        properties.put("show_sql", environment.getProperty("show_sql"));
        properties.put("format_sql", environment.getProperty("format_sql"));
        properties.put("use_sql_comments", environment.getProperty("use_sql_comments"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl"
                + ".auto"));
        factoryBean.setHibernateProperties(properties);
        factoryBean.setPackagesToScan("com.dev.cinema.model");
        return factoryBean;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(environment.getProperty("db.driver"));
        source.setUrl(environment.getProperty("db.url"));
        source.setUsername(environment.getProperty("db.username"));
        source.setPassword(environment.getProperty("db.password"));
        return source;
    }
}
