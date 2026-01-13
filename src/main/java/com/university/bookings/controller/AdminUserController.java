package com.university.bookings.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.bookings.Entities.User;
import com.university.bookings.dto.CreateStaffRequest;
import com.university.bookings.dto.StaffResponse;
import com.university.bookings.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/staff/create")
    public StaffResponse createStaff(
            @RequestBody CreateStaffRequest request) {
    
        User staff = userService.createStaff(request);
    
        return new StaffResponse(
                staff.getId(),
                staff.getName(),
                staff.getEmail()
        );
    }
    
}
