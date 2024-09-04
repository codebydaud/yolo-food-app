
package com.yolo.chef.userProfile;

import com.yolo.chef.address.Address;
import com.yolo.chef.address.AddressRepository;
import com.yolo.chef.dto.CreateUserProfileRequest;
import com.yolo.chef.exception.NotFoundException;
import com.yolo.chef.user.User;
import com.yolo.chef.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Map<String, String>> createUserProfile(String username, CreateUserProfileRequest userProfileRq) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                Address address = new Address();
                address.setHouse(userProfileRq.getHouse());
                address.setStreet(userProfileRq.getStreet());
                address.setArea(userProfileRq.getArea());
                address.setZip_code(userProfileRq.getZipCode());
                address.setCity(userProfileRq.getCity());
                address.setCountry(userProfileRq.getCountry());
                address.setCreatedAt(LocalDateTime.now());
                address.setUpdatedAt(LocalDateTime.now());
                addressRepository.save(address);

                UserProfile userProfile = new UserProfile();
                userProfile.setFirstName(userProfileRq.getFirstName());
                userProfile.setLastName(userProfileRq.getLastName());
                userProfile.setContactNumber(userProfileRq.getContactNumber());
                userProfile.setCreatedAt(LocalDateTime.now());
                userProfile.setUpdatedAt(LocalDateTime.now());
                userProfile.setAddressId(address.getId());
                userProfile.setUserId(user.getId());
                userProfile.setCurrencyId(1);
                userProfileRepository.save(userProfile);

                response.put("message", "User profile created successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("message", "An error occurred while creating the user profile");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
