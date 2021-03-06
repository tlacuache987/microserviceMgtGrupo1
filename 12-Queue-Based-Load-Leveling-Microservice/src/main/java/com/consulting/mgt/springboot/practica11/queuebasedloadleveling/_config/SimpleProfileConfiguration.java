package com.consulting.mgt.springboot.practica11.queuebasedloadleveling._config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.consulting.mgt.springboot.practica11.queuebasedloadleveling._config.profile.SimpleProfile;
import com.consulting.mgt.springboot.practica11.queuebasedloadleveling.service.ITaskService;
import com.consulting.mgt.springboot.practica11.queuebasedloadleveling.service.impl.TaskService;
import com.consulting.mgt.springboot.practica11.queuebasedloadleveling.task.consumer.impl.SimpleTaskConsumer;
import com.consulting.mgt.springboot.practica11.queuebasedloadleveling.task.producer.impl.SimpleTaskProducer;

@Configuration
@SimpleProfile
public class SimpleProfileConfiguration {

	// Implementa
	@Autowired
	private ObjectFactory<SimpleTaskProducer> simpleTaskProducerObjectGactory;
	
	@Autowired
	@Qualifier("myExecutorService")
	private ExecutorService myExecutorService;
	
	@Bean
	@Scope("prototype")
	public SimpleTaskProducer simpleTaskProducer() {
		return new SimpleTaskProducer(1, 
				simpleTaskConsumer(), 
				ApplicationConfig.PRODUCER_TASK_DELAY);
	}

	@Bean
	public SimpleTaskConsumer simpleTaskConsumer() {
		return new SimpleTaskConsumer(
				ApplicationConfig.CONSUMER_TASK_DELAY);
	}
	
	@Bean
	public ITaskService taskService() {
		return new TaskService(myExecutorService, simpleTaskProducerObjectGactory);
	}

}
