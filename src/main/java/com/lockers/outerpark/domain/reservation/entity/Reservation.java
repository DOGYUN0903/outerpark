package com.lockers.outerpark.domain.reservation.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private final ReservationStatus status = ReservationStatus.CONFIRMED;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;
	@Column(name = "reservation_number", nullable = false)
	private String reservationNumber;
	@Column(nullable = false)
	private int amount;

	@CreatedDate
	@Column(name = "reserved_at")
	private LocalDate reservedAt;

	@LastModifiedDate
	@Column(name = "cancelled_at")
	private LocalDate cancelledAt;

	public Reservation(User user, Seat seat, String reservationNumber, int amount) {
		this.user = user;
		this.seat = seat;
		this.reservationNumber = reservationNumber;
		this.amount = amount;
	}
}
