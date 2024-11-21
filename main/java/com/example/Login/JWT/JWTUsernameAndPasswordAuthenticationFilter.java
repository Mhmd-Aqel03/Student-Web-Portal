package com.example.Login.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

//import java.util.Date;
@Component
public class JWTUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public JWTUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(req.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
//            System.out.println(authenticationRequest.getUsername());
            return super.getAuthenticationManager().authenticate(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            String Key = "SomeSecureKey_SomeSecureKey_SomeSecureKey_SomeSecureKey";

            String token = Jwts.builder()
                    .subject(authResult.getName())
                    .claim("authorities", authResult.getAuthorities())
                    .issuedAt(new java.util.Date()).
                    expiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                    .signWith(Keys.hmacShaKeyFor(Key.getBytes()))
                    .compact();

            // Set the content type to JSON
            response.setContentType("application/json");

            // Create a JSON object with the token
            String jsonResponse = "{ \"token\": \""+"Bearer " + token + "\" }";

            // Write the JSON response to the response body
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong with the JWT token generation");
        }
    }


}

