package org.fub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "FUB Application Backed API",version = "1.0",description ="FUB Application's backend Api"))
@CrossOrigin(value = "*")
@EnableWebSecurity
public class FubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FubApplication.class, args);
	}

}
