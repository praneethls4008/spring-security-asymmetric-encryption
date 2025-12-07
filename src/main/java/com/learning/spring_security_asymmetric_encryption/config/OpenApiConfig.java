package com.learning.spring_security_asymmetric_encryption.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring Security JWT asymmetric Encryption",
                        email = "contact@email.com",
                        url = "https://contact.com"
                ),
                description = "Open API documentation for  spring security JWT asymmetric",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "License V1",
                        url = "https://license.com"
                ),
                termsOfService = "https://termsservice.com"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8001",
                        description = "Local ENV"
                ),
                @Server(
                        url = "https://prod.url.com",
                        description = "PROD ENV"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
