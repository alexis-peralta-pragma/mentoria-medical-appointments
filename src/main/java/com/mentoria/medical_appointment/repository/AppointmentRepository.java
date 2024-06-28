package com.mentoria.medical_appointment.repository;

import com.mentoria.medical_appointment.entity.Appointments;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepository {

    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    private final DynamoDbAsyncTable<Appointments> table;

    public AppointmentRepository(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.table = dynamoDbEnhancedAsyncClient
                .table("appointments", TableSchema.fromBean(Appointments.class));
    }

    public Mono<List<Appointments>> getAvailableAppointmentsByDoctorId(String doctorId){
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("doctorId = :doctorId AND available = :available")
                        .expressionValues(
                                Map.of(":doctorId", AttributeValue.builder().s(doctorId).build(),
                                        ":available", AttributeValue.builder().bool(true).build()))
                        .build())
                .build();

        // Ejecutar la consulta de escaneo y recopilar los resultados en una lista
        var pagePublisher = table.scan(scanRequest);
        return Mono.from(pagePublisher).map(page -> page.items().stream().collect(Collectors.toList()));

    }
}
