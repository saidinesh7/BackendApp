package com.saloonapp.app.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = "bearer-key")})
public class SwaggerConfig {

   @Bean
   public OpenAPI swaggerApiConfig() {

        Server server = new Server();
    server.setUrl("https://merry-passion-production.up.railway.app");

       var info = new Info()
               .title("saloon API's")
               .description("saloon app apis")
               .version("1.0");

       var components = new Components()
               .addSecuritySchemes("bearer-key", new SecurityScheme()
                       .type(SecurityScheme.Type.HTTP)
                       .scheme("bearer")
                       .bearerFormat("JWT"));
       return new OpenAPI().components(components).info(info).servers(List.of(server));
   }
}