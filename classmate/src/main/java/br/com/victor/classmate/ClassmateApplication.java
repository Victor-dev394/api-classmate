package br.com.victor.classmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class ClassmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassmateApplication.class, args);
	}

}
