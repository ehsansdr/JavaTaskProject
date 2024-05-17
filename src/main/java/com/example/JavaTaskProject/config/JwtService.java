package com.example.JavaTaskProject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

/** we need the class that can manipulate this jwt token */

@Service
public class JwtService {
    // you can see the token in this site
    // https://jwt.io/   (51:00)

    // secret key defination in stack over flow
    // https://stackoverflow.com/questions/31309759/what-is-secret-key-for-jwt-based-authentication-and-how-to-generate-it
    // encryption secret key
    // https://asecuritysite.com/encryption/plain
    private static final String SECRET_KEY = "a9d796fb824461f486fd5e0bba1050f010b9b06ca04578a1daeb00360b785554";

    public String extractUsername(String token) {
        return null;
    }

    private Claims extractAllClaims(String token) {
        /* what is sign in key ?                      (55:00)
        * is a secret that is used to digitally sign JWT
        * it is used to create signature part of JWT
        * which is used to verify that the sender of jwt is who claim to be
        * and ensure the message was not change along the way
        * so we want to ensure that the same person of the same client
        * that is sending this jwt key is the one that claims to be */

        return Jwts                                 // (53:06) or (55:23)
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
