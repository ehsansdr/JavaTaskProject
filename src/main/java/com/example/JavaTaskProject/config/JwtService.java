package com.example.JavaTaskProject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
        return extractClaim(token, Claims::getSubject); // subject is the email or username of the user
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // so first extract all claims
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // in this method we can declare the time of validation
    public String generateToken(
            Map<String, Object> extraClaims,  // this map will contain the claim and extra claim that we want to add
            UserDetails userDetails
    ) {
        // todo : declare the time of validation of token in this return part
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // the issue date (when token create I think)
                .setExpiration(new Date(System.currentTimeMillis() + (1000  * 60 * 60))) // 1s * 60(1 minute contains 60 sec) * 60(minutes)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // generate and create token
    }

    // we want to validate if this  token right here belongs to this UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        //we get username to compare and ,make sure about that detail
        final String username = extractUsername(token);

        // if the username that we get from of token the username is same as username we have as input
        // and we should to make sure the validation of token and that is not expired
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // we return boolean for mke sure the toke is not expired by using method that return expiration date
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // we want to make sure it is today's date
    }

    // this method return the expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
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
