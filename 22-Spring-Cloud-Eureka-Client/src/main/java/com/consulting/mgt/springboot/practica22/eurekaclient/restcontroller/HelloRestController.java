package com.consulting.mgt.springboot.practica22.eurekaclient.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consulting.mgt.springboot.practica22.eurekaclient.MyListener;
import com.consulting.mgt.springboot.practica22.eurekaclient.helper.DiscoveryClientHelper;
import com.consulting.mgt.springboot.practica22.eurekaclient.helper.EurekaClientHelper;

@RestController
public class HelloRestController {

	@Value("${spring.application.name}")
	private String name;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	private Environment env;

	@Autowired
	private DiscoveryClientHelper discoveryClientHelper;

	@Autowired
	private EurekaClientHelper eurekaClientHelper;

	@GetMapping("/hello")
	public Map<String, Object> hello() {

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("message", "Hello !");
		map.put("application name", name);
		map.put("application port", MyListener.APPLICATION_PORT);
		map.put("application port (not real)", env.getProperty("server.port"));
		map.put("application profile", activeProfile);
		map.put("URI from Discovery Client", discoveryClientHelper.getServiceURI("my-client-app"));
		map.put("Instances from Discovery Client", discoveryClientHelper.getInstances("my-client-app"));
		map.put("URI from Eureka Client", eurekaClientHelper.getServiceURI("my-client-app"));
		map.put("Instances from Eureka Client", eurekaClientHelper.getInstances("my-client-app"));

		return map;
	}
}
