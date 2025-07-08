package com.lockers.outerpark.domain.reservation.controller;

import com.lockers.outerpark.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

}
