package com.simpletripbe.moduleapi;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.simpletripbe")
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.batch.modulebatch", "com.simpletripbe.moduledomain", "com.simpletripbe.modulecommon", "com.simpletripbe.moduleapi"})
public class ModuleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}

}
