package com.lite.backend.business;

import com.lite.backend.config.JwtProvider;
import com.lite.backend.data.dto.LoginDto;
import com.lite.backend.data.dto.SignUpDto;
import com.lite.backend.data.dto.TokenDto;
import com.lite.backend.data.entity.Role;
import com.lite.backend.data.entity.User;
import com.lite.backend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public User signUp(SignUpDto dto) throws Exception{

        Optional<User> userQuery = userRepository.findByEmail(dto.email());

        if(userQuery.isPresent()){
            throw new RuntimeException("User already exists");
        }

        User newUser = userRepository.save(User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role())
                .build());

        return newUser;
    }

    public TokenDto signIn(LoginDto dto){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        Optional<User> userQuery = userRepository.findByEmail(dto.email());

        return new TokenDto(dto.email(), token, userQuery.get().getRole());
    }
}
