package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.concert.dto.request.ConcertRegisterRequest;
import com.lockers.outerpark.domain.concert.dto.request.ConcertUpdateRequest;
import com.lockers.outerpark.domain.concert.dto.response.ConcertRegisterResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertUpdateResponse;
import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.exception.ConcertException;
import com.lockers.outerpark.domain.user.exception.UserException;

public interface ConcertService {
	/**
	 * ADMIN 권한을 가진 사용자가 새로운 공연을 등록합니다.
	 *
	 * @param userId 등록 요청을 한 사용자 ID
	 * @param request 공연 등록에 필요한 정보(title, price, performanceDate 등)
	 * @return 등록된 공연 정보를 담은 {@link ConcertRegisterResponse}
	 * @throws UserException ADMIN이 아닌 경우 발생
	 * @author kimyongjun0129
	 */
	ConcertRegisterResponse createConcert(Long userId,
		ConcertRegisterRequest request);

	/**
	 * 주어진 사용자 ID와 수정 요청 데이터를 기반으로 콘서트 정보를 수정합니다.
	 * 요청에 포함된 각 필드를 확인하여 null이 아닌 경우 해당 콘서트 속성을 업데이트합니다.
	 *
	 * @author kimyongjun0129
	 *
	 * @param userId 수정 작업을 수행하는 사용자의 ID. 해당 사용자를 통해 콘서트를 조회합니다.
	 * @param request 콘서트 정보를 수정하기 위한 요청 데이터 (제목, 공연 시간, 가격 등 포함).
	 * @return 수정된 콘서트 정보를 담은 {@link ConcertUpdateResponse} 객체.
	 * @throws UserException 사용자가 존재하지 않는 경우 발생.
	 * @throws ConcertException 공연이 존재하지 않는 경우 발생.
	 */
	ConcertUpdateResponse updateConcert(Long userId, Long concertId, ConcertUpdateRequest request);

	/**
	 * 공연 ID를 기반으로 공연 정보를 조회합니다.
	 *
	 * @param concertId 조회할 공연 ID
	 * @return 조회된 공연 정보를 담은 {@link ConcertResponse} 객체
	 * @throws ConcertException 존재하지 않는 공연일 경우 발생
	 * @author kimyongjun0129
	 */
	ConcertResponse getConcert(Long concertId);

	/**
	 * 콘서트 목록을 페이징 처리하여 조회합니다.
	 *
	 * @param pageable 페이징 및 정렬 정보를 담은 Pageable 객체
	 * @return 콘서트 목록을 DTO로 변환한 Page 객체
	 * @throws IllegalArgumentException 만약 Concert → DTO 변환 과정에서 문제가 생긴 경우 발생
	 * @author kimyongjun0129
	 */
	Page<ConcertResponse> getConcerts(Pageable pageable);

	/**
	 * 지정된 사용자가 작성한 콘서트를 논리적으로 삭제합니다.
	 *
	 * @param userId 현재 로그인한 사용자 ID
	 * @param concertId 삭제 대상 콘서트 ID
	 * @throws UserException 유효하지 않은 사용자일 경우
	 * @throws ConcertException 해당 콘서트가 없거나 작성자가 일치하지 않는 경우
	 * @author kimyongjun0129
	 */
	void deleteConcert(Long userId, Long concertId);

	/**
	 * 주어진 ID에 해당하는 삭제되지 않은 콘서트를 조회합니다.
	 *
	 * @param concertId 조회할 콘서트의 ID
	 * @return 삭제되지 않은 {@link Concert} 객체
	 * @throws ConcertException 콘서트가 삭제되었거나 존재하지 않는 경우 발생
	 * @author kimyongjun0129
	 */
	Concert getActiveConcert(Long concertId);
}
