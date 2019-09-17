package com.igor.CouponSystemSpringProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication//(scanBasePackages="com.igor.CouponSystemSpringProj")
@EnableJpaRepositories//
public class CouponSystemSpringProjApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext context = SpringApplication.run(CouponSystemSpringProjApplication.class, args);
		SpringApplication.run(CouponSystemSpringProjApplication.class, args);
		System.out.println("Welcome to Coupon System!");
	}

}
