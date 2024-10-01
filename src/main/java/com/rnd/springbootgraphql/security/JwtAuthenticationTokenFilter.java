package com.rnd.springbootgraphql.security;

import com.rnd.springbootgraphql.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  private final JwtValidator validator;

  public JwtAuthenticationTokenFilter(JwtValidator validator) {
    this.validator = validator;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwtToken = authHeader.substring(7);
      SecuredUser securedUser = validator.validate(jwtToken);

      List<GrantedAuthority> grantedAuthorities =
          List.of(new SimpleGrantedAuthority(securedUser.role()));
      JwtUserDetails userDetails =
          new JwtUserDetails(securedUser.username(), securedUser.password(), grantedAuthorities);

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      if (e instanceof InvalidTokenException) {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"Unauthorized\"}");
      }
    }
  }
}
