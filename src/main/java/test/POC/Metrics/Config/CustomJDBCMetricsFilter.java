package test.POC.Metrics.Config;

import com.codahale.metrics.MetricRegistry;
import com.soulgalore.jdbcmetrics.JDBCMetrics;
import com.soulgalore.jdbcmetrics.QueryThreadLocal;
import com.soulgalore.jdbcmetrics.ReadAndWrites;
import com.soulgalore.jdbcmetrics.filter.JDBCMetricsFilter;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by piyush on 9/11/14.
 */
public class CustomJDBCMetricsFilter implements Filter{

    private static final String TYPE_READ = "read";
    private static final String TYPE_WRITE = "write";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // run once per thread
        if (QueryThreadLocal.getNrOfQueries() == null) {
            QueryThreadLocal.init();
        }

        try {
            chain.doFilter(request, response);
        } finally {
            // set the stats & cleanup
            ReadAndWrites rw = QueryThreadLocal.getNrOfQueries();
            updateStatistics(rw, (HttpServletRequest) request);
            QueryThreadLocal.removeNrOfQueries();
        }
    }

    @Override
    public void destroy() {

    }

    private void updateStatistics(ReadAndWrites rw,HttpServletRequest request) {
        if (request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)!=null) {
            String requestPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
            if(requestPattern.equals("/") || requestPattern.isEmpty()) requestPattern="root";
            if (JDBCMetrics.getInstance().getRegistry().getTimers().containsKey(createName(requestPattern, TYPE_WRITE, "write-time-per-request"))) {
                JDBCMetrics.getInstance().getRegistry().getTimers().get(createName(requestPattern, TYPE_READ, "read-time-per-request")).update(rw.getTotalReadTime(), TimeUnit.NANOSECONDS);
                JDBCMetrics.getInstance().getRegistry().getTimers().get(createName(requestPattern, TYPE_WRITE, "write-time-per-request")).update(rw.getTotalWriteTime(), TimeUnit.NANOSECONDS);
                JDBCMetrics.getInstance().getRegistry().getHistograms().get(createName(requestPattern, TYPE_READ, "read-counts-per-request")).update(rw.getReads());
                JDBCMetrics.getInstance().getRegistry().getHistograms().get(createName(requestPattern, TYPE_WRITE, "write-counts-per-request")).update(rw.getWrites());
            } else {
                JDBCMetrics.getInstance().getRegistry().timer(createName(requestPattern, TYPE_READ, "read-time-per-request"));
                JDBCMetrics.getInstance().getRegistry().timer(createName(requestPattern, TYPE_WRITE, "write-time-per-request"));
                JDBCMetrics.getInstance().getRegistry().histogram(createName(requestPattern, TYPE_READ, "read-counts-per-request"));
                JDBCMetrics.getInstance().getRegistry().histogram(createName(requestPattern, TYPE_WRITE, "write-counts-per-request"));
            }
        }
    }

    private static String createName(String group, String type, String name) {
        return MetricRegistry.name(group, type, name);
    }
}
