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
import com.lockers.outerpark.domain.concert.exception.ConcertException;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.exception.UserException;
import com.lockers.outerpark.domain.user.repository.UserRepository;
import com.lockers.outerpark.domain.user.service.UserService;

@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ConcertServiceImpl(ConcertRepository concertRepository, UserRepository userRepository,
        UserService userService) {
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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

    /**
     * 주어진 사용자 ID와 수정 요청 데이터를 기반으로 콘서트 정보를 수정합니다.
     * 요청에 포함된 각 필드를 확인하여 null이 아닌 경우 해당 콘서트 속성을 업데이트합니다.
     *
     * @author kimyongjun0129
     *
     * @param userId 수정 작업을 수행하는 사용자의 ID. 해당 사용자를 통해 콘서트를 조회합니다.
     * @param request 콘서트 정보를 수정하기 위한 요청 데이터 (제목, 공연 시간, 가격 등 포함).
     * @return 수정된 콘서트 정보를 담은 {@link UpdateConcertResponse} 객체.
     * @throws UserException.UserNotFoundException 사용자가 존재하지 않는 경우 발생.
     * @throws ConcertException.ConcertNotFoundException 공연이 존재하지 않는 경우 발생.
     */
    @Override
    @Transactional
    public UpdateConcertResponse updateConcert(Long userId, UpdateConcertRequest request) {

        // 사용자 조회 및 유효성 검사
        User loginUser = userService.getActiveUserById(userId);

        // 콘서트 조회 및 유효성 검사
        Concert concert = concertRepository.findConcertByWriter(loginUser)
            .orElseThrow(ConcertException.ConcertNotFoundException::new);

        // 각 필드가 null이 아닌 경우에만 업데이트 수행
        if (request.getTitle() != null) {
            concert.updateTitle(request.getTitle());
        }

        if (request.getRunningTime() != null) {
            concert.updateRunningTime(request.getRunningTime());
        }

        if (request.getPrice() != null) {
            concert.updatePrice(request.getPrice());
        }

        if (request.getPerformanceDate() != null) {
            concert.updatePerformanceDate(request.getPerformanceDate());
        }

        if (request.getLimitAge() != null) {
            concert.updateLimitAge(request.getLimitAge());
        }

        Concert updateConcert = concertRepository.save(concert);

        return new UpdateConcertResponse(updateConcert);
    }

    /**
     * 공연 ID를 기반으로 공연 정보를 조회합니다.
     *
     * @param concertId 조회할 공연 ID
     * @return 조회된 공연 정보를 담은 {@link FindConcertResponse} 객체
     * @throws ConcertException.ConcertNotFoundException 존재하지 않는 공연일 경우 발생
     * @author kimyongjun0129
     */
    @Override
    @Transactional(readOnly = true)
    public FindConcertResponse findConcert(Long concertId) {

        // 공연 조회 및 유효성 검사
        Concert concert = concertRepository.findById(concertId)
            .orElseThrow(ConcertException.ConcertNotFoundException::new);

        return new FindConcertResponse(concert);
    }

    @Override
    public Page<FindConcertResponse> findConcerts() {
        return null;
    }

    @Override
    public void deleteConcert(Long userId) {

    }
}
