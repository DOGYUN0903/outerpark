package com.lockers.outerpark.domain.reservation.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.lockers.outerpark.domain.concert.entity.Concert;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//    @OneToOne(fetch = FetchType.LAZY)
	//    @JoinColumn(name = "seat_id", nullable = false)
	//    private Seat seat;

	/**
	 * 임시 concertId 필드 (ERD의 concert_id 컬럼)
	 * TODO: 향후 Concert 엔티티 완성 후 @ManyToOne 관계로 변경
	 */
	@ManyToOne
	@JoinColumn(name = "concert_id", nullable = false)
	private Concert concert;

	@Column(name = "reservation_number", nullable = false)
	private String reservationNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReservationStatus status = ReservationStatus.CONFIRMED;

	@Column(nullable = false)
	private int amount;

	@CreatedDate
	@Column(name = "reserved_at")
	private LocalDate reservedAt;

	@LastModifiedDate
	@Column(name = "cancelled_at")
	private LocalDate cancelledAt;

	public Reservation(User user, String reservationNumber, int amount) {
		this.user = user;
		this.reservationNumber = reservationNumber;
		this.amount = amount;
	}

}
