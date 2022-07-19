package com.github.dmitrypopina.javaschool.ResourseManager.config;

import com.github.dmitrypopina.javaschool.ResourseManager.security.JwtTokenFilterConfigurer;
import com.github.dmitrypopina.javaschool.ResourseManager.security.JwtTokenProviderService;
import com.github.dmitrypopina.javaschool.ResourseManager.security.JwtTokenProviderServiceInt;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@NoArgsConstructor
public class SecurityConfiguration {
    private JwtTokenProviderService jwtTokenProviderService;

    @Autowired
    public void setJwtTokenProviderService(JwtTokenProviderService jwtTokenProviderService) {
        this.jwtTokenProviderService = jwtTokenProviderService;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .httpBasic(withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/users/public/**/").permitAll()
                        .anyRequest().authenticated()
                );
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProviderService));

    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
