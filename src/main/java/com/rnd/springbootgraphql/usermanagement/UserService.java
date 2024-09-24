package com.rnd.springbootgraphql.usermanagement;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Map<Integer, User> userMap = new HashMap<>();
  private static final Map<Integer, Address> addressMap = new HashMap<>();
  private static final AtomicInteger userIdGenerator = new AtomicInteger();
  private static final AtomicInteger addressIdGenerator = new AtomicInteger();

  public Collection<User> users() {
    return userMap.values();
  }

  public User userByID(int id) {
    return userMap.getOrDefault(id, null);
  }

  public Address getUserAddress(int userId) {
    return addressMap.get(userId);
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

  public User updateAddress(AddressRequest addressRequest) throws Exception {
    User user = userMap.get(addressRequest.userId());
    if (Objects.isNull(user)) {
      throw new Exception("User not found");
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
}
