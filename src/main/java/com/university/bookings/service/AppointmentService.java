package com.university.bookings.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.university.bookings.Entities.Appointment;
import com.university.bookings.Entities.AppointmentStatus;
import com.university.bookings.Entities.Role;
import com.university.bookings.Entities.ServiceEntity;
import com.university.bookings.Entities.User;
import com.university.bookings.Entities.WorkingSchedule;
import com.university.bookings.dto.CreateAppointmentRequest;
import com.university.bookings.repository.AppointmentRepository;
import com.university.bookings.repository.ServiceRepository;
import com.university.bookings.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final WorkingScheduleService workingScheduleService;
    private final NotificationService notificationService;
    private final EmailService emailService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            ServiceRepository serviceRepository,
            UserRepository userRepository,
            WorkingScheduleService workingScheduleService,
            NotificationService notificationService,
            EmailService emailService) {

        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.workingScheduleService = workingScheduleService;
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    // ===============================
    // CUSTOMER – إنشاء موعد
    // ===============================
    public Appointment createAppointment(
            CreateAppointmentRequest request,
            String userEmail) {

        User customer = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can book appointments");
        }

        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        DayOfWeek day = start.getDayOfWeek();
        WorkingSchedule schedule = workingScheduleService.getByDay(day);

        if (schedule.isHoliday()) {
            throw new RuntimeException("Cannot book on holiday");
        }

        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        if (startTime.isBefore(schedule.getStartTime()) ||
                endTime.isAfter(schedule.getEndTime())) {
            throw new RuntimeException("Outside working hours");
        }

        // منع تداخل المواعيد
        boolean conflict = appointmentRepository
                .existsConflictingAppointment(start, end);

        if (conflict) {
            throw new RuntimeException("Appointment time is already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setService(service);
        appointment.setStartTime(start);
        appointment.setEndTime(end);
        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);

        //  WebSocket Notification
        notificationService.notifyUser(
                customer.getId(),
                "An appointment booking request has been sent " + start
        );

        emailService.sendEmail(
                customer.getEmail(),
                "تم تقديم طلب تحديد موعد",
                "Your appointment request has been successfully submitted.\n\n"
                        + "Date & Time: " + start + "\n"
                        + "Status: PENDING"
        );


        return saved;
    }

    // ===============================
    // ADMIN – الموافقة على موعد
    // ===============================
    @Transactional
    public Appointment approve(Long id) {

        Appointment appt = getById(id);
        appt.setStatus(AppointmentStatus.APPROVED);

        Appointment saved = appointmentRepository.save(appt);

        notificationService.notifyUser(
                appt.getCustomer().getId(),
                "تمت الموافقة على موعدك"
        );

        emailService.sendEmail(
                appt.getCustomer().getEmail(),
                "تمت الموافقة على الموعد",
                "Good news!\n\nYour appointment has been APPROVED.\n"
                        + "Date & Time: " + appt.getStartTime()
        );


        return saved;
    }

    // ===============================
    // ADMIN – رفض موعد
    // ===============================
    @Transactional
    public Appointment reject(Long id) {

        Appointment appt = getById(id);
        appt.setStatus(AppointmentStatus.REJECTED);

        Appointment saved = appointmentRepository.save(appt);

        notificationService.notifyUser(
                appt.getCustomer().getId(),
                "تم رفض موعدك"
        );

        emailService.sendEmail(
                appt.getCustomer().getEmail(),
                "تم رفض الموعد",
                "We are sorry to inform you that your appointment has been REJECTED.\n\n"
                        + "Please try booking another time slot."
        );


        return saved;
    }

    // ===============================
    // ADMIN – جلب كل المواعيد
    // ===============================
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    // ===============================
    // Helper
    // ===============================
    private Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Appointment getById(Long id, String email) {

        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // ADMIN يشوف كل شي
        if (appt.getCustomer().getEmail().equals(email)) {
            return appt;
        }

        // غير صاحب الموعد
        throw new RuntimeException("Access denied");
    }

}
