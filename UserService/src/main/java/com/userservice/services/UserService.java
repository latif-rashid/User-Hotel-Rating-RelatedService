package com.userservice.services;

import com.userservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    // user operation

    // create User
    User saveUser(User user);

    // get all user
    List<User> getAllUser();

    // get single user by given userId
    User getUser(String userId);

    // get delete User by given userId
    User deleteUser(String userId);

    // TODO: Update and delete
}
