package com.lite.backend.business;

import com.lite.backend.data.dto.SignUpDto;
import com.lite.backend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(SignUpDto dto){

    }
}
