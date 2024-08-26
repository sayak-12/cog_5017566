package com.bookstoreapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bookstoreapi.filter.JwtAuthenticationFilter;
import com.bookstoreapi.filter.JwtAuthorizationFilter;
import com.bookstoreapi.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UserServiceImpl userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(authConfig));
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/api/login/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilter(jwtAuthenticationFilter)
            .addFilter(new JwtAuthorizationFilter(authenticationManager(authConfig), jwtUtil))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
