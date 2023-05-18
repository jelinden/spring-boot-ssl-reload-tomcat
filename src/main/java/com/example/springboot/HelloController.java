package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "<html><head></head><body><h1>Hello!</h1>Greetings from Spring Boot!</body><html>";
	}

}
