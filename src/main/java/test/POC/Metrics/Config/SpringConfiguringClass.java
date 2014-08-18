package test.POC.Metrics.Config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class SpringConfiguringClass extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        ConsoleReporter
            .forRegistry(metricRegistry)
            .build()
            .start(1, TimeUnit.MINUTES);
        
        
//        final Graphite graphite = new Graphite(new InetSocketAddress("graphite.example.com", 2003));
//        final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
//                                                          .prefixedWith("web1.example.com")
//                                                          .convertRatesTo(TimeUnit.SECONDS)
//                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
//                                                          .filter(MetricFilter.ALL)
//                                                          .build(graphite);
//        reporter.start(1, TimeUnit.MINUTES);
    }
}