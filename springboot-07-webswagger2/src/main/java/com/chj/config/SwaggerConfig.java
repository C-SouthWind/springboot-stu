package com.chj.config;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Set;

@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }

    //配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("chen")
                //是否开启swagger
                .enable(flag)
                .select()
                /*
                配置要扫描接口的方式
                basePackage 指定要扫描的包
                any() 扫描全部
                none() 不扫描
                withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象
                withMethodAnnotation() 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.chj.controller"))
                //paths() 扫描指定路径
                //.paths(PathSelectors.ant("/chj/**"))
                .build();
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact(
                "chen",
                "http://www.baidu.com",
                "15139530908");
        return new ApiInfo(
                "chj的swaggerApi文档",
                "chj",
                "v1.0",
                "http://www.baidu.com",
                 contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

}
