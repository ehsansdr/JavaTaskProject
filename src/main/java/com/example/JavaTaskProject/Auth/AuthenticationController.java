package com.example.JavaTaskProject.Auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    /** this class will have to end point */

    /* now within this authentication controller we will create two end point
    * one for register
    * the other for register */

    private final AuthenticationService service;

    // we use this method for register e need register request
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        // in this part we expect get token
        // for decoding the token in this site
        // https://jwt.io/

        return ResponseEntity.ok(service.register(request));
    }

    // it will return authentication respond we need authentication request
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
