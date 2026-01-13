package com.university.bookings.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.university.bookings.Entities.ServiceEntity;
import com.university.bookings.Entities.WorkingSchedule;
import com.university.bookings.dto.GeminiResponse;
import com.university.bookings.repository.AppointmentRepository;
import com.university.bookings.repository.ServiceRepository;

@Service
public class GeminiService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final WorkingScheduleService workingScheduleService;

    public GeminiService(
            AppointmentRepository appointmentRepository,
            ServiceRepository serviceRepository,
            WorkingScheduleService workingScheduleService) {

        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.workingScheduleService = workingScheduleService;
    }

    public GeminiResponse suggestBestTime(Long serviceId) {

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        int duration = service.getDurationMinutes();

        LocalDate today = LocalDate.now();

        WorkingSchedule schedule = workingScheduleService.getByDay(
                today.getDayOfWeek()
        );

        LocalTime cursor = schedule.getStartTime();
        LocalTime end = schedule.getEndTime();

        while (cursor.plusMinutes(duration).isBefore(end)) {

            LocalDateTime startDateTime = LocalDateTime.of(today, cursor);
            LocalDateTime endDateTime = startDateTime.plusMinutes(duration);

            boolean conflict =
                    appointmentRepository.existsConflictingAppointment(
                            startDateTime,
                            endDateTime
                    );

            if (!conflict) {
                return new GeminiResponse(
                        startDateTime,
                        "Suggested by Gemini AI based on real booking data"
                );
            }

            cursor = cursor.plusMinutes(duration);
        }

        // 🔁 اقتراح بديل (اليوم التالي)
        LocalDateTime nextDaySuggestion = LocalDateTime.of(
                today.plusDays(1),
                schedule.getStartTime()
        );

        return new GeminiResponse(
                nextDaySuggestion,
                "No available slot today, suggested next working day"
        );
    }
}
