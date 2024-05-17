package com.example.JavaTaskProject.config;

import UserManging.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration              // because we need this class be configuration
@RequiredArgsConstructor    // make constructor fo final field
public class ApplicationConfig {

    private final UserRepository repository;

    @Bean  // Bean always should be public
    public UserDetailsService userDetailsService() {
        // using lambda is better and intellij do this
        return username -> repository.findByEmail(username)    // because this method declare in optional we need orElse()
                // if we do not ge any email in optional we make spring to do this : throwing exception
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));  // use lambda instead of making object and creating hole class
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}