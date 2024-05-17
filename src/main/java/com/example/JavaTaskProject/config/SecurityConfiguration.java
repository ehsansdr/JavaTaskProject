package com.example.JavaTaskProject.config;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity      // because this class will be our security class , this 2 anno they need to be together
                        // when we work with spring boot 3.0
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /** we need to do tell spring which configuration
     * that we want to use in order to make all project works
     * so we want to bind becuase we create the filter but this filter is not yet used
     * this is going to be our security config class
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()      // how we can configure or session management (1:31:00)
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()      // here we need which authentication provider we need to use
                .authenticationProvider(authenticationProvider) // be careful you should create that
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);  /** I want now to use JWT filter that we just created */


        return http.build();
    }

    // white list : we have some end point that they do not require any authentication or any token that
    // which are open


}
