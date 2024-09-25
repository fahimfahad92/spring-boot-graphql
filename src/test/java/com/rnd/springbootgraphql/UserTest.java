package com.rnd.springbootgraphql;

import static org.junit.jupiter.api.Assertions.*;

import com.rnd.springbootgraphql.usermanagement.Address;
import com.rnd.springbootgraphql.usermanagement.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

  WebTestClient client;
  HttpGraphQlTester tester;

  @BeforeAll
  void setUp(WebApplicationContext context) {
    client =
        MockMvcWebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("/graphql")
            .build();

    tester = HttpGraphQlTester.create(client);
  }

  @Test
  @Order(1)
  void shouldCreateUser() {
    var document =
        """
            mutation {
              addUser(name: "Fahim") {
                id
                name
              }
            }
        """;

    var user = tester.document(document).execute().path("addUser").entity(User.class).get();

    assertEquals(user.getName(), "Fahim");
    assertTrue(user.getId() > 0);
  }

  @Test
  @Order(2)
  void shouldReturnErrorForQueringAddressBeforeSettingAddress() {
    var document =
        """
              query {
                userAddress(userId:1) {
                  id,
                  road
                  flat
                  house
                }
              }
            """;

    tester
        .document(document)
        .execute()
        .errors()
        .satisfy(
            errors -> {
              assertFalse(errors.isEmpty());
              assertEquals("User address is not set", errors.get(0).getMessage());
            });
  }

  @Test
  @Order(3)
  void shouldSetAddressForUser() {
    var document =
        """
            mutation {
               updateAddress(addressRequest:
                 {
                   userId:1,
                   flat:"flat",
                   house:"house",
                   road:"road"
                 }
               ) {
                 id
                 name
                 address {
                   id,
                   road
                   flat
                   house
                 }
               }
             }
        """;

    var user = tester.document(document).execute().path("updateAddress").entity(User.class).get();

    assertNotNull(user);
    assertNotNull(user.getAddress());
    assertEquals(user.getAddress().house(), "house");
    assertTrue(user.getAddress().id() > 0);
  }

  @Test
  @Order(4)
  void shouldReturnUserForValidId() {
    var document =
        """
          query {
            userByID(id:1) {
              id
              name
              address {
                road
                flat
                house
              }
            }
          }
        """;

    var user = tester.document(document).execute().path("userByID").entity(User.class).get();

    assertNotNull(user);
    assertNotNull(user.getAddress());
    assertEquals(user.getName(), "Fahim");
    assertTrue(user.getId() > 0);
  }

  @Test
  @Order(5)
  void shouldReturnErrorForInvalidUserId() {
    var document =
        """
              query {
                userByID(id:100) {
                  id
                  name
                  address {
                    road
                    flat
                    house
                  }
                }
              }
            """;

    tester
        .document(document)
        .execute()
        .errors()
        .satisfy(
            errors -> {
              assertFalse(errors.isEmpty());
              assertEquals("User not found", errors.get(0).getMessage());
            });
  }

  @Test
  @Order(6)
  void shouldReturnListOfUsers() {
    var document =
        """
              query {
                users {
                  id
                  name
                  address {
                    road
                    flat
                    house
                  }
                }
              }
          """;

    var users = tester.document(document).execute().path("users").entityList(User.class).get();

    assertNotNull(users);
    assertFalse(users.isEmpty());
  }

  @Test
  @Order(7)
  void shouldReturnUserAddress() {
    var document =
        """
          query {
            userAddress(userId:1) {
              id,
              road
              flat
              house
            }
          }
        """;

    var address =
        tester.document(document).execute().path("userAddress").entity(Address.class).get();

    assertNotNull(address);
    assertTrue(address.id() > 0);
    assertEquals(address.house(), "house");
  }

  @Test
  @Order(8)
  void shouldReturnErrorForGettingAddressWithInvalidUserId() {
    var document =
        """
          query {
            userAddress(userId:2) {
              id,
              road
              flat
              house
            }
          }
        """;

    tester
        .document(document)
        .execute()
        .errors()
        .satisfy(
            errors -> {
              assertFalse(errors.isEmpty());
              assertEquals("User not found", errors.get(0).getMessage());
            });
  }
}
