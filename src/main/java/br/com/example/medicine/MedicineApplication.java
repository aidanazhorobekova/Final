package br.com.example.medicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EntityScan(basePackages ="br.com.example.Medicine.model")
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class MedicineApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(MedicineApplication.class, args);
		
	}
	
	
}
