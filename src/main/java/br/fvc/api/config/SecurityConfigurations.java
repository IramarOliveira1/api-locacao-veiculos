package br.fvc.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    String[] staticResources = {
            "/public/**",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verify-code").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/send-mail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/payment-types/all").permitAll()
                        .requestMatchers(HttpMethod.POST, "/payment-types/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/payment-types/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vehicle/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vehicle/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vehicle/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vehicle/home").permitAll()
                        .requestMatchers(HttpMethod.POST, "/payment-types/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agency/all").permitAll()
                        .requestMatchers(HttpMethod.POST, "/agency/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/agency/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/insurance/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ssl-test").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}