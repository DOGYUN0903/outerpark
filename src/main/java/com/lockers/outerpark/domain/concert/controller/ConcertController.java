package com.lockers.outerpark.domain.concert.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.concert.dto.request.ConcertRegisterRequest;
import com.lockers.outerpark.domain.concert.dto.request.ConcertUpdateRequest;
import com.lockers.outerpark.domain.concert.dto.response.ConcertRegisterResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertUpdateResponse;
import com.lockers.outerpark.domain.concert.service.ConcertService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertController {

	private final ConcertService concertService;

	@PostMapping
	public ResponseEntity<ApiResponse<ConcertRegisterResponse>> createConcert(
		@AuthenticationPrincipal Long id,
		@Valid @RequestBody ConcertRegisterRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success("콘서트가 등록되었습니다.", concertService.createConcert(id, request)));
	}

	@PatchMapping("/{concertId}")
	public ResponseEntity<ApiResponse<ConcertUpdateResponse>> updateConcert(
		@AuthenticationPrincipal Long id,
		@PathVariable Long concertId,
		@RequestBody ConcertUpdateRequest request
	) {

		return ResponseEntity.ok()
			.body(ApiResponse.success("콘서트가 수정되었습니다.", concertService.updateConcert(id, concertId, request)));
	}

	@GetMapping("/{concertId}")
	public ResponseEntity<ApiResponse<ConcertResponse>> getConcert(
		@PathVariable Long concertId
	) {

		return ResponseEntity.ok()
			.body(ApiResponse.success("콘서트가 조회되었습니다.", concertService.getConcert(concertId)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<ConcertResponse>>> getConcertsPage(
		Pageable pageable
	) {

		return ResponseEntity.ok()
			.body(ApiResponse.success("콘서트가 조회되었습니다.", concertService.getConcerts(pageable)));
	}

	@DeleteMapping("/{concertId}")
	public ResponseEntity<ApiResponse<ConcertResponse>> deleteConcert(
		@AuthenticationPrincipal Long id,
		@PathVariable Long concertId
	) {

		concertService.deleteConcert(id, concertId);

		return ResponseEntity.ok()
			.body(ApiResponse.success("콘서트가 삭제되었습니다.", null));

	}
}
