package com.newsmoa.app.config;

import com.newsmoa.app.security.EncodedCategoryMatcher;
import com.newsmoa.app.util.UrlEncodingUtil;
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

import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //뉴스 기사 리스트 요청을 허용하기 위한 카테고리 리스트
        Set<String> encodedCategories = UrlEncodingUtil.encodeAll(
                Set.of("경제", "과학", "사회", "세계", "문화")
        );
        
        http
                .cors(cors-> cors.disable())                       //cors 전부 차단(현재는 프론트와 백의 origin이 같으므로 cors가 의미없음)
                .csrf(csrf -> csrf.disable())                      //csrf 미사용(현재 스프링부트 서버는 CORS가 꺼져있고 SameSite 쿠키설정으로 보호받으므로 csrf가 필요없음)
                .sessionManagement(session -> session //세션 관리 정책 설정
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)         //필요 시에만 세션 생성(인증 등)
                )
                .authorizeHttpRequests(authorize -> authorize //http 요청 설정
                        .requestMatchers(                                                   //인증없이 접근 가능한 url
                                "/api/login",                                               //로그인
                                "/api/users",                                               //회원가입
                                "/api/users/exists",                                        //id 중복체크
                                "/api/article/recommand",                                   //추천 기사 목록(메인 페이지에 필요)
                                "/api/stat/**").permitAll()                                 //랭킹이나 통계자료
                        .requestMatchers(                                                   //인증없이 접근 가능한 url
                                new EncodedCategoryMatcher(encodedCategories)                 //카테고리별 기사 리스트(본문부터는 gemini 요청이 생기므로 인증 필요)
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated()                       //나머지 /api/** 경로는 인증 필요
                        .anyRequest().permitAll()                                           //그 외 모든 요청은 허용(현재 구조상 nginx가 차단하므로 실질적으로는 접근불가)
                )
                .formLogin(formLogin -> formLogin.disable()) //기본 로그인 폼 비활성화
//                .httpBasic(httpBasic -> httpBasic.disable())  //기본 HTTP Basic 인증 비활성화
                .logout(logout -> logout                        //로그아웃 설정
                        .logoutUrl("/api/logout")                                         //로그아웃 url 설정
                        .logoutSuccessHandler((request, response, authentication) -> { //로그아웃 성공시
                            response.setStatus(200);                                      //200 OK 반환
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