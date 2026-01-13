package com.university.bookings.dto;

import java.time.LocalDateTime;

import com.university.bookings.Entities.AppointmentStatus;

public class AppointmentResponse {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private Long serviceId;

    // 🔹 Constructor
    public AppointmentResponse(Long id,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               AppointmentStatus status,
                               Long serviceId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.serviceId = serviceId;
    }

    // 🔹 Getters فقط (لا نحتاج Setters)
    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Long getServiceId() {
        return serviceId;
    }
}
