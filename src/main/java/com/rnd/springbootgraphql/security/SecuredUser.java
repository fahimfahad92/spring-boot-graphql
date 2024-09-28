package com.rnd.springbootgraphql.security;

public record SecuredUser(String username, String password, String role) {}
