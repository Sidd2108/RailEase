package com.rail.railEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//SBA - combination of 3 annotations:
//EnableAutoConfig, @ConponentScan, @SpringbootConfiguration
@SpringBootApplication
public class RailEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailEaseApplication.class, args);
	}

}
