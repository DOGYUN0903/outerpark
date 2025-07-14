package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.dto.request.ConcertRegisterRequest;
import com.lockers.outerpark.domain.concert.dto.request.ConcertUpdateRequest;
import com.lockers.outerpark.domain.concert.dto.response.ConcertRegisterResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertUpdateResponse;
import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.exception.ConcertErrorCode;
import com.lockers.outerpark.domain.concert.exception.ConcertException;
import com.lockers.outerpark.domain.concert.repository.ConcertRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

	private final ConcertRepository concertRepository;
	private final UserService userService;

	@Override
	@Transactional
	public ConcertRegisterResponse createConcert(Long userId, ConcertRegisterRequest request) {

		User user = userService.getActiveUserById(userId);

		Concert concert = request.toEntity(user);

		Concert saveConcert = concertRepository.save(concert);
		return ConcertRegisterResponse.of(saveConcert);
	}

	@Override
	@Transactional
	public ConcertUpdateResponse updateConcert(Long userId, Long concertId, ConcertUpdateRequest request) {

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

		return ConcertUpdateResponse.of(concert);
	}

	@Override
	@Transactional(readOnly = true)
	public ConcertResponse getConcert(Long concertId) {

		Concert concert = getActiveConcert(concertId);

		return ConcertResponse.of(concert);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ConcertResponse> getConcerts(Pageable pageable) {
		Page<Concert> concerts = concertRepository.findAllByIsDeletedFalse(pageable);

		return concerts.map(ConcertResponse::of);
	}

	@Override
	@Transactional
	public void deleteConcert(Long userId, Long concertId) {

		// 사용자 조회 및 유효성 검사
		userService.validateActiveUserById(userId);

		Concert concert = getActiveConcert(concertId);

		// 공연 논리삭제
		concert.softDelete();
	}

	//콘서트 조회 및 유효성 검사\
	@Override
	@Transactional(readOnly = true)
	public Concert getActiveConcert(Long concertId) {

		return concertRepository.findByIdAndIsDeletedFalse(concertId)
			.orElseThrow(() -> new ConcertException(ConcertErrorCode.CONCERT_ALREADY_DELETE));
	}
}
