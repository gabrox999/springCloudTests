package it.madlabs.anag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
/*
 * @RestController marks the class as a controller class,
 * and also ensures that return values from @RequestMapping
 * methods in this class will be automatically converted
 * appropriately and written directly to the HTTP response.
 */
@EnableEurekaClient
public class AnagApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagApplication.class, args);
	}

	@RequestMapping("/available")
	public String available(){
		return "it's work! " + new Date();
	}

	@RequestMapping("/checked-out")
	public String checkedOut(){
		return "OOOKKK! " + new Date();
	}
}
