package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.dto.FindConcertResponse;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertResponse;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertRequest;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertResponse;
import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.exception.UserException;
import com.lockers.outerpark.domain.user.repository.UserRepository;

@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    public ConcertServiceImpl(ConcertRepository concertRepository, UserRepository userRepository) {
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RegisterConcertResponse registerConcert(Long userId, RegisterConcertRequest request) {

        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);

        if (!user.getUserRole().equals(UserRole.ADMIN))
            throw new UserException.InvalidUserRoleException();

        Concert concert = new Concert(user,
            request.getTitle(),
            request.getRunningTime(),
            request.getPrice(),
            request.getPrice(),
            request.getPerformanceDate());

        Concert saveConcert = concertRepository.save(concert);
        return new RegisterConcertResponse(saveConcert);
    }

    @Override
    public UpdateConcertResponse updateConcert(Long userId, UpdateConcertRequest request) {
        return null;
    }

    @Override
    public FindConcertResponse findConcert(Long concertId) {
        return null;
    }

    @Override
    public Page<FindConcertResponse> findConcerts() {
        return null;
    }

    @Override
    public void deleteConcert(Long userId) {

    }
}
