package test.POC.Metrics.Config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import test.POC.Metrics.Controller.HomeController;
import test.POC.Metrics.Domain.User;

@Configuration //Specifies the class as configuration
@ComponentScan(basePackages = { "test.POC.Metrics" }) //Specifies which package to scan
//@Import({DataBaseConfig.class})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableWebMvc //Enables to use Spring's annotations in the code
public class WebAppConfig extends WebMvcConfigurerAdapter{
    private static final String PROPERTY_NAME_DATABASE_DRIVER                   = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD                 = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL                      = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME                 = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT                 = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL                = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN    = "entitymanager.packages.to.scan";
    @Resource
    private Environment env;

    private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
    
    @Bean
    public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
            dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
            dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
            dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
            return dataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());     
        sessionFactoryBean.setAnnotatedClasses(new Class[]{User.class});
        sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
//        sessionFactoryBean.setPackagesToScan(new String[] { "test.POC.Metrics" });
        sessionFactoryBean.setHibernateProperties(hibProperties()); 
        logger.info("Hibernate Session Factory created...");
        return sessionFactoryBean;
    } 
    
    private Properties hibProperties() {
            Properties properties = new Properties();
            properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
            properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
            return properties;        
    }
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    @Bean
    public UrlBasedViewResolver setupViewResolver() {       
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
}