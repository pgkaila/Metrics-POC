package test.POC.Metrics.registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;

@Configuration
public class MyMetricRegistry extends MetricRegistry {

    private static final MetricRegistry registry = new MyMetricRegistry();

    public MyMetricRegistry() {
    }

    @Bean
    public static MetricRegistry getRegistry() {
        return registry;
    }

}
