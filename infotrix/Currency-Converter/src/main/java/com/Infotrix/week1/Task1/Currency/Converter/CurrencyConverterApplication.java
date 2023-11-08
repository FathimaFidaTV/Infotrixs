package com.Infotrix.week1.Task1.Currency.Converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = "com.Infotrix.week1.Task1.Currency.Converter")
public class CurrencyConverterApplication {
	
	
    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
        
        
    }
}
