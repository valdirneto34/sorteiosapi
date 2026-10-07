package io.github.valdirneto34.sorteiosapi.infra.springdoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CDL - Sistema de Gestão de Campanhas e Sorteios",
                version = "v1",
                description = "API RESTful para gestão de afiliados, funcionários, controle de cotas e emissão de cupons com conformidade LGPD.",
                contact = @Contact(
                        name = "Valdir de S C Neto",
                        email = "valdirneto100c@gmail.com",
                        url = "https://github.com/valdirneto34"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}