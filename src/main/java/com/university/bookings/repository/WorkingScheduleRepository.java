package com.university.bookings.repository;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.bookings.Entities.WorkingSchedule;

public interface WorkingScheduleRepository
        extends JpaRepository<WorkingSchedule, Long> {

    Optional<WorkingSchedule> findByDay(DayOfWeek day);
}