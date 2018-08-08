package ejiopeg.example.zookeeper.server.core;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping(name = "HelloService", path = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}

}
