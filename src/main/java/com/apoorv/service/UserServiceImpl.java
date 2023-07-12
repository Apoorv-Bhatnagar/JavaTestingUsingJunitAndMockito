package com.apoorv.service;

import com.apoorv.data.UserRepository;
import com.apoorv.data.UserRepositoryImpl;
import org.apoorv.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UserRepository userRepository,EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService=emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName) {
        if(firstName==null)
        {
            throw new IllegalArgumentException("firstname is empty");
        }
      User user=new User(firstName,lastName, UUID.randomUUID().toString());

      boolean isUserCreated=userRepository.save(user);
try {
    emailVerificationService.scheduleEmailConfirmation(user);
}
catch(RuntimeException e)
{
    throw new RuntimeException();
}

       return user;
    }

}
