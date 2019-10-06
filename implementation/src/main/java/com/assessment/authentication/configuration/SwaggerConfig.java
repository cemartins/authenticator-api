package com.assessment.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.assessment.authentication.api"))
                    .paths(PathSelectors.any())
                    .build()
                .apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(true)
                .tags(new Tag("Authentication REST API", "REST API for authentication API"));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Authentication REST API")
                .description("Authentication Assessment")
                .contact(new Contact("Carlos", "www.martins.net", "cemartins@netcabo.pt"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}