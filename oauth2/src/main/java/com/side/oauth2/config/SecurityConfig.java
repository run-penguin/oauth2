package com.side.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(req -> req.disable())
            .authorizeHttpRequests(req -> req
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/images/**").permitAll()
                .anyRequest().authenticated()
            )
            // .formLogin(req -> req
            //     .loginPage("/login")
            //     .permitAll()
            // )
            .oauth2Login(req -> req
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(req -> req
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
            );

        return http.build();
    }
}
