Query:
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
Query with parameter:
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
Basic mutation operation:
```
mutation {
  addUser(name: "Fahim") {
    id
    name
  }
}
```

Input type mutation:
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