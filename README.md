# Incident Management System
This is a **Spring Boot-based Incident Management System** designed to streamline the process of monitoring and resolving various incidents. The application demonstrates a robust backend powered by JPA for database interaction, DTOs for structured data transfers, and services for processing business logic.
## Features
### Core Functionalities:
- **Manage Incidents**: Create, update, and view incident details.
- **Assignable Resources**: Assign units and officers to incidents.
- **Real-Time Location Tracking**: Track units’ locations dynamically.
- **Filtering**: Only fetch visible incidents for better performance and security.

### Core Components:
1. **Services**:
    - DispatcherService: Handles incident resolution, assignments, and data retrieval.
    - UnitService: Manages unit-related actions, such as fetching and updating unit data.

2. **Controllers**:
    - DispatcherController: Endpoints for dispatcher-related operations.
    - UnitController: Endpoints for unit management.

3. **DTOs**:
    - Encapsulate data structures for incidents and updates (e.g., `CreateIncidentDto`, `ResolveIncidentDto`).

4. **Persistence**:
    - Entity classes like `Incident` and `Unit` define relationships and database structure.
    - Repositories such as `IncidentRepository` for seamless database operations.

## Tech Stack
- **Backend Framework**: Spring Boot.
- **Persistence**: Spring Data JPA with Hibernate.
- **Database**: Compatible with relational databases (e.g., MySQL, PostgreSQL, H2 for testing).
- **Language**: Java 17+.
- **Build Tool**: Maven.
- **Other Tools**: Lombok (to reduce boilerplate code).
