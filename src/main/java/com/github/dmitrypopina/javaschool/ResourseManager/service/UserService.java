package com.github.dmitrypopina.javaschool.ResourseManager.service;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.exception.CustomException;
import com.github.dmitrypopina.javaschool.ResourseManager.repo.UserRepo;
import com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity.SignUpRequest;
import com.github.dmitrypopina.javaschool.ResourseManager.response.springsecurity.LoginResponse;
import com.github.dmitrypopina.javaschool.ResourseManager.response.springsecurity.UserResponse;
import com.github.dmitrypopina.javaschool.ResourseManager.security.JwtTokenProviderServiceInt;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtTokenProviderServiceInt jwtTokenProviderService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtTokenProviderService(JwtTokenProviderServiceInt jwtTokenProviderService) {
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    public LoginResponse login(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            User user = userRepo.findByUsername(userName);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setEmail(user.getEmail());
            loginResponse.setUsername(user.getUsername());
            loginResponse.setAccessToken(jwtTokenProviderService.createToken(userName, user.getRoles()));

            return loginResponse;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User signUp(SignUpRequest request) {
        if(userRepo.existsByUsername(request.getUsername())){
            throw new CustomException("User already exists in system", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        request.setPassword(user.getPassword());

        userRepo.save(user);
        return user;
    }


    public List<User> findAll(){
        return userRepo.findAll();
    }

    public UserResponse searchUser(String userName) {
        User user = userRepo.findByUsername(userName);
        if (user == null) {
            throw new CustomException("Provided user doesn't exist", HttpStatus.NOT_FOUND);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());

        return userResponse;
    }

    public String refreshToken(String userName) {
        return jwtTokenProviderService.createToken(userName, userRepo.findByUsername(userName).getRoles());
    }

    public User findByUsername(String userName){
        return userRepo.findByUsername(userName);
    }

    public User findUserById(Long id){
        return  userRepo.findById(id).orElseThrow();
    }

    public User save(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
