package com.example.JavaTaskProject.config;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** this is the class that has duty to be JWT filter between client and server
 * and we want this filter be active every time we get request
 * so we need to extend OncePerRequestFilter class , and like its name
 * its filter by one request */

@Component
@RequiredArgsConstructor // it will create constructor using any final field that we declare in our class
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,    // we can intercept every request and extract data from this
            @NonNull HttpServletResponse response,  // like : we can add header to our response
            @NonNull FilterChain filterChain        // contains lists of other filters that we need to excute
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // Bearer token should always shoud start with key word "Bearer "
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            // if we do not have these two condition :
            filterChain.doFilter(request, response);
            return;
        }

        // try to extract token from this authentication header
        jwt = authHeader.substring(7);  // if we count "Bearer " the count is 7
        // todo : jwtService.extractUsername(jwt); // we need the class that can manipulate this jwt token
        userEmail = jwtService.extractUsername(jwt);



    }


}
