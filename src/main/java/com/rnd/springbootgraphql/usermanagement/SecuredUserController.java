package com.rnd.springbootgraphql.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SecuredUserController {

  private final UserService userService;

  @Autowired
  public SecuredUserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/registration")
  public ResponseEntity<Boolean> createUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(userService.createUser(userDto));
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) throws Exception {
    return ResponseEntity.ok(userService.loginUser(userDto));
  }
}
