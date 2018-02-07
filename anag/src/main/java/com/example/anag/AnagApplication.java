package com.example.anag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
/*
 * @RestController marks the class as a controller class,
 * and also ensures that return values from @RequestMapping
 * methods in this class will be automatically converted
 * appropriately and written directly to the HTTP response.
 */
public class AnagApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagApplication.class, args);
	}

	@RequestMapping("/available")
	public String available(){
		return "it's work!";
	}

	@RequestMapping("/checked-out")
	public String checkedOut(){
		return "OOOKKK!";
	}
}
