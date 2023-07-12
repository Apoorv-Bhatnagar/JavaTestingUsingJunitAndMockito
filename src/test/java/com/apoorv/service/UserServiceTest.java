package com.apoorv.service;

import com.apoorv.data.UserRepository;
import com.apoorv.service.UserService;
import org.apoorv.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;

    @BeforeEach
    void init()
    {
        firstName="apoorv";
        lastName="bhatnagar";
    }
    @DisplayName("user object created")
    @Test
    void testCreateUser()
    {
        Mockito.when(userRepository.save(any(User.class))).thenReturn(false);

        User user= userService.createUser(firstName,lastName);
         assertNotNull(user,"the User cant be null");
        assertEquals(firstName,user.getFirstName(),"doesnt match");
        assertNotNull(user.getId(),"id is not set");
       verify(userRepository,times(1)).save(any(User.class));

    }
    @Test
    void testCreateUser_WhenFirstnameEmpty()
    {

        IllegalArgumentException throwsError = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName);
        }, "throws error");
        assertEquals("firstname is empty",throwsError.getMessage());

    }
    @Test
    void testCreateUser_whenSaveMethodThrowsException()
    {
            when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);
          assertThrows(IllegalArgumentException.class,()->{
              userService.createUser(firstName,lastName);
          },"should have thrown user exception");
    }
    @Test
    @DisplayName("EmailService")
    void testCreateUser_whenEmailNotificationExceptionThrown()
    {
        when(userRepository.save(any(User.class))).thenReturn(true);
        // the method is void thats why we are testing like this
    //    doThrow(RuntimeException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
//      assertThrows(RuntimeException.class,()->
//      {
//          userService.createUser(firstName,lastName);
//          },"should have thrown runtimeexception");
   //calling with real method
        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
          userService.createUser(firstName,lastName);
          verify(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    }

}
