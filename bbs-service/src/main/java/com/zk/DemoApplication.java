package com.zk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(basePackages = {"com.zk.mapper"})
@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

	@Autowired


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
