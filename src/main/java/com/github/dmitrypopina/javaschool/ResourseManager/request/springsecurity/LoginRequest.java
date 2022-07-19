package com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
