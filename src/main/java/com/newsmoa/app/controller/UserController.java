package com.newsmoa.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.dto.UserRequest;
import com.newsmoa.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    
    @Operation(summary = "로그인 API", description = "입력한 ID와 PW로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        try {
            // AuthenticationManager를 사용하여 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getId(), userRequest.getPassword())
            );

            // SecurityContext에 인증 정보 저장
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );

            return ResponseEntity.ok("로그인 성공");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
    @PostMapping("/users")
    public ResponseEntity<Object> users(@RequestBody UserRequest userRequest) {
        try {
            userService.signup(userRequest);  // 변수명 통일!
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("회원가입이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 오류가 발생했습니다.");
        }
    }

    @Operation(summary = "아이디 중복 체크", description = "입력한 로그인 아이디의 중복 여부를 확인합니다.")
    @GetMapping("users/exists")
    public boolean checkId(@RequestParam("id") String id) {
        return userService.isIdAvailable(id);
    }
}
