package com.newsmoa.app.controller;

import com.newsmoa.app.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Operation(summary = "로그인 API", description = "입력한 ID와 PW로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequest user){
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
    @PostMapping("users")
    public ResponseEntity<Object> users(@RequestBody UserRequest user){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "아이디 중복 체크", description = "입력한 로그인 아이디의 중복 여부를 확인합니다.")
    @GetMapping("users/exists")
    public ResponseEntity<Object> userExists(@RequestParam String id){
        return ResponseEntity.ok().build();
    }
}
