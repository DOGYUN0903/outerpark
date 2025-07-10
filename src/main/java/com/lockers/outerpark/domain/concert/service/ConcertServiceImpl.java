package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.lockers.outerpark.domain.user.exception.UserException;
import com.lockers.outerpark.domain.user.service.UserService;

@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final UserService userService;

    public ConcertServiceImpl(ConcertRepository concertRepository, UserService userService) {
        this.concertRepository = concertRepository;
        this.userService = userService;
    }

    /**
     * ADMIN 권한을 가진 사용자가 새로운 공연을 등록합니다.
     *
     * @param userId 등록 요청을 한 사용자 ID
     * @param request 공연 등록에 필요한 정보(title, price, performanceDate 등)
     * @return 등록된 공연 정보를 담은 {@link RegisterConcertResponse}
     * @throws UserException.InvalidUserRoleException ADMIN이 아닌 경우 발생
     * @author kimyongjun0129
     */
    @Override
    @Transactional
    public RegisterConcertResponse registerConcert(Long userId, RegisterConcertRequest request) {

        User user = userService.getActiveUserById(userId);

        Concert concert = Concert.of(user, request);

        Concert saveConcert = concertRepository.save(concert);
        return RegisterConcertResponse.of(saveConcert);
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
    public UpdateConcertResponse updateConcert(Long userId, Long concertId, UpdateConcertRequest request) {

        // 사용자 조회 및 유효성 검사
        User loginUser = userService.getActiveUserById(userId);

        Concert concert = getActiveConcert(concertId);

        // 각 필드가 null이 아닌 경우에만 업데이트 수행
        // String의 경우 빈칸이 아닌 경우에만도 업데이트하도록 수정
        if (!request.getTitle().isBlank()) {
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

        return UpdateConcertResponse.of(concert);
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

        Concert concert = getActiveConcert(concertId);

        return FindConcertResponse.of(concert);
    }

    /**
     * 콘서트 목록을 페이징 처리하여 조회합니다.
     *
     * @param pageable 페이징 및 정렬 정보를 담은 Pageable 객체
     * @return 콘서트 목록을 DTO로 변환한 Page 객체
     * @throws IllegalArgumentException 만약 Concert → DTO 변환 과정에서 문제가 생긴 경우 발생
     * @author kimyongjun0129
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FindConcertResponse> findConcerts(Pageable pageable) {
        Page<Concert> concerts = concertRepository.findAllByIsDeletedFalse(pageable);

        return concerts.map(FindConcertResponse::of);
    }

    /**
     * 지정된 사용자가 작성한 콘서트를 논리적으로 삭제합니다.
     *
     * @param userId 현재 로그인한 사용자 ID
     * @param concertId 삭제 대상 콘서트 ID
     * @throws UserException.UserNotFoundException 유효하지 않은 사용자일 경우
     * @throws ConcertException.ConcertNotFoundException 해당 콘서트가 없거나 작성자가 일치하지 않는 경우
     * @author kimyongjun0129
     */
    @Override
    @Transactional
    public void deleteConcert(Long userId, Long concertId) {

        // 사용자 조회 및 유효성 검사
        User loginUser = userService.getActiveUserById(userId);

        Concert concert = getActiveConcert(concertId);

        // 공연 논리삭제
        concert.softDelete();
    }

    //콘서트 조회 및 유효성 검사
    @Transactional(readOnly = true)
    public Concert getActiveConcert(Long concertId) {

        return concertRepository.findByIdAndIsDeletedFalse(concertId)
            .orElseThrow(ConcertException.ConcertNotFoundException::new);
    }
}
