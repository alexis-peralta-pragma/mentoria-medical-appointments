package com.mentoria.medical_appointment.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class AwsConfig {

    @Bean
    public DynamoDbAsyncClient getDynamoDbClient() {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(getProviderChain())
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    private AwsCredentialsProvider getProviderChain(){
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }
}