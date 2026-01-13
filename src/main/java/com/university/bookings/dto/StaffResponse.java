package com.university.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffResponse {
    private Long id;
    private String name;
    private String email;
}
