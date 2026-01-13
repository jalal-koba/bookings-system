package com.university.bookings.dto;

import java.time.LocalDateTime;

public class CreateAppointmentRequest {

    private Long userId;
    private Long serviceId;
    private LocalDateTime startTime;

    public Long getUserId() {
        return userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
