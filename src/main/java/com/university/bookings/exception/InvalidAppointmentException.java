package com.university.bookings.exception;

public class InvalidAppointmentException extends RuntimeException {

    public InvalidAppointmentException(String message) {
        super(message);
    }
}
