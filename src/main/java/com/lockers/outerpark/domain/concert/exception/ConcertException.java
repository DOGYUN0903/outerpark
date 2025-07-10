package com.lockers.outerpark.domain.concert.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class ConcertException {
    public static class ConcertNotFoundException extends BusinessException {
        public ConcertNotFoundException() {
            super(ConcertErrorCode.CONCERT_NOT_FOUND);
        }
    }

    public static class ConcertAlreadyDeletedException extends BusinessException {
        public ConcertAlreadyDeletedException() {
            super(ConcertErrorCode.CONCERT_ALREADY_DELETE);
        }
    }
}
