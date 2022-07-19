package com.github.dmitrypopina.javaschool.ResourseManager.response.springsecurity;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String email;
    private String accessToken;
}
