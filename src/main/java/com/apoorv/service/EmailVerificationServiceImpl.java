package com.apoorv.service;

import org.apoorv.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService{
    @Override
    public void scheduleEmailConfirmation(User user) {
        System.out.println("confirmed");
    }
}
