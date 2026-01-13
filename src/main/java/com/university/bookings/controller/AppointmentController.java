package com.university.bookings.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.university.bookings.Entities.Appointment;
import com.university.bookings.dto.AppointmentResponse;
import com.university.bookings.dto.CreateAppointmentRequest;
import com.university.bookings.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // CUSTOMER يطلب حجز
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public AppointmentResponse create(
            @RequestBody CreateAppointmentRequest request,
            Authentication authentication) {

        Appointment appt = service.createAppointment(
                request,
                authentication.getName()
        );

        return toResponse(appt);
    }


    @GetMapping("/{id}")
    public AppointmentResponse getById(
            @PathVariable Long id,
            Authentication authentication) {

        Appointment appt = service.getById(id, authentication.getName());
        return toResponse(appt);
    }


    // ADMIN يوافق
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public AppointmentResponse approve(@PathVariable Long id) {
        return toResponse(service.approve(id));
    }



    // ADMIN يرفض
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public AppointmentResponse reject(@PathVariable Long id) {
        return toResponse(service.reject(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")

    // 🔹 Mapper بسيط داخل Controller
    private AppointmentResponse toResponse(Appointment appt) {
        return new AppointmentResponse(
                appt.getId(),
                appt.getStartTime(),
                appt.getEndTime(),
                appt.getStatus(),
                appt.getService().getId()
        );
    }
}
