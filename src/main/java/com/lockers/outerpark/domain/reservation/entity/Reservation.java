package com.lockers.outerpark.domain.reservation.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.reservation.type.ReservationStatus;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.user.entity.User;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
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
	private ReservationStatus status = ReservationStatus.PENDING;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id", nullable = false)
	private Concert concert;
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
	private List<ReservationSeat> reservationSeats = new ArrayList<>();
	@Column(nullable = false)
	private int count;
	@Column(nullable = false, name = "total_amount")
	private int amount;

	@CreatedDate
	@Column(name = "reserved_at")
	private LocalDate reservedAt;

	@LastModifiedDate
	@Column(name = "cancelled_at")
	private LocalDate cancelledAt;

	public Reservation(User user, Concert concert, int count, int totalAmount) {
		this.user = user;
		this.concert = concert;
		this.count = count;
		this.amount = totalAmount;
	}

	/**
	 * 양방향 편의 관계 메서드
	 */
	public void addReservationSeat(ReservationSeat reservationSeat) {
		reservationSeats.add(reservationSeat);
		reservationSeat.setReservation(this);
	}

	/**
	 * 예약 상태 CONFIRMED로 변경
	 */
	public void confirm() {
		this.status = ReservationStatus.CONFIRMED;
	}

	/**
	 * 예약 상태 CANCELLED로 변경
	 */
	public void cancel() {
		this.status = ReservationStatus.CANCELLED;
	}
}
