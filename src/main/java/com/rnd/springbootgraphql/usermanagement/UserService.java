package com.rnd.springbootgraphql.usermanagement;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static Map<Integer, User> userMap = new HashMap<>();
  private static Map<Integer, Address> addressMap = new HashMap<>();
  private static AtomicInteger userIdGenerator = new AtomicInteger();
  private static AtomicInteger addressIdGenerator = new AtomicInteger();

  public Collection<User> users() {
    return userMap.values();
  }

  public User userByID(int id) {
    return userMap.getOrDefault(id, null);
  }

  public Address getUserAddress(int userId) {
    return addressMap.get(userId);
  }

  public User addUser(String name) {
    User user = new User(userIdGenerator.addAndGet(1), name);
    userMap.put(user.getId(), user);
    return user;
  }

  public User addAddress(int userId, String road, String house, String flat) throws Exception {
    User user = userMap.get(userId);
    if (Objects.isNull(user)) {
      throw new Exception("User not found");
    }

    Address address = new Address(addressIdGenerator.addAndGet(1), road, house, flat);
    addressMap.put(userId, address);
    return user;
  }
}
