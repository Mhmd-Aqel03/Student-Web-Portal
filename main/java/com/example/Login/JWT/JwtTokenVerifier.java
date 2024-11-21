package com.example.Login.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if(authToken == null || !authToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        try{
            //Get token Without "Bearer "
            String token = authToken.substring(7);
            String Key = "SomeSecureKey_SomeSecureKey_SomeSecureKey_SomeSecureKey";
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(Key.getBytes())).build().parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();

            String username = claims.getSubject();

            var authorities = (List<Map<String,String>>)  claims.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        }catch (JwtException e) {
            throw new IllegalStateException("Token is not valid", e);
        }
    }
}
