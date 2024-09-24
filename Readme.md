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
----------------------------
mutation {
  addUser(name: "Fahad") {
    id
    name
  }
}
---------------------------
mutation {
  addAddress(userId:1, flat:"flat", house:"house", road:"road") {
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