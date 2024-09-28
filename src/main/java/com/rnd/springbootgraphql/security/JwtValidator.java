package com.rnd.springbootgraphql.security;

import com.rnd.springbootgraphql.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

  private static final Logger logger = LoggerFactory.getLogger(JwtValidator.class);

  /**
   * This method should validate jwt token and get appropriate user from DB using the claims
   *
   * @return SecuredUser
   */
  public SecuredUser validate(String jwtToken) {

    try {
      Claims body =
          Jwts.parser()
              .setSigningKey(SecurityConstant.JWT_TOKEN)
              .parseClaimsJws(jwtToken)
              .getBody();

      if (isExpired((Long) body.get("validTill"))) {
        logger.error("Token expired");
        throw new InvalidTokenException("Token Expired");
      }

      return new SecuredUser(body.getSubject(), null, (String) body.get("role"));
    } catch (Exception e) {
      logger.error("Token validation failed {}", e.getMessage());
      throw new InvalidTokenException(e.getMessage());
    }
  }

  private boolean isExpired(Long validTill) {
    return System.currentTimeMillis() > validTill;
  }
}
