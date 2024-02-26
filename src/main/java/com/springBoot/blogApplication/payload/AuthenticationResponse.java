package com.springBoot.blogApplication.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
}
