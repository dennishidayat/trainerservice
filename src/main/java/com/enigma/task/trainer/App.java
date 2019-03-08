package com.enigma.task.trainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.enigma.task.trainer.App;
import com.enigma.task.trainer.config.DaoSpringConfig;

@EnableJpaRepositories({"com.enigma.task.trainer.repository"})
@EntityScan({"com.enigma.task.trainer.model"})
@Import({DaoSpringConfig.class})
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
