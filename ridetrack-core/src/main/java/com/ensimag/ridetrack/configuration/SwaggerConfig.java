package com.ensimag.ridetrack.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class SwaggerConfig {
	
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	
	@Value("${application.name}")
	private String applicationName;
	
	@Value("${build.version}")
	private String buildVersion;
	
	@Value("${build.timestamp}")
	private String buildTimestamp;
	
	@Bean
	public Docket api() {
		log.debug("Starting Swagger");
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.pathMapping("/")
				.genericModelSubstitutes(ResponseEntity.class)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false);
		
		docket = docket.select()
				.paths(PathSelectors.any())
				.build();
		log.debug("Started Swagger");
		return docket;
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
			applicationName,
			"This is RideTrack, application for tracking things.",
			buildVersion.concat(" ").concat(buildTimestamp),
			"",
			new Contact("RideTrack", "","jan.bayer@grenoble-inp.org"),
			"",
			"",
			Collections.emptyList());
	}
	
	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(regex(DEFAULT_INCLUDE_PATTERN))
				.build();
	}
	
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(
				new SecurityReference("apiKey", authorizationScopes));
	}
}
