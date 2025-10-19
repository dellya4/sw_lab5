package com.example.demo;
import com.example.demo.entity.Operators;
import com.example.demo.repository.OperatorsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initOperators(OperatorsRepository operatorsRepository) {
		return args -> {
			if (operatorsRepository.count() == 0) {
				operatorsRepository.saveAll(List.of(
						new Operators(null, "Ivan", "Romashkin", "Sales"),
						new Operators(null, "Anna", "Kyztetcova", "Support"),
						new Operators(null, "Oleg", "Kyrlikov", "IT"),
						new Operators(null, "Ersyltan", "Serikov", "Marketing"),
						new Operators(null, "Akniet", "Tyrsinbaeva", "Design"),
						new Operators(null, "Konstantin", "Latuta", "IT")
				));
			}
		};
	}

}


