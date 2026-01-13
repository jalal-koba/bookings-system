package com.university.bookings.controller;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.university.bookings.Entities.WorkingSchedule;
import com.university.bookings.service.WorkingScheduleService;

@RestController
@RequestMapping("/api/working-schedules")
public class WorkingScheduleController {

    private final WorkingScheduleService service;

    public WorkingScheduleController(WorkingScheduleService service) {
        this.service = service;
    }

    // 🔓 عرض للجميع
    @GetMapping
    public List<WorkingSchedule> getAll() {
        return service.getAll();
    }

    // 🔓 عرض حسب اليوم
    @GetMapping("/{day}")
    public WorkingSchedule getByDay(@PathVariable DayOfWeek day) {
        return service.getByDay(day);
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public WorkingSchedule create(@RequestBody WorkingSchedule schedule) {
        return service.create(schedule);
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public WorkingSchedule update(
            @PathVariable Long id,
            @RequestBody WorkingSchedule schedule) {
        return service.update(id, schedule);
    }

    // 🔒 ADMIN فقط
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
