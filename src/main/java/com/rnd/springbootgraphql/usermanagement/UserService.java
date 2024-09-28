package com.rnd.springbootgraphql.usermanagement;

import com.rnd.springbootgraphql.exception.AddressNotFound;
import com.rnd.springbootgraphql.exception.UserNotFound;
import com.rnd.springbootgraphql.security.JwtGenerator;
import com.rnd.springbootgraphql.security.SecuredUser;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Map<Integer, User> userMap = new HashMap<>();
  private static final Map<Integer, Address> addressMap = new HashMap<>();
  private static final AtomicInteger userIdGenerator = new AtomicInteger();
  private static final AtomicInteger addressIdGenerator = new AtomicInteger();
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Collection<User> users() {
    return userMap.values();
  }

  public User userByID(int id) {
    User user = userMap.getOrDefault(id, null);
    if (Objects.isNull(user)) {
      throw new UserNotFound("User not found");
    }

    return user;
  }

  public Address getUserAddress(int userId) {
    if (!userMap.containsKey(userId)) {
      throw new AddressNotFound("User not found");
    }
    Address address = addressMap.getOrDefault(userId, null);
    if (Objects.isNull(address)) {
      throw new AddressNotFound("User address is not set");
    }
    return address;
  }

  public Map<Integer, Address> getUserAddress(List<Integer> userIds) {
    Map<Integer, Address> userAddressMap = new HashMap<>();
    for (Integer userId : userIds) {
      userAddressMap.put(userId, addressMap.get(userId));
    }

    return userAddressMap;
  }

  public User addUser(String name) {
    User user = new User(userIdGenerator.addAndGet(1), name);
    userMap.put(user.getId(), user);
    return user;
  }

  public User updateAddress(AddressRequest addressRequest) {
    User user = userMap.get(addressRequest.userId());
    if (Objects.isNull(user)) {
      throw new UserNotFound("User not found");
    }

    Address address =
        new Address(
            addressIdGenerator.addAndGet(1),
            addressRequest.road(),
            addressRequest.house(),
            addressRequest.flat());
    addressMap.put(addressRequest.userId(), address);
    return user;
  }

  public boolean createUser(UserDto userDto) {
    return userRepository.createUser(userDto);
  }

  public String loginUser(UserDto userDto) throws Exception {
    SecuredUser securedUser = userRepository.validateUser(userDto);
    return JwtGenerator.generate(securedUser);
  }
}
