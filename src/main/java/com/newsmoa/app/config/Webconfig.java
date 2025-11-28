package com.newsmoa.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry; // 🚨 이 부분을 확인/수정하세요!
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//전역 설정 예시
//Spring Boot WebMvcConfigurer 설정 (임시 방편)
@Configuration
public class Webconfig implements WebMvcConfigurer {

 @Override
 public void addCorsMappings(CorsRegistry registry) {
     registry.addMapping("/api/**")
             // 기존 출처와 함께 'null'을 추가하여 로컬 파일 시스템 접근 허용
             .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500", "null") 
             .allowedMethods("GET", "POST", "OPTIONS")
             .allowCredentials(true);
 }
}