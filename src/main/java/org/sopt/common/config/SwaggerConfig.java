package org.sopt.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SOPT 과제 API")
                        .description("38기 SOPT 과제를 위한 API")
                        .version("v1"))
                .servers(
                        List.of(
                                new Server().description("Server").url("/")
                        )
                );
    }
}
