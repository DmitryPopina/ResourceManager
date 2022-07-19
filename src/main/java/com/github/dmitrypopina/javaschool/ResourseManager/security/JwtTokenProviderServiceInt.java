package com.github.dmitrypopina.javaschool.ResourseManager.security;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.UserRole;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtTokenProviderServiceInt {
    String createToken(String username, List<UserRole> roles);
    Authentication getAuthentication(String token);
    String getUsername(String token);
    String resolveToken(HttpServletRequest req);
    boolean validateToken(String token);
}
