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
  addAddress(addressInput: 
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
      road
      flat
      house
    }
  }
}
```