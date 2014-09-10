package test.POC.Metrics.Controller;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.ryantenney.metrics.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@Timed(name = "home view Timer", absolute=true)
//	@Metered(name = "home view Meter", absolute=true)
//	@Counted(name = "homeCount",monotonic=true)
////	@Gauge(name = "home view Gauge", absolute = true)
//	@ExceptionMetered(name = "home view Exception Meter", absolute = true)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
//		Counter count = new Counter();
		//		com.yammer.metrics.core.Counter count = Metrics.newCounter(new MetricName(HomeController.class, "home count"));
//		count.inc();
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		for(int i=0;i<500000;i++);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate  + "    :    ");
		
		return "home";
	}
	
}
