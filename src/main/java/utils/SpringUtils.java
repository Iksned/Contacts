package utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtils {
    private static WebApplicationContext context;

    private SpringUtils() {}

    public synchronized static Object getBean(String beanName) {
        if (context == null) {
            context = ContextLoader.getCurrentWebApplicationContext();
        }
        return context.getBean(beanName);
    }

}