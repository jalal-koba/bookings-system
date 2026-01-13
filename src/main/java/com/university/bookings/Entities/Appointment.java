package com.university.bookings.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public User getCustomer() {
        return customer;
    }

    public ServiceEntity getService() {
        return service;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // 👤 الزبون صاحب الحجز
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    // 🧾 الخدمة المحجوزة
    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
}
