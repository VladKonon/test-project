package com.osdb.test.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        List<ResponseMessage> defaultServerErrorResponse = createDefaultServerErrorResponses();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(singletonList(securityContext()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(POST, defaultServerErrorResponse)
                .globalResponseMessage(GET, defaultServerErrorResponse)
                .globalResponseMessage(PUT, defaultServerErrorResponse)
                .globalResponseMessage(PATCH, defaultServerErrorResponse)
                .globalResponseMessage(DELETE, defaultServerErrorResponse)
                .globalResponseMessage(HEAD, defaultServerErrorResponse)
                .globalResponseMessage(OPTIONS, defaultServerErrorResponse)
                .globalResponseMessage(PUT, defaultServerErrorResponse);
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private List<ResponseMessage> createDefaultServerErrorResponses() {
        return newArrayList(
                composeErrorResponse(500, "Failure. Unexpected condition was encountered."),
                composeErrorResponse(502, "Failure. The server, while acting as a gateway or proxy, " +
                        "received an invalid response from an inbound server it accessed while attempting to fulfill " +
                        "the request."),
                composeErrorResponse(401, "Unauthorized. Invalid access token.")
        );
    }

    private ResponseMessage composeErrorResponse(int code, String description) {
        return new ResponseMessageBuilder()
                .code(code)
                .message(description)
                .responseModel(new ModelRef("string"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference("apiKey",
                authorizationScopes));
    }
}