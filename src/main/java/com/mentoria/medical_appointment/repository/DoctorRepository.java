package com.mentoria.medical_appointment.repository;

import com.mentoria.medical_appointment.entity.Doctor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;

@Repository
public class DoctorRepository {
    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    private final DynamoDbAsyncTable<Doctor> table;

    public DoctorRepository(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.table = dynamoDbEnhancedAsyncClient
                .table("doctors", TableSchema.fromBean(Doctor.class));
    }

    public Mono<Doctor> getById(String doctorId){
        return Mono.fromFuture(table.getItem(Key.builder().partitionValue(doctorId).build()));
    }
}
