package com.lockers.outerpark.domain.concert.entity;

import java.time.LocalDate;

import com.lockers.outerpark.common.entity.BaseEntity;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "concerts", indexes = {
	@Index(name = "idx_isDeleted_performanceDate", columnList = "isDeleted, performanceDate ASC, id ASC")})
@Getter
public class Concert extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private int runningTime;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int limitAge;

	@Column(nullable = false)
	private LocalDate performanceDate;

	public Concert(User writer, String title, int runningTime, int price, int limitAge, LocalDate performanceDate) {
		this.writer = writer;
		this.title = title;
		this.runningTime = runningTime;
		this.price = price;
		this.limitAge = limitAge;
		this.performanceDate = performanceDate;
	}

	@Builder
	public Concert(Long id, User writer, String title, int runningTime, int price, int limitAge,
		LocalDate performanceDate) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.runningTime = runningTime;
		this.price = price;
		this.limitAge = limitAge;
		this.performanceDate = performanceDate;
	}

	public Concert() {

	}

	public static Concert of(User writer, RegisterConcertRequest request) {
		return new Concert(
			writer,
			request.getTitle(),
			request.getRunningTime(),
			request.getPrice(),
			request.getLimitAge(),
			request.getPerformanceDate()
		);
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateRunningTime(Integer runningTime) {
		this.runningTime = runningTime;
	}

	public void updatePrice(Integer price) {
		this.price = price;
	}

	public void updateLimitAge(Integer limitAge) {
		this.limitAge = limitAge;
	}

	public void updatePerformanceDate(LocalDate performanceDate) {
		this.performanceDate = performanceDate;
	}
}
