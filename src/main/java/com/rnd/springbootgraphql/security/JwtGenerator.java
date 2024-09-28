package com.rnd.springbootgraphql.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtGenerator {

  private static final int THREE_HOURS_IN_MILL_SECONDS = 10800000;

  public static String generate(SecuredUser securedUser) {

    Claims claims = Jwts.claims().setSubject(securedUser.username());
    claims.put("validTill", System.currentTimeMillis() + THREE_HOURS_IN_MILL_SECONDS);
    claims.put("role", securedUser.role());

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_TOKEN)
        .compact();
  }
}
