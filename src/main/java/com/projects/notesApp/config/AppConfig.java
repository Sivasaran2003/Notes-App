package com.projects.notesApp.config;

import com.projects.notesApp.services.MyUserDetailsService;
import com.projects.notesApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {
    private final UserDetailsService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST ,"/user/login").permitAll()      // Allow login
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()            // Allow user registration
                        .requestMatchers("/user/**").authenticated()     // Protect other user endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public AppConfig(MyUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // This bean is required for Spring Security to manage authentication
        return config.getAuthenticationManager();
    }

    // Register AuthenticationManager to use your custom UserService
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
