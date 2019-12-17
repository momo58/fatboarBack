package com.pfa.fatboar.FatboarBack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pfa.fatboar.FatboarBack.services.ServiceImpl.GameScheduledSingleton;

@Configuration
public class SingletonConfiguration {
	
	@Bean
    public GameScheduledSingleton gameScheduledSingleton() {
        return new GameScheduledSingleton();
    } 

}
