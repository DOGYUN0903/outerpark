package com.lockers.outerpark.domain.lock.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lockers.outerpark.domain.lock.service.RedisLockService;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LockAspect {

    private final RedisLockService redisLockService;

    @Order
    @Around("execution(* com.lockers.outerpark.domain.reservation.service.ReservationServiceImpl.createReservationV1(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // ReservationServiceImpl.createReservation 파라미터 가져오기
        Object[] args = joinPoint.getArgs();
        ReservationRequest request = (ReservationRequest)args[0];
        List<Long> seatIds = request.getSeatIds();
        Long concertId = (Long)args[2];

        // 락 획득 & 실패 시 내부에서 예외 처리
        String lockId = redisLockService.acquireLock(concertId, seatIds);
        try {
            return joinPoint.proceed();
        } finally {
            // 락 해제
            redisLockService.releaseLock(concertId, seatIds, lockId);
        }
    }
}
