package com.newsmoa.app.controller;

import com.newsmoa.app.dto.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequest user){
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("users")
    public ResponseEntity<Object> users(@RequestBody UserRequest user){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("users/exists")
    public ResponseEntity<Object> userExists(@RequestParam String id){
        return ResponseEntity.ok().build();
    }
}
