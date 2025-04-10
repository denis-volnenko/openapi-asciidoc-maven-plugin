package ru.volnenko.example.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.context.annotation.Configuration;
import ru.volnenko.example.example.controller.DateController;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager",
                description = "Менеджер управления проектами и задачами",
                version = "1.0.0",
                contact = @Contact(
                        name = "Денис Волненко",
                        email = "denis@volnenko.ru",
                        url = "https://www.volnenko.ru"
                )
        )
)
public class OpenApiConfig extends ResourceConfig {

    public OpenApiConfig() {
        register(WadlResource.class);
        register(DateController.class);
    }

}
