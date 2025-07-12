package com.lockers.outerpark.domain.lock.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class LockException {
    public static class SeatAlreadyLockedException extends BusinessException {
        public SeatAlreadyLockedException() {
            super(LockErrorCode.SEAT_LOCK_CONFLICT);
        }
    }
}
