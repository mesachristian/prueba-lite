package com.lite.backend.controller;

import com.lite.backend.business.AuthService;
import com.lite.backend.data.dto.LoginDto;
import com.lite.backend.data.dto.SignUpDto;
import com.lite.backend.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate( @RequestBody LoginDto dto ) {
        return ResponseEntity.ok(authService.signIn(dto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto dto ){
        try{
            User newUser = this.authService.signUp(dto);
            return ResponseEntity.ok("Ok");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
