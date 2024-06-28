package com.mentoria.medical_appointment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Doctor {
    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("doctorId")}))
    private String doctorId;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
}
