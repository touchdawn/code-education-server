package com.itheima.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebGlobalConfig {

//    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowCredentials(true)
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Access-Control-Allow-Origin:*", "Access-Control-Allow-Headers:*",
                                "Access-Control-Request-Method:GET,POST,OPTIONS,DELETE","Access-Control-Allow-Methods:POST,GET,OPTIONS,DELETE");
            }
        };
    }
}
