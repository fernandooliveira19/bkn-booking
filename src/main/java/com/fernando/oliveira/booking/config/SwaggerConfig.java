package com.fernando.oliveira.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

	private static final String BASE_PACKAGE = "com.fernando.oliveira.booking";

	@Value(value = "${info.app.name}")
	private String projectName;

	@Value(value = "${info.app.description}")
	private String projectDescription;

	@Value(value = "${info.app.version}")
	private String projectVersion;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.build()
				.apiInfo(getApiInfo())
				.useDefaultResponseMessages(true)
				.consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
				.produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE));
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title(projectName)
				.description(projectDescription)
				.version(projectVersion)
				.build();
	}
}
