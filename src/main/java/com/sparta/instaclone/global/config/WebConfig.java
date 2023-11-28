package com.sparta.instaclone.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // OPTIONS 메서드 추가
                .allowedHeaders("*")  // 허용할 헤더 추가
                .exposedHeaders("Authorization", "Another-Header", "Yet-Another-Header", "Other-Custom-Header", "Content-Encoding", "Kuma-Revision")
                .allowCredentials(true);
    }


}
