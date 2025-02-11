package com.rnd.springbootgraphql.security;

import static com.rnd.springbootgraphql.security.SecurityUtil.*;

import com.rnd.springbootgraphql.exception.InvalidTokenException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.util.Map;
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
      Jwt<?, ?> jwt =
          Jwts.parser().verifyWith(SecurityUtil.getSigningKey()).build().parse(jwtToken);

      Map<String, Object> map = (Map<String, Object>) jwt.getPayload();
      if (isExpired((Long) map.get(VALID_TILL_KEY))) {
        logger.error("Token expired");
        throw new InvalidTokenException("Token Expired");
      }
      return new SecuredUser((String) map.get(SUBJECT_KEY), null, (String) map.get(ROLE_KEY));
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new InvalidTokenException("Invalid token");
    }
  }

  private boolean isExpired(Long validTill) {
    return System.currentTimeMillis() > validTill;
  }
}
