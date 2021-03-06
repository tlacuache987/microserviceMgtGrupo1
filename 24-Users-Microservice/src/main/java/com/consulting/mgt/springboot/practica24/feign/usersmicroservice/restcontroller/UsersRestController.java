package com.consulting.mgt.springboot.practica24.feign.usersmicroservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.consulting.mgt.springboot.practica24.feign.usersmicroservice.client.IAgeServiceClient;
import com.consulting.mgt.springboot.practica24.feign.usersmicroservice.client.IUppercaseServiceClient;
import com.consulting.mgt.springboot.practica24.feign.usersmicroservice.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UsersRestController {

	@Autowired
	private IAgeServiceClient ageServiceClient;

	@Autowired
	private IUppercaseServiceClient uppercaseServiceClient;
	
	@GetMapping("/{name}")
	public User users(@PathVariable String name) {

		log.info("Sending User");

		return new User(uppercaseServiceClient.toUppercase(name).get("uppercase").toString(), 
					    (int)ageServiceClient.age().get("age"));
	}
}
