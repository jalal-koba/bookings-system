   
package com.university.bookings.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.university.bookings.Entities.Role;
import com.university.bookings.Entities.User;
import com.university.bookings.dto.CreateStaffRequest;
import com.university.bookings.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createStaff(CreateStaffRequest request) {

        User staff = new User();
        staff.setName(request.getName());
        staff.setEmail(request.getEmail());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setRole(Role.STAFF);

        return userRepository.save(staff);
    }
}
