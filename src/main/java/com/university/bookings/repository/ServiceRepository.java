package com.university.bookings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.bookings.Entities.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByStaffList_Id(Long staffId);
}
