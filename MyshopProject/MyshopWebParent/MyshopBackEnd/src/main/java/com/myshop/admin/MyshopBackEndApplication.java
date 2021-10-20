package com.myshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.myshop.common.entity", "com.myshop.admin.user" })
public class MyshopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyshopBackEndApplication.class, args);
	}

}
