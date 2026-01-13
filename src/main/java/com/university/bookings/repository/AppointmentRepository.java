package com.university.bookings.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university.bookings.Entities.Appointment;
import com.university.bookings.Entities.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // كل مواعيد مستخدم
    List<Appointment> findByCustomer(User customer);

    // 🔥 منع تداخل المواعيد (الأفضل)
    @Query("""
        SELECT COUNT(a) > 0
        FROM Appointment a
        WHERE a.startTime < :endTime
          AND a.endTime > :startTime
    """)
    boolean existsConflictingAppointment(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
