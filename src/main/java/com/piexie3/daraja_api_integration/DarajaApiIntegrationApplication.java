package com.piexie3.daraja_api_integration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Banking application",
				description = "Backend api tutorial for learning purposes",
				version = "V1.0",
				contact = @Contact(
						name = "Emmanuel Bett",
						email = "emmanuelbett@proton.me",
						url = "https://github.com/piexie3"
				),
				license = @License(
						name = "Banking app",
						url = "https://github.com/piexie3"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "W3school java tutorial",
				url = "https://github.com/piexie3"
		)
)
@EnableJpaAuditing
public class DarajaApiIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarajaApiIntegrationApplication.class, args);
	}

}
