package com.mentoria.medical_appointment.service;

import com.mentoria.medical_appointment.entity.ResponseDto;
import com.mentoria.medical_appointment.repository.AppointmentRepository;
import com.mentoria.medical_appointment.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public Mono<ResponseDto> getDoctorAppointments(String doctorId) {
        return Mono.zip(doctorRepository.getById(doctorId), appointmentRepository.getAvailableAppointmentsByDoctorId(doctorId))
                .map(tuple2 -> ResponseDto.builder()
                        .doctorInfo(tuple2.getT1())
                        .availableAppointments(tuple2.getT2())
                        .build());

    }

}
