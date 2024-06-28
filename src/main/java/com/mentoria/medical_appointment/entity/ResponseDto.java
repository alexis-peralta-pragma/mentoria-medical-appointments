package com.mentoria.medical_appointment.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDto {
    private Doctor doctorInfo;
    private List<Appointments> availableAppointments;
}
