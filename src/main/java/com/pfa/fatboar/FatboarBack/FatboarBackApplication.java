package com.pfa.fatboar.FatboarBack;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class FatboarBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FatboarBackApplication.class, args);
	}
	
	@Bean
	public Executor asyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(5);
	    executor.setMaxPoolSize(5);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("Asynchronous Process-");
	    executor.initialize();
	    return executor;
	}

}
