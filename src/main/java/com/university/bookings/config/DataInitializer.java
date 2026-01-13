package com.university.bookings.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.university.bookings.Entities.Role;
import com.university.bookings.Entities.User;
import com.university.bookings.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByEmail("admin@admin.com").isPresent()) {
                return;
            }

            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("developer123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println(" Admin user created: admin@admin.com");
        };
    }
}
