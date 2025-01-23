# Notification System

## Overview

The **Notification System** is a microservice-based application that processes and delivers notifications via various channels (Email, SMS, Push). It uses Apache Kafka for event-driven messaging, supports prioritization, and processes notifications in batches for efficiency.

## Technologies Used

- **Spring Boot**: Framework for building the REST API and business logic.
- **Kafka**: Messaging system for handling asynchronous notifications.
- **JPA (Java Persistence API)**: To interact with the database for storing notification data.
- **Docker**: For containerizing Kafka, Zookeeper.
- **MySQL**: Relational database for storing notification records.

## Architecture

The Notification System follows a **microservices architecture**. Key highlights of its design include:

1. **Modular Services**: Each service handles a specific concern:
    - API Layer: Handles incoming HTTP requests.
    - Kafka Producers/Consumers: Decouple request handling from processing and delivery.
    - Notification Service: Processes notifications and delegates delivery to providers.
    - Database Layer: Stores notification data for tracking and auditing.

2. **Event-Driven Messaging**:
    - Kafka is used for communication between producers and consumers, ensuring loose coupling.
    - Notifications are routed to specific Kafka topics based on their priority (High, Medium, Low).

3. **Batch Processing**:
    - The system processes notifications in batches for better throughput and performance.

4. **Scalability and Fault Tolerance**:
    - Kafka ensures reliability by replicating messages across brokers.
    - Consumers can be scaled horizontally to handle increased load.
    - Notifications are processed asynchronously, allowing the system to handle spikes in traffic without affecting performance.

5. **Resilient Error Handling**:
    - The Global Exception Handler manages errors at different layers, ensuring meaningful feedback to the client and proper logging.

## How It Works

1. **Notification Submission**: Client/System send a notification request through the API.
2. **Kafka Messaging**: Notifications are routed to Kafka topics based on their priority.
3. **Batch Processing**: Notifications are consumed and processed in batches for efficiency.
4. **Delivery**: Notifications are sent via the appropriate channel, and the status is updated in the database.

## Setting Up the System

### Prerequisites

- **Java 11** or higher
- **Apache Kafka** and **Zookeeper**
- **MySQL**
- **Docker** (for running Kafka and Zookeeper containers)

### Configuration

1. **Kafka and Zookeeper**: Run the following Docker commands to set up Kafka and Zookeeper:

   ```bash
   docker run -p 2181:2181 --name zookeeper zookeeper
   
   docker run -p 9092:9092 --name kafka \
      -e KAFKA_ZOOKEEPER_CONNECT="localhost:2181" \
      -e KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://localhost:9092" \
      -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
      confluentinc/cp-kafka
   ```

2. **Database**: Set up a MySQL database named `notification_db` and configure the credentials in the application properties.

3. **Run the Application**: Start the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

- **POST /api/notifications**: Submit a notification request.

## Kafka Topics

- `notification-topic-high`
- `notification-topic-medium`
- `notification-topic-low`

## Features

- **Microservices Architecture**: The service is designed as an independent, scalable, and fault-tolerant microservice.
- **Event-Driven Messaging**: Uses Kafka to decouple producers and consumers.
- **Prioritized Notifications**: Processes high-priority notifications faster.
- **Batch Processing**: Enhances performance and reduces overhead.
- **Channel Flexibility**: Supports Email, SMS, and Push notifications.
