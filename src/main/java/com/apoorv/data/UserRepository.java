package com.apoorv.data;

import org.apoorv.model.User;

public interface UserRepository {
    boolean save(User user);
}
