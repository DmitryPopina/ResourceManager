package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.UserRole;
import com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity.LoginRequest;
import com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity.SignUpRequest;
import com.github.dmitrypopina.javaschool.ResourseManager.response.springsecurity.LoginResponse;
import com.github.dmitrypopina.javaschool.ResourseManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/public/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest requestHeader, @RequestBody LoginRequest request) throws RuntimeException {

        LoginResponse loginResponse = userService.login(request.getUsername(), request.getPassword());
        if(loginResponse == null){
            throw new RuntimeException("Login failed. Possible cause : incorrect username/password");
        }else{
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/public/signUp")
    public ResponseEntity<User> signUp(HttpServletRequest requestHeader, @RequestBody SignUpRequest request, @AuthenticationPrincipal User creator) throws RuntimeException {

        User user;
        user = userService.signUp(request);
        if (creator != null || creator.getRole() != UserRole.ROLE_ADMIN) {
            user.setRole(UserRole.ROLE_USER);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/refreshToken")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refreshToken(HttpServletRequest req) {
        return userService.refreshToken(req.getRemoteUser());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody List<User> getAll() {
        return this.userService.findAll();
    }

    /*@PostMapping
    public User create(@RequestBody User user){
        if (userService.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException(String.format("User name %s already exists", user.getUsername()));
        } else {
            User newUser = new User();
            BeanUtils.copyProperties(user, newUser, "id");
            return userService.save(newUser);
        }
    }*/
}
