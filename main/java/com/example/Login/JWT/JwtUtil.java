package com.example.Login.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;

public class JwtUtil {
    public String getUsername(String token){
        String Key = "SomeSecureKey_SomeSecureKey_SomeSecureKey_SomeSecureKey";

        Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(Key.getBytes())).build().parseSignedClaims(token);

        return claimsJws.getPayload().getSubject();
    }
}
