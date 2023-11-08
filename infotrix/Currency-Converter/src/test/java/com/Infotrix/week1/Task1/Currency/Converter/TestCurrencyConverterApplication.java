package com.Infotrix.week1.Task1.Currency.Converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCurrencyConverterApplication {

	public static void main(String[] args) {
		SpringApplication.from(CurrencyConverterApplication::main).with(TestCurrencyConverterApplication.class).run(args);
	}

}
