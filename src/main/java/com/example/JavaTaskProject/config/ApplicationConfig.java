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
    public AuthenticationProvider authenticationProvider() { // AuthenticationProvider always from spring framework package
        // AuthenticationProvider is data access provider responsible to fetch user detail and also encode password and ../

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // within this AuthenticationProvider we need to specify few properties:

        // we need to declare which user details we need to use in order fetch information about our user
        authProvider.setUserDetailsService(userDetailsService());
        // we need to declare which password encoder we use in our application so we need to specify that use correct algorithm
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }




    // this AuthenticationConfiguration hold already the information about the AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {   // do not forget to add this throw
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //
        return new BCryptPasswordEncoder(); // so we create our password encoder bean
    }

}