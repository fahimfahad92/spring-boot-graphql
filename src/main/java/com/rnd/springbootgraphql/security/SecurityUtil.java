package com.rnd.springbootgraphql.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class SecurityUtil {
  // load it from aws parameter store or external source
  private static final String JWT_TOKEN =
      "jwtTokenSecretjwtTokenSecretjwtTokenSecretjwtTokenSecret";

  public static final String VALID_TILL_KEY = "validTillKey";
  public static final String SUBJECT_KEY = "sub";
  public static final String ROLE_KEY = "role";

  public static SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(JWT_TOKEN);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
