package com.springBoot.blogApplication.services;

import com.springBoot.blogApplication.payload.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}
