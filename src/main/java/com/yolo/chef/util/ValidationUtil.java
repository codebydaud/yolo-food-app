package com.yolo.chef.util;

import com.yolo.chef.dto.LoginRequest;
import com.yolo.chef.exception.EmailAlreadyExistsException;
import com.yolo.chef.exception.UserInvalidException;
import com.yolo.chef.exception.UsernameAlreadyExistsException;
import com.yolo.chef.user.UserRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtil {


    private final UserRepository userRepository;
    public static final Logger log = LoggerFactory.getLogger(ValidationUtil.class);

    public static boolean isValidEmail(String identifier) {
        try {
            new InternetAddress(identifier).validate();
            return true;
        } catch (AddressException e) {
            log.warn("Invalid email address: {}", identifier);
        }

        return false;
    }

    public static void validateUserDetailsNotEmpty(LoginRequest user) {
        if (user == null) {
            throw new UserInvalidException(ApiMessages.USER_DETAILS_EMPTY_ERROR.getMessage(), "Empty request");
        }

        if (user.email() == null || user.email().isEmpty()) {
            throw new UserInvalidException(ApiMessages.USER_EMAIL_EMPTY_ERROR.getMessage(), "Email is empty");
        }

        if (user.username() == null || user.username().isEmpty()) {
            throw new UserInvalidException(ApiMessages.USERNAME_EMPTY_ERROR.getMessage(), "Username is empty");
        }
    }

    public static void validateUserDetails(LoginRequest user) {
        validateUserDetailsNotEmpty(user);

        if (!isValidEmail(user.email())) {
            throw new UserInvalidException(String.format(ApiMessages.USER_EMAIL_ADDRESS_INVALID_ERROR.getMessage(), user.email()), "Please give correct email");
        }

    }

    public void validateNewUser(LoginRequest user) {
        validateUserDetails(user);
        if (doesUsernameExist(user.username())) {
            throw new UsernameAlreadyExistsException(ApiMessages.USERNAME_ALREADY_EXISTS_ERROR.getMessage(),"User with username : " + user.username() + " already exists");
        }
        if (doesEmailExist(user.email())) {
            throw new EmailAlreadyExistsException(ApiMessages.USER_EMAIL_ALREADY_EXISTS_ERROR.getMessage(),"User with email : " + user.email() + " already exists");
        }

    }

    public boolean doesUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean doesEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }


}
