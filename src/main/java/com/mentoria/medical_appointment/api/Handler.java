package com.mentoria.medical_appointment.api;

import com.mentoria.medical_appointment.service.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final AppointmentService appointmentService;
    public Mono<ServerResponse> getDoctorAppointments(ServerRequest serverRequest) {
        String doctorId = serverRequest.pathVariable("doctorId");
        return this.appointmentService.getDoctorAppointments(doctorId)
                .log()
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(throwable -> {
                    System.out.println(throwable.getMessage());
                    return ServerResponse.status(HttpStatus.BAD_GATEWAY).build();
                });
    }
}
