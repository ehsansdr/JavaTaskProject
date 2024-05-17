package com.example.JavaTaskProject.config;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private final UserDetailsService userDetailsService;

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

        // we need the class that can manipulate this jwt token
        userEmail = jwtService.extractUsername(jwt); // we extract our username (email)


        //todo : so we should continue our validation process


        // in this if we check that:
        // user email is not null and user is not authenticated yet (if the Authentication is null means the user is not yet authenticated)
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // so we get UserDetails from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);


            // next step is to validate the token is still valid or not by this if
            if (jwtService.isTokenValid(jwt, userDetails) /*&& isTokenValid*/) {

                // so of this block execute the token is valid
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,  // we do not have credential
                        userDetails.getAuthorities()
                );
                authToken.setDetails(  // here I want to build the details out of our  request
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // final step is to update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /** after these if that check and handle the username and authentication have this statement */
        filterChain.doFilter(request, response); // we need always to pass hand to the next filters to be executed
    }


}
