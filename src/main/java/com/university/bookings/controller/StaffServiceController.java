package com.university.bookings.controller;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.bookings.Entities.ServiceEntity;
import com.university.bookings.service.ServiceEntityService;

@RestController
@RequestMapping("/api/staff/services")
public class StaffServiceController {

    private final ServiceEntityService serviceEntityService;

    public StaffServiceController(ServiceEntityService serviceEntityService) {
        this.serviceEntityService = serviceEntityService;
    }

    // 🔒 STAFF فقط
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping
    public List<ServiceEntity> myServices(Authentication authentication) {
        return serviceEntityService.getStaffServices(authentication.getName());
    }
}
