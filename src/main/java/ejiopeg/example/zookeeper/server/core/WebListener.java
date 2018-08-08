package ejiopeg.example.zookeeper.server.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class WebListener implements ServletContextListener {
	
	@Value("${server.address}")
	private String serverAddress;
	
	@Value("${server.port}")
	private int serverPort;
	
	@Autowired
	public ServiceRegistry serviceRegistry;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext servletContext = sce.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo,HandlerMethod> infoMap = mapping.getHandlerMethods();
		for (RequestMappingInfo info : infoMap.keySet()) {
			String serviceName = info.getName();
			System.out.println("-----------------" + serviceName);
			if (serviceName != null) {
				serviceRegistry.register(serviceName, String.format("%s:%d", serverAddress,serverPort));
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
