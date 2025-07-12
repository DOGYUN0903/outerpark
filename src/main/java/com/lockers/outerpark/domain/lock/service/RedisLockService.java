package com.lockers.outerpark.domain.lock.service;

import static com.lockers.outerpark.domain.lock.exception.LockException.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lockers.outerpark.domain.lock.repository.RedisLockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final RedisLockRepository redisLockRepository;
    private static final long LOCK_TTL = 3000; // TTL = 3초

    // 여러 좌석 락 획득
    public String acquireLock(Long concertId, List<Long> seatIds) {
        String uuid = UUID.randomUUID().toString(); // 락 소유자 식별용
        List<Long> acquiredSeatIds = new ArrayList<>(); // 지금까지 락을 성공한 seatId 모아둠

        for (Long seatId : seatIds) {
            String key = generateKey(concertId, seatId);
            boolean isLocked = redisLockRepository.tryLock(key, uuid, LOCK_TTL);

            if (!isLocked) {
                for (Long acquiredId : acquiredSeatIds) {
                    redisLockRepository.unlock(generateKey(concertId, acquiredId), uuid);
                }
                throw new SeatAlreadyLockedException();
            }

            acquiredSeatIds.add(seatId);
        }

        return uuid;
    }

    // 여러 좌석 락 해제
    public void releaseLock(Long concertId, List<Long> seatIds, String uuid) {
        for (Long seatId : seatIds) {
            String key = generateKey(concertId, seatId);
            redisLockRepository.unlock(key, uuid);
        }
    }

    private String generateKey(Long concertId, Long seatId) {
        return "lock:concert:" + concertId + ":seat:" + seatId;
    }
}
