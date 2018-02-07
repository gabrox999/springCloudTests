package com.example.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
/*
 * standup a registry that other applications can talk to
 *
 * When the registry starts up it will complain, with a stacktrace,
 * that there are no replica nodes for the registry to connect to.
 * In a production environment, you will want more than one instance
 * of the registry.
 *
 * see application.yml
 */
public class RegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}
}
