package test.POC.Metrics.Config;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class MetricsConfiguringClass extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        ConsoleReporter
            .forRegistry(metricRegistry)
            .build()
            .start(15, TimeUnit.MINUTES);
        
        
        final Graphite graphite = new Graphite(new InetSocketAddress("0.0.0.0", 2003));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                                                          .prefixedWith("MetricsPOC")
                                                          .convertRatesTo(TimeUnit.SECONDS)
                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                          .filter(MetricFilter.ALL)
                                                          .build(graphite);
        reporter.start(15, TimeUnit.MINUTES);
    }
}