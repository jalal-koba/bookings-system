package com.university.bookings.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class GeminiRequest {

    private LocalTime workStart;
    private LocalTime workEnd;
    private List<LocalDateTime> bookedAppointments;
    private int serviceDurationMinutes;

    // getters & setters
    public LocalTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalTime workStart) {
        this.workStart = workStart;
    }

    public LocalTime getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(LocalTime workEnd) {
        this.workEnd = workEnd;
    }

    public List<LocalDateTime> getBookedAppointments() {
        return bookedAppointments;
    }

    public void setBookedAppointments(List<LocalDateTime> bookedAppointments) {
        this.bookedAppointments = bookedAppointments;
    }

    public int getServiceDurationMinutes() {
        return serviceDurationMinutes;
    }

    public void setServiceDurationMinutes(int serviceDurationMinutes) {
        this.serviceDurationMinutes = serviceDurationMinutes;
    }
}
