package com.university.bookings.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "services")
public class ServiceEntity {
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setStaffList(List<User> staffList) {
        this.staffList = staffList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<User> getStaffList() {
        return staffList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer durationMinutes;
    // أولوية الخدمة (اختياري)
    private Integer priority;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();



    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "service_staff",
        joinColumns = @JoinColumn(name = "service_id"),
        inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<User> staffList;




}