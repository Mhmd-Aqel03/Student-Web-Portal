package com.example.Login.Security;

import com.example.Login.Auth.ApplicatioUserService;
import com.example.Login.JWT.JWTUsernameAndPasswordAuthenticationFilter;
import com.example.Login.JWT.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final ApplicatioUserService applicatioUserService;
    private final AuthenticationManager authenticationManager;

    @Lazy
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicatioUserService applicatioUserService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.applicatioUserService = applicatioUserService;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JWTUsernameAndPasswordAuthenticationFilter JWTauth = new JWTUsernameAndPasswordAuthenticationFilter(authenticationManager);
        JwtTokenVerifier JwtVerify = new JwtTokenVerifier();

        //Disable csrf
        http.csrf(c -> c.disable());

        //Todo: Find way to allow localhost:9000(add CORS configuration)
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        //Permesions
        http.authorizeRequests((authorizeRequests) ->authorizeRequests.requestMatchers("/login", "/signup").permitAll()
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/admin/**").hasRole("ADMIN")
        );
        //Logout
//        http.logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .permitAll()
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID", "remember-me")
//                .logoutSuccessUrl("/login"));
//        //Remember Me
//        http.rememberMe(rb -> rb.tokenValiditySeconds(14 * 24 * 60 * 60).rememberMeParameter("remember-me"));
//        //Login
//        http.formLogin(form -> form.loginPage("/login")
//                .successHandler(new CustomAuthenticationSuccessHandler())
//                .passwordParameter("password")
//                .usernameParameter("username"));

        //JWT Filter
        http.addFilterBefore(JwtVerify, UsernamePasswordAuthenticationFilter.class);;
        http.addFilterAfter(JWTauth, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected AuthenticationManager configure(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicatioUserService);

        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:9000")); // Allow your frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow specific HTTP methods
        configuration.setAllowCredentials(true); // Allow credentials
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all endpoints
        return source;
    }
}


