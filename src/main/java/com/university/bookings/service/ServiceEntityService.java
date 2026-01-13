package com.university.bookings.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.university.bookings.Entities.Role;
import com.university.bookings.Entities.ServiceEntity;
import com.university.bookings.Entities.User;
import com.university.bookings.exception.ResourceNotFoundException;
import com.university.bookings.repository.ServiceRepository;
import com.university.bookings.repository.UserRepository;

@Service
public class ServiceEntityService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository; 

    public ServiceEntityService(ServiceRepository serviceRepository,
        UserRepository userRepository) { // ✅
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        }

    public ServiceEntity create(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public List<ServiceEntity> getAll() {
        return serviceRepository.findAll();
    }

    public ServiceEntity getById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service not found"));
    }

    public ServiceEntity update(Long id, ServiceEntity updatedService) {
        ServiceEntity service = getById(id);

        service.setName(updatedService.getName());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());
        service.setDurationMinutes(updatedService.getDurationMinutes());
        service.setPriority(updatedService.getPriority());

        return serviceRepository.save(service);
    }

    public void delete(Long id) {
        ServiceEntity service = getById(id);
        serviceRepository.delete(service);
    }
    public ServiceEntity assignStaffToService(Long serviceId, Long staffId) {

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    
        if (staff.getRole() != Role.STAFF) {
            throw new RuntimeException("User is not staff");
        }
    
        service.getStaffList().add(staff);
    
        return serviceRepository.save(service);
    }
    public List<ServiceEntity> getStaffServices(String staffEmail) {

        User staff = userRepository.findByEmail(staffEmail)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    
        if (staff.getRole() != Role.STAFF) {
            throw new RuntimeException("Access denied");
        }
    
        return serviceRepository.findByStaffList_Id(staff.getId());
    }
    
    
}
