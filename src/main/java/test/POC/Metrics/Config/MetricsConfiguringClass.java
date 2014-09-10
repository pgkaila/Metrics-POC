package test.POC.Metrics.Config;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurer;
import com.soulgalore.jdbcmetrics.JDBCMetrics;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jdbi.InstrumentedTimingCollector;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class MetricsConfiguringClass extends MetricsConfigurerAdapter implements MetricsConfigurer{

    private static final Logger logger = LoggerFactory.getLogger(MetricsConfiguringClass.class);


//    JDBCMetrics jdbcMetrics = JDBCMetrics.getInstance();


    @Override
    public void configureReporters(MetricRegistry registry) {
        
//    	WebAppConfig wac = null;
//        
//        final DBI dbi = new DBI(wac.dataSource());
//        dbi.setTimingCollector(new InstrumentedTimingCollector(metricRegistry));

        logger.info("registry name : " + registry.getNames());

//        logger.info("jdbcregistry name : " + jdbcMetrics.getRegistry().getNames());

//        ConsoleReporter
//                .forRegistry(jdbcMetrics.getRegistry())
//                .build()
//                .start(10, TimeUnit.MINUTES);

    	ConsoleReporter
            .forRegistry(registry)
            .build()
            .start(30, TimeUnit.SECONDS);

        
        
        
        
//        final Graphite graphite = new Graphite(new InetSocketAddress("0.0.0.0", 2003));
//        final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
//                                                          .prefixedWith("MetricsPOC")
//                                                          .convertRatesTo(TimeUnit.SECONDS)
//                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
//                                                          .filter(MetricFilter.ALL)
//                                                          .build(graphite);
//        reporter.start(1, TimeUnit.MINUTES);
    }

    @Override
    public MetricRegistry getMetricRegistry(){
        return JDBCMetrics.getInstance().getRegistry();
    }


}