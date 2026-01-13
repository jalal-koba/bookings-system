package com.university.bookings.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.university.bookings.Entities.ServiceEntity;
import com.university.bookings.Entities.User;
import com.university.bookings.service.ServiceEntityService;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceEntityService serviceService;

    public ServiceController(ServiceEntityService serviceService) {
        this.serviceService = serviceService;
    }

    // 🔓 متاح للجميع
    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAll();
    }

    // 🔓 متاح للجميع
    @GetMapping("/{id}")
    public ServiceEntity getService(@PathVariable Long id) {
        return serviceService.getById(id);
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ServiceEntity> createService(
            @RequestBody ServiceEntity service) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceService.create(service));
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ServiceEntity updateService(
            @PathVariable Long id,
            @RequestBody ServiceEntity service) {

        return serviceService.update(id, service);
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {

        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{serviceId}/assign-staff/{staffId}")
    public ServiceEntity assignStaff(
            @PathVariable Long serviceId,
            @PathVariable Long staffId) {

        return serviceService.assignStaffToService(serviceId, staffId);
    }

    @GetMapping("/{serviceId}/staff")
    public List<User> getServiceStaff(@PathVariable Long serviceId) {
        return serviceService.getById(serviceId).getStaffList();
    }

}
