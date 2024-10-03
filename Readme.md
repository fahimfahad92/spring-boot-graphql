# Introduction

This is a template project for GraphQL. I am using Java 21
and Spring Boot 3.4. To keep it simple I did not use any database.

In this project I demonstrated:
1. Basic Query type
2. Basic mutation type
3. Mutation with input type
4. Multiple schema file for specific domain
5. Exception handling
6. Integration tests
7. Secured the apis using JWT token

**How to test:**
1. Install Java 21 (if not installed before)
2. Open project in IDE and run the project. 
3. Create test user and login to get token
4. Go to http://localhost:8080/graphiql?path=/graphql and try below queries and mutations with authorization token in the header

**Create User API**

URL: http://localhost:8080/user/registration
Sample request:
```agsl
{
    "name": "Fahim",
    "password": "password"
}
```

**Login API**

URL: http://localhost:8080/user/login
Sample request:
```agsl
{
    "name": "Fahim",
    "password": "password"
}
```

Add authorization header:

```agsl
{
  "Authorization":"Bearer <<JWT_TOKEN>>"
}
```

**Query:**
```
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
```
**Query with parameter:**
```
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
```
**Basic mutation operation:**
```
mutation {
  addUser(name: "Fahim") {
    id
    name
  }
}
```

**Input type mutation:**
```
mutation {
  updateAddress(addressRequest: 
    {
      userId:1, 
      flat:"flat", 
      house:"house", 
      road:"road2"
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
```