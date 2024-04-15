package ru.otus.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/books").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/book/new").hasRole("ADMIN")
                        .requestMatchers("/book","/book/*").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/book/edit/*").hasRole("ADMIN")
                        .requestMatchers("/api/authors").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/genres").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/books").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/books/*").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/books/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
