package com.university.bookings.Entities;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "working_schedule")
public class WorkingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, unique = true)
    private DayOfWeek day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private boolean holiday = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }


    public Long getId() {
        return id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isHoliday() {
        return holiday;
    }
}
