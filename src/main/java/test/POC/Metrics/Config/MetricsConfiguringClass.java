package test.POC.Metrics.Config;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.skife.jdbi.v2.DBI;
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
public class MetricsConfiguringClass extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry registry) {
        
//    	WebAppConfig wac = null;
//        
//        final DBI dbi = new DBI(wac.dataSource());
//        dbi.setTimingCollector(new InstrumentedTimingCollector(metricRegistry));
    	
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
}