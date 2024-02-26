package com.springBoot.blogApplication.services;

import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.payload.AuthenticationResponse;
import com.springBoot.blogApplication.payload.LoginDTO;

public interface AuthService {
    AuthenticationResponse login(LoginDTO loginDTO);
    AuthenticationResponse register(User user);
}
