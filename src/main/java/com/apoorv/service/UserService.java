package com.apoorv.service;

import org.apoorv.model.User;

public interface UserService {
    User createUser(String firstName, String lastName);
}
