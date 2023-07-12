package com.apoorv.service;

import org.apoorv.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);

}
