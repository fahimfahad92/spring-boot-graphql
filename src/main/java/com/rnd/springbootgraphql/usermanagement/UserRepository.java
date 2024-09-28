package com.rnd.springbootgraphql.usermanagement;

import com.rnd.springbootgraphql.security.SecuredUser;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {

  private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

  Map<String, SecuredUser> userMap = new HashMap<>();

  public boolean createUser(UserDto userDto) {
    try {
      if (userMap.containsKey(userDto.name())) {
        throw new Exception("User already exists");
      }

      SecuredUser securedUser = new SecuredUser(userDto.name(), userDto.password(), "USER");
      userMap.put(userDto.name(), securedUser);

      return true;
    } catch (Exception e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  public SecuredUser validateUser(UserDto userDto) throws Exception {
    SecuredUser securedUser = userMap.getOrDefault(userDto.name(), null);
    if (Objects.isNull(securedUser)) {
      throw new Exception("Invalid credentials");
    }

    // should implement custom logic
    if (!securedUser.password().equals(userDto.password())) {
      throw new Exception("Invalid credentials");
    }

    return securedUser;
  }
}
