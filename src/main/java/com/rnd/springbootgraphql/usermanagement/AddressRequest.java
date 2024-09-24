package com.rnd.springbootgraphql.usermanagement;

public record AddressRequest(int userId, String road, String house, String flat) {}
