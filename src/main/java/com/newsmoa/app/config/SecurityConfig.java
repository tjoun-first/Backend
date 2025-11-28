package com.newsmoa.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
     // 🚨 추가: WebMvcConfigurer에서 설정한 CORS 설정을 적용하도록 활성화
        		.cors(Customizer.withDefaults())
                // CSRF 보호 비활성화 (API 서버이므로 세션 기반의 CSRF 보호는 불필요)
                .csrf(csrf -> csrf.disable())
                // 세션 관리 정책 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시 세션 생성
                )
             // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 문자열 패턴으로 변경
                        .requestMatchers("/api/login", "/api/signup").permitAll() // /api/login, /api/signup 은 인증 없이 접근 허용
                        .requestMatchers("/api/**").authenticated() // 나머지 /api/** 경로는 인증 필요
                        .anyRequest().permitAll() // 그 외 모든 요청은 일단 허용 (필요에 따라 변경)
                )
                // 기본 로그인 폼 비활성화
                .formLogin(formLogin -> formLogin.disable())
                // 기본 HTTP Basic 인증 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })
                );

        return http.build();
    }

    // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록 (로그인 처리에 사용)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}