package com.lockers.outerpark.domain.seat.entity;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.seat.exception.SeatException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@Getter
@NoArgsConstructor
public class Seat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id", nullable = false)
	private Concert concert;

	@Column(nullable = false)
	private int seatNumber;

	@Column(nullable = false)
	private Boolean isReserved = false;

	public Seat(Concert concert, int seatNumber) {
		this.concert = concert;
		this.seatNumber = seatNumber;
		this.isReserved = false;
	}

	// 비즈니스 메서드

	/**
	 * 좌석 예약
	 */
	public void reserve() {
		if (this.isReserved) {
			throw new SeatException.SeatAlreadyReservedException();
		}
		if (this.getIsDeleted()) {
			throw new SeatException.SeatAlreadyDeletedException();
		}
		this.isReserved = true;
	}

	/**
	 * 좌석 예약 취소
	 */
	public void cancelSeatReservation() {
		if (!this.isReserved) {
			throw new SeatException.SeatNotReservedException();
		}
		if (this.getIsDeleted()) {
			throw new SeatException.SeatAlreadyDeletedException();
		}
		this.isReserved = false;
	}
}
