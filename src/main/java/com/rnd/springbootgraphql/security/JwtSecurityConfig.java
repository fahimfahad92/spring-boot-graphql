package com.rnd.springbootgraphql.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig {

  private final JwtValidator validator;

  @Autowired
  public JwtSecurityConfig(JwtValidator validator) {
    this.validator = validator;
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter(validator);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers("/user/registration")
                    .permitAll()
                    .requestMatchers("/user/login")
                    .permitAll()
                    .requestMatchers("/graphiql")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(
            exception ->
                exception
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

    http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
