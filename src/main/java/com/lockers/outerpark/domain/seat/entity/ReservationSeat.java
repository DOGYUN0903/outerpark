package com.lockers.outerpark.domain.seat.entity;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

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
import lombok.Setter;

@Entity
@Table(name = "reservation_seats")
@Getter
@Setter
@NoArgsConstructor
public class ReservationSeat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;

	@Column(nullable = false)
	private String reservationNumber;

	/**
	 * 예약과 좌석을 연결하는 ReservationSeat 생성
	 */
	public ReservationSeat(Seat seat, String reservationNumber) {
		this.seat = seat;
		this.reservationNumber = reservationNumber;
	}
}