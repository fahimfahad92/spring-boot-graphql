package com.rnd.springbootgraphql.usermanagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @MutationMapping
  public User addUser(@Argument String name) {
    return userService.addUser(name);
  }

  //  @MutationMapping
  //  public User addAddress(
  //      @Argument int userId, @Argument String road, @Argument String house, @Argument String
  // flat)
  //      throws Exception {
  //    return userService.addAddress(userId, road, house, flat);
  //  }

  @MutationMapping
  public User addAddress(@Argument AddressRequest addressRequest) throws Exception {
    return userService.addAddress(
        addressRequest.userId(),
        addressRequest.road(),
        addressRequest.house(),
        addressRequest.flat());
  }

  @QueryMapping
  public Collection<User> users() {
    logger.info("Getting all user");
    return userService.users();
  }

  @QueryMapping
  public User userByID(@Argument int id) {
    logger.info("Getting user {}", id);
    return userService.userByID(id);
  }

  @QueryMapping
  public Address userAddress(@Argument int userId) {
    logger.info("Getting user address {}", userId);
    return userService.getUserAddress(userId);
  }

  @BatchMapping
  Map<User, Address> address(Collection<User> users) {
    logger.info("Getting data for {}", users.size());
    Map<User, Address> userAddressMap = new HashMap<>();
    for (User user : users) {
      Address address = userService.getUserAddress(user.getId());
      userAddressMap.put(user, address);
    }
    return userAddressMap;
  }
}
