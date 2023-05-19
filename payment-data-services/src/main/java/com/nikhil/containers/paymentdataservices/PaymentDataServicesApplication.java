package com.nikhil.containers.paymentdataservices;

import lombok.Lombok;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})
@Log4j2
@ComponentScan(value = {
		"com.nikhil.containers.paymentdataservices.PaymentDataServicesApplication",
		"com.nikhil.containers.paymentdataservices.api.*",
		"com.nikhil.containers.paymentdataservices.config.*",
		"com.nikhil.containers.paymentdataservices.model.*",
		"com.nikhil.containers.paymentdataservices.service.*"
})
public class PaymentDataServicesApplication {

	static Logger log = LogManager.getLogger(PaymentDataServicesApplication.class);

	public static String userName = "Default Server";

	public static void main(String[] args) {
		log.trace("starting Payment Data Services Application");
		String unEnv = System.getenv("user_name");
		userName = unEnv == null ? userName : unEnv;
		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			userName = reader.readLine();
			System.out.println("On Startup input :: " + userName);

		} catch (Exception e) {
			//System.out.println("System Error: " + e.getMessage());

			log.error("System Error: " + e.getMessage());
			userName = "faulty system";

			log.error("Falling back to default server name: " + userName);
		}
		SpringApplication.run(PaymentDataServicesApplication.class, args);

		log.trace("terminating Payment Data Services Application");
	}

}
