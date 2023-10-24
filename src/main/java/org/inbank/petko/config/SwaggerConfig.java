package org.inbank.petko.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure our Swagger API UI
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Configuration
public class SwaggerConfig {

    /**
     * Set Project's and Author's information on Swagger API UI
     * @return custom API headers
     */
    @Bean
    public OpenAPI decisionAPI() {
        return new OpenAPI()
                .info(new Info().title("Decision App REST API")
                        .description("Application for performing a decision on a Client's credit request")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Dmitry Petko, petkodmitry@gmail.com")
                        .url("mailto:petkodmitry@gmail.com")
                );
    }

}
