package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableZuulProxy
/*
 * Spring Cloud Netflix includes an embedded Zuul proxy,
 * which we can enable with the @EnableZuulProxy annotation.
 * This will turn the Gateway application into a reverse proxy
 * that forwards relevant calls to other services
 *
 * To forward requests from the Gateway application,
 * we need to tell Zuul the routes that it should watch and
 * the services to which to forward requests to those routes.
 * We specify routes using application.yml
 */
@EnableDiscoveryClient
/*
 * stand up a client that both registers itself with the
 * registry and uses the Spring Cloud DiscoveryClient abstraction
 * to interrogate the registry for it’s own host and port.
 * The @EnableDiscoveryClient activates the Netflix Eureka DiscoveryClient implementation.
 */
@RestController
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public SimplePreFilter simplePreFilter(){
		return new SimplePreFilter();
	}

	@Bean
	public AddContextPreFilter addContextPreFilter(){
		return new AddContextPreFilter();
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

}
