package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filter(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/products").permitAll()
                        .requestMatchers("/products/edit/*").hasRole("ADMIN")
                        .requestMatchers("/products/new").hasRole("ADMIN")
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/personal/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                        .requestMatchers("/api/products/**").hasRole("ADMIN")
                        .requestMatchers("/api/brands").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/characteristics/**").permitAll()
                        .requestMatchers("/api/characteristics/**").hasRole("ADMIN")
                        .requestMatchers("/api/orders").permitAll()
                        .requestMatchers("/api/order-items").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("https://**").permitAll()
                        .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
