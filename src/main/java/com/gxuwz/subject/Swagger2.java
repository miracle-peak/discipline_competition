package com.gxuwz.subject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * author: 蔡奇峰
 * date: 2020/4/4 18:09
 * @Version V1.0
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createAPiInfo())
                .select()
                // 暴露给swagger的包路径
                .apis(RequestHandlerSelectors.basePackage("com.gxuwz.subject.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    protected ApiInfo createAPiInfo() {
        return new ApiInfoBuilder().title("title--->")
                .description("subject-competition")
                .termsOfServiceUrl("https://www.cnblogs.com/chuangqi/")
                .contact("tale") // 开发者
                .version("1.0")
                .build();
    }

}
