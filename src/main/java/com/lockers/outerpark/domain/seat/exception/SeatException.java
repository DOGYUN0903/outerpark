package com.lockers.outerpark.domain.seat.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class SeatException {

	public static class SeatNotFoundException extends BusinessException {
		public SeatNotFoundException() {
			super(SeatErrorCode.SEAT_NOT_FOUND);
		}

		public SeatNotFoundException(String customMessage) {
			super(SeatErrorCode.SEAT_NOT_FOUND);
		}
	}

	public static class SeatAlreadyReservedException extends BusinessException {
		public SeatAlreadyReservedException() {
			super(SeatErrorCode.SEAT_ALREADY_RESERVED);
		}
	}

	public static class SeatNotReservedException extends BusinessException {
		public SeatNotReservedException() {
			super(SeatErrorCode.SEAT_NOT_RESERVED);
		}
	}

	public static class SeatAlreadyDeletedException extends BusinessException {
		public SeatAlreadyDeletedException() {
			super(SeatErrorCode.SEAT_ALREADY_DELETED);
		}
	}

	public static class DuplicateSeatsExistException extends BusinessException {
		public DuplicateSeatsExistException() {
			super(SeatErrorCode.DUPLICATE_SEATS_EXIST);
		}
	}

	public static class InvalidSeatSelectionException extends BusinessException {
		public InvalidSeatSelectionException(String customMessage) {
			super(SeatErrorCode.INVALID_SEAT_SELECTION);
		}
	}
}