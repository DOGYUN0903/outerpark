package com.lockers.outerpark.domain.seat.entity;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.concert.entity.Concert;

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
	private String seatNumber;

	// 생성자
	public Seat(Concert concert, String seatNumber) {
		this.concert = concert;
		this.seatNumber = seatNumber;
	}

	// 임시 생성자
	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
	}

}
