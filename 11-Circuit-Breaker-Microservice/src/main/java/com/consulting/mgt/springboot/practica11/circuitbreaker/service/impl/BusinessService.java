package com.consulting.mgt.springboot.practica11.circuitbreaker.service.impl;

import java.net.URI;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.consulting.mgt.springboot.practica11.circuitbreaker.controller.model.StatusResponse;
import com.consulting.mgt.springboot.practica11.circuitbreaker.service.IBusinessService;
import com.consulting.mgt.springboot.practica11.circuitbreaker.service.exception.FailingServiceException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessService implements IBusinessService {

	// Define propiedades Rest Template y String failingServiceURL

	// Inyecta propiedades por constructor

	@SneakyThrows
	@Override
	public StatusResponse perform() {

		// Define URI
		URI uri = null;

		try {

			// Llama a la URI mediante REST Template
			return null;

		} catch (ResourceAccessException ex) {

			log.info("can'not connect to uri: {}, message: {}", uri.toString(), ex.getLocalizedMessage());
			throw new FailingServiceException(ex.getMessage());

		} catch (HttpServerErrorException ex) {

			log.info("uri: {} returns {} status code", uri.toString(), ex.getRawStatusCode());
			throw new FailingServiceException(ex.getMessage());

		}
	}
}