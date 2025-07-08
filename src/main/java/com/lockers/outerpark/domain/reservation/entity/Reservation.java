package com.lockers.outerpark.domain.reservation.entity;

import com.lockers.outerpark.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

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
