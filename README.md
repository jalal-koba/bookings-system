# University Booking System

A comprehensive appointment booking system built with Spring Boot that allows users to book appointments for various services, with role-based access control and administrative capabilities.

## Features

- **Role-based Access Control**: Different roles (ADMIN, STAFF, CUSTOMER) with specific permissions
- **Service Management**: Create, update, and manage services offered
- **Appointment Booking**: Customers can book appointments for services
- **Approval Workflow**: Administrators can approve or reject appointments
- **Staff Assignment**: Assign staff members to specific services
- **Working Schedule Management**: Define working hours for the organization
- **AI Integration**: Suggest optimal appointment times using AI
- **Email Notifications**: Automated email notifications for appointments
- **SSL Support**: Secure communication with SSL/TLS encryption

## Technologies Used

- **Spring Boot 3.2.5** - Application framework
- **Java 17** - Programming language
- **Spring Security** - Authentication and authorization
- **JWT** - Token-based authentication
- **Spring Data JPA** - Database access layer
- **H2 Database** - In-memory database for development
- **WebSocket** - Real-time communication
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

## Setup and Installation

### 1. Clone the repository
```bash
git clone <repository-url>
cd university-booking-system
```

### 2. Build the project
```bash
mvn clean install
```

### 3. Run the application
```bash
mvn spring-boot:run
```

Or build and run the JAR file:
```bash
mvn clean package
java -jar target/bookings-0.0.1-SNAPSHOT.jar
```

### 4. Access the application
The application will be accessible at: `https://localhost:8443`

> Note: Since the application uses SSL, you may need to accept the self-signed certificate in your browser.

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user (role defaults to CUSTOMER)
- `POST /api/auth/login` - Authenticate user and get JWT token

### User Management
- `POST /api/users` - Create a new user (ADMIN only)
- `POST /api/admin/users/staff/create` - Create a new staff member (ADMIN only)

### Services
- `GET /api/services` - Get all services
- `GET /api/services/{id}` - Get a specific service
- `POST /api/services/create` - Create a new service (ADMIN only)
- `PUT /api/services/update/{id}` - Update a service (ADMIN only)
- `DELETE /api/services/delete/{id}` - Delete a service (ADMIN only)
- `POST /api/services/{serviceId}/assign-staff/{staffId}` - Assign staff to a service (ADMIN only)
- `GET /api/services/{serviceId}/staff` - Get staff assigned to a service

### Staff Services
- `GET /api/staff/services` - Get services assigned to the authenticated staff member (STAFF only)

### Appointments
- `POST /api/appointments/create` - Create a new appointment (CUSTOMER only)
- `GET /api/appointments/{id}` - Get a specific appointment
- `PUT /api/appointments/{id}/approve` - Approve an appointment (ADMIN only)
- `PUT /api/appointments/{id}/reject` - Reject an appointment (ADMIN only)

### Working Schedules
- `GET /api/working-schedules` - Get all working schedules
- `GET /api/working-schedules/{day}` - Get schedule for a specific day
- `POST /api/working-schedules/create` - Create a working schedule (ADMIN only)
- `PUT /api/working-schedules/update/{id}` - Update a working schedule (ADMIN only)
- `DELETE /api/working-schedules/delete/{id}` - Delete a working schedule (ADMIN only)

### AI Suggestions
- `GET /api/ai/suggest/{serviceId}` - Get AI-suggested best time for a service

### H2 Console
- `GET /h2-console` - Access the H2 database console (for development)

## Roles and Permissions

- **ADMIN**: Full access to all features including user management, service creation, appointment approval/rejection
- **STAFF**: Access to view services assigned to them
- **CUSTOMER**: Ability to create appointments and view their own appointments

## Configuration

The application is configured through `src/main/resources/application.yml`:

- Server runs on port 8443 with SSL enabled
- Uses H2 in-memory database
- Email configuration for notifications
- JWT for authentication tokens

## Database Schema

The system includes the following main entities:

- **User**: Stores user information (customers, staff, admins)
- **ServiceEntity**: Defines available services with prices and durations
- **Appointment**: Represents booked appointments with status
- **WorkingSchedule**: Defines working hours for the organization

## Security

- JWT-based authentication
- Role-based authorization using Spring Security
- SSL/TLS encryption for secure communication
- Passwords are encrypted using Spring's PasswordEncoder

## Development

To run tests:
```bash
mvn test
```

To run with debug mode:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Troubleshooting

1. **SSL Certificate Issues**: The application uses a self-signed certificate. You may need to accept security exceptions in your browser.

2. **Database Connection**: The app uses an in-memory H2 database. Data will be lost when the application restarts.

3. **Port Conflicts**: If port 8443 is in use, update the `application.yml` file to use a different port.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.