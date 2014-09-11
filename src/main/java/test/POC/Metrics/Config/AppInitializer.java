package test.POC.Metrics.Config;

import java.util.HashMap;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;


public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
//        ServletRegistration.Dynamic MetricsServlet = servletContext.addServlet("MetricsServlet", com.yammer.metrics.reporting.MetricsServlet.class);
//        MetricsServlet.setInitParameter("show-jvm-metrics", "false");
//        MetricsServlet.addMapping("/jdbcmetrics");
        
//        FilterRegistration.Dynamic metricsHttpFilter = servletContext.addFilter("metricsHttpFilter",
//        		com.codahale.metrics.servlet.InstrumentedFilter.class);
//        metricsHttpFilter.addMappingForUrlPatterns(null, true, "/*");

        FilterRegistration.Dynamic instrumentedFilter = servletContext.addFilter("instrumentedFilter",
                com.codahale.metrics.servlet.InstrumentedFilter.class);
        instrumentedFilter.addMappingForUrlPatterns(null, true, "/*");

//        FilterRegistration.Dynamic JDBCMetricsFilter = servletContext.addFilter("JDBCMetricsFilter",
//        		com.soulgalore.jdbcmetrics.filter.JDBCMetricsFilter.class);
//        JDBCMetricsFilter.setInitParameters(new HashMap<String, String>(){{
//            put("use-headers","true");
//            put("request-header-name","jdbcmetrics");
//        }});
//        JDBCMetricsFilter.addMappingForUrlPatterns(null, true, "/*");

        FilterRegistration.Dynamic CustomJDBCMetricsFilter = servletContext.addFilter("customJDBCMetricsFilter",
                test.POC.Metrics.Config.CustomJDBCMetricsFilter.class);
        CustomJDBCMetricsFilter.addMappingForUrlPatterns(null, true, "/*");

    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("test.POC.Metrics.Config");
        return context;
    }
}