package com.university.bookings.service;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import com.university.bookings.Entities.WorkingSchedule;
import com.university.bookings.exception.ResourceNotFoundException;
import com.university.bookings.repository.WorkingScheduleRepository;

@Service
public class WorkingScheduleService {

    private final WorkingScheduleRepository repository;

    public WorkingScheduleService(WorkingScheduleRepository repository) {
        this.repository = repository;
    }

    public WorkingSchedule create(WorkingSchedule schedule) {
        return repository.save(schedule);
    }

    public List<WorkingSchedule> getAll() {
        return repository.findAll();
    }

    public WorkingSchedule getByDay(DayOfWeek day) {
        return repository.findByDay(day)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No schedule for " + day));
    }

    public WorkingSchedule update(Long id, WorkingSchedule updated) {
        WorkingSchedule schedule = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Schedule not found"));

        schedule.setDay(updated.getDay());
        schedule.setStartTime(updated.getStartTime());
        schedule.setEndTime(updated.getEndTime());
        schedule.setHoliday(updated.isHoliday());

        return repository.save(schedule);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
