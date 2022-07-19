package com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.UserRole;
import lombok.Data;

@Data
public class SignUpRequest
{
    private String username;
    private String email;
    private String password;
    private UserRole role;
}
