package br.com.pvv.senai.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
	
	@Bean
	 public OpenAPI customOpenAPI() {
	   return new OpenAPI()
	          .components(new Components()
	          .addSecuritySchemes("bearer-key",
	          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
	
//	 @Bean
//	 public OpenAPI customOpenAPI() {
//	     return new OpenAPI()
//	             .components(new Components().addSecuritySchemes("basicScheme",
//	                     new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//	             .info(new Info().title("SpringShop API").version("0.1")
//	                     .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//	             .externalDocs(new ExternalDocumentation()
//	                     .url("static/openapi.yaml"));
//	 }

}
