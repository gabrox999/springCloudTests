package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

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
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public SimplePreFilter simplePreFilter(){
		return new SimplePreFilter();
	}
}
