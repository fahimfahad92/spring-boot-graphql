package com.rnd.springbootgraphql.security;

import static com.rnd.springbootgraphql.security.SecurityUtil.ROLE_KEY;
import static com.rnd.springbootgraphql.security.SecurityUtil.VALID_TILL_KEY;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

public class JwtGenerator {

  private static final int THREE_HOURS_IN_MILL_SECONDS = 10800000;
  private static final String USER_ROLE = "USER";

  public static String generate(SecuredUser securedUser) {

    SecretKey signingKey = SecurityUtil.getSigningKey();

    return Jwts.builder()
        .header()
        .and()
        .claims()
        .add(VALID_TILL_KEY, System.currentTimeMillis() + THREE_HOURS_IN_MILL_SECONDS)
        .add(ROLE_KEY, USER_ROLE)
        .and()
        .subject(securedUser.username())
        .signWith(signingKey)
        .compact();
  }

  public static String refreshAccessToken(SecuredUser securedUser) {
    return generate(securedUser);
  }
}
