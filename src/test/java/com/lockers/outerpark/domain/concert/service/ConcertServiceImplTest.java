package com.lockers.outerpark.domain.concert.service;

import static com.lockers.outerpark.domain.user.entity.UserRole.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

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

@ExtendWith(MockitoExtension.class)
class ConcertServiceImplTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertServiceImpl concertServiceImpl;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("공연 등록 성공")
    void 공연_등록_성공() {
        //given
        Long userId = 1L;

        User user = new User("example@naver.com", "hero123", LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L,
            ADMIN);

        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);

        //when
        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.save(any(Concert.class))).thenReturn(concert);
        RegisterConcertResponse response = concertServiceImpl.registerConcert(userId, request);

        //then
        verify(userService).getActiveUserById(userId);
        verify(concertRepository).save(any(Concert.class));

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("제목");
    }

    @Test
    @DisplayName("공연 등록 실패")
    void 사용자_권한_부족으로_인한_공연_등록_실패() {
        //given
        Long userId = 1L;

        User user = new User("example@naver.com", "hero123", LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L,
            USER);

        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        //when & then
        when(userService.getActiveUserById(userId)).thenReturn(user);
        assertThrows(UserException.InvalidUserRoleException.class, () -> {
            concertServiceImpl.registerConcert(userId, request);
        });

        verify(concertRepository, never()).save(any());
    }

    @Test
    @DisplayName("공연 수정 성공")
    void 공연_정보_수정_성공() {
        // given
        Long userId = 1L;
        Long concertId = 1L;

        // 사용자 생성
        User user = mock(User.class);

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);

        // 업데이트 요청 생성
        UpdateConcertRequest updateConcertRequest = new UpdateConcertRequest();
        ReflectionTestUtils.setField(updateConcertRequest, "title", "제목2");
        ReflectionTestUtils.setField(updateConcertRequest, "runningTime", 180);
        ReflectionTestUtils.setField(updateConcertRequest, "price", 65000);
        ReflectionTestUtils.setField(updateConcertRequest, "limitAge", 19);
        ReflectionTestUtils.setField(updateConcertRequest, "performanceDate", LocalDate.parse("2025-07-10"));

        // when
        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.findByIdAndWriter(concertId, user)).thenReturn(Optional.of(concert));
        when(concertRepository.save(any(Concert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateConcertResponse response = concertServiceImpl.updateConcert(userId, concertId, updateConcertRequest);

        // then
        verify(concertRepository).save(any(Concert.class));
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("제목2");
    }

    @Test
    @DisplayName("공연 수정 실패")
    void 공연_유효성_부족으로_인한_공연_수정_실패() {
        // given
        Long userId = 1L;
        Long concertId = 1L;

        // 사용자 생성
        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, USER);

        // 업데이트 요청 생성
        UpdateConcertRequest updateConcertRequest = new UpdateConcertRequest();
        ReflectionTestUtils.setField(updateConcertRequest, "title", "제목2");
        ReflectionTestUtils.setField(updateConcertRequest, "runningTime", 180);
        ReflectionTestUtils.setField(updateConcertRequest, "price", 65000);
        ReflectionTestUtils.setField(updateConcertRequest, "limitAge", 19);
        ReflectionTestUtils.setField(updateConcertRequest, "performanceDate", LocalDate.parse("2025-07-10"));

        // when + then
        when(userService.getActiveUserById(userId)).thenReturn(user);
        Assertions.assertThrows(ConcertException.ConcertNotFoundException.class, () -> {
            concertServiceImpl.updateConcert(userId, concertId, updateConcertRequest);
        });

        verify(concertRepository, never()).save(any(Concert.class));
    }

    @Test
    @DisplayName("공연 수정 실패")
    void 논리_삭제_된_공연_수정_실패() {
        // given
        Long userId = 1L;
        Long concertId = 1L;

        // 사용자 생성
        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, ADMIN);

        // 업데이트 요청 생성
        UpdateConcertRequest updateConcertRequest = new UpdateConcertRequest();
        ReflectionTestUtils.setField(updateConcertRequest, "title", "제목2");
        ReflectionTestUtils.setField(updateConcertRequest, "price", 65000);
        ReflectionTestUtils.setField(updateConcertRequest, "performanceDate", LocalDate.parse("2025-07-10"));

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);

        concert.softDelete();

        // when
        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.findByIdAndWriter(concertId, user)).thenReturn(Optional.of(concert));
        assertThrows(ConcertException.ConcertAlreadyDeletedException.class, () -> {
            concertServiceImpl.updateConcert(userId, concertId, updateConcertRequest);
        });

        // then
        verify(concertRepository, never()).save(any(Concert.class));
    }

    @Test
    @DisplayName("공연 단건 조회 성공")
    void 공연_단건_조회_성공() {
        //given
        Long concertId = 1L;

        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, ADMIN);

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);

        // sanity check
        assertFalse(concert.getIsDeleted());

        //when
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));

        FindConcertResponse findConcertResponse = concertServiceImpl.findConcert(concertId);

        //then
        assertNotNull(findConcertResponse);
        assertEquals("제목", findConcertResponse.getTitle());
        assertEquals(180, findConcertResponse.getRunningTime());
        assertEquals(75000, findConcertResponse.getPrice());
        assertEquals(19, findConcertResponse.getLimitAge());
        assertEquals(LocalDate.parse("2025-07-09"), findConcertResponse.getPerformanceDate());
        assertEquals(user.getNickname(), findConcertResponse.getWriter().getNickname());
    }

    @Test
    @DisplayName("존재하지 않는 공연 단건 조회 실패")
    void 공연이_존재하지_않아서_조회_실패() {
        //given
        Long concertId = 1L;

        //when + then
        when(concertRepository.findById(concertId)).thenReturn(Optional.empty());
        ConcertException.ConcertNotFoundException concertNotFoundException = assertThrows(
            ConcertException.ConcertNotFoundException.class, () -> {
                concertServiceImpl.findConcert(concertId);
            });

        assertEquals("공연이 존재하지 않습니다.", concertNotFoundException.getMessage());
    }

    @Test
    @DisplayName("논리 삭제된 공연 단건 조회 실패")
    void 논리_삭제된_공연_단건_조회_실패() {
        //given
        Long concertId = 1L;

        User user = mock(User.class);

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);
        concert.softDelete();

        // sanity check
        assertTrue(concert.getIsDeleted());

        //when + then
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));
        ConcertException.ConcertAlreadyDeletedException concertNotFoundException = assertThrows(
            ConcertException.ConcertAlreadyDeletedException.class, () -> {
                concertServiceImpl.findConcert(concertId);
            });

        assertEquals("이미 삭제된 공연입니다.", concertNotFoundException.getMessage());
    }

    @Test
    @DisplayName("공연 다건 조회 성공")
    void 공연_다건_조회_성공() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        User user = new User("test@naver.com", "닉네임",
            LocalDate.of(2000, 1, 1), "pw1234!", 1000L, USER);

        RegisterConcertRequest request1 = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request1, "title", "제목1");
        ReflectionTestUtils.setField(request1, "runningTime", 180);
        ReflectionTestUtils.setField(request1, "price", 75000);
        ReflectionTestUtils.setField(request1, "limitAge", 19);
        ReflectionTestUtils.setField(request1, "performanceDate", LocalDate.parse("2025-07-09"));

        RegisterConcertRequest request2 = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request2, "title", "제목2");
        ReflectionTestUtils.setField(request2, "runningTime", 200);
        ReflectionTestUtils.setField(request2, "price", 65000);
        ReflectionTestUtils.setField(request2, "limitAge", 20);
        ReflectionTestUtils.setField(request2, "performanceDate", LocalDate.parse("2025-07-10"));
        Concert concert1 = Concert.of(user, request1);
        Concert concert2 = Concert.of(user, request2);

        List<Concert> concertList = Arrays.asList(concert1, concert2);
        Page<Concert> concertPage = new PageImpl<>(concertList, pageable, concertList.size());

        when(concertRepository.findAllByIsDeletedFalse(pageable)).thenReturn(concertPage);

        // when
        Page<FindConcertResponse> concerts = concertServiceImpl.findConcerts(pageable);

        // then
        assertNotNull(concerts);
        assertEquals(2, concerts.getContent().size());

        FindConcertResponse first = concerts.getContent().get(0);
        assertEquals("제목1", first.getTitle());
        assertEquals(180, first.getRunningTime());
        assertEquals(75000, first.getPrice());
        assertEquals("닉네임", first.getWriter().getNickname());

        FindConcertResponse second = concerts.getContent().get(1);
        assertEquals("제목2", second.getTitle());
        assertEquals(200, second.getRunningTime());
        assertEquals(65000, second.getPrice());
        assertEquals("닉네임", second.getWriter().getNickname());

        verify(concertRepository).findAllByIsDeletedFalse(pageable);
    }

    @Test
    @DisplayName("공연 삭제 성공")
    void 공연_삭제_성공() {
        //given
        Long userId = 1L;
        Long concertId = 1L;

        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, ADMIN);

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);

        //when
        assertFalse(concert.getIsDeleted());

        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.findByIdAndWriter(concertId, user)).thenReturn(Optional.of(concert));
        when(concertRepository.save(any(Concert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        concertServiceImpl.deleteConcert(userId, concertId);

        //then
        assertTrue(concert.getIsDeleted());
        verify(concertRepository).save(concert);
    }

    @Test
    @DisplayName("공연 삭제 실패")
    void 작성한_공연_없으므로_공연_삭제_실패() {
        //given
        Long userId = 1L;
        Long concertId = 1L;

        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, ADMIN);

        //when
        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.findByIdAndWriter(concertId, user)).thenReturn(Optional.empty());

        assertThrows(ConcertException.ConcertNotFoundException.class, () -> {
            concertServiceImpl.deleteConcert(userId, concertId);
        });

        //then
        verify(concertRepository, never()).save(any(Concert.class));
    }

    @Test
    @DisplayName("공연 삭제 실패")
    void 작성한_공연_이미_삭제되어_공연_삭제_실패() {
        //given
        Long userId = 1L;
        Long concertId = 1L;

        User user = new User("example@naver.com", "hero123",
            LocalDate.parse("2003-02-18"), "e23fD@fv665", 100000L, ADMIN);

        // 기존 공연 생성
        RegisterConcertRequest request = new RegisterConcertRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "runningTime", 180);
        ReflectionTestUtils.setField(request, "price", 75000);
        ReflectionTestUtils.setField(request, "limitAge", 19);
        ReflectionTestUtils.setField(request, "performanceDate", LocalDate.parse("2025-07-09"));

        Concert concert = Concert.of(user, request);
        concert.softDelete();

        // sanity check
        assertTrue(concert.getIsDeleted());

        //when
        when(userService.getActiveUserById(userId)).thenReturn(user);
        when(concertRepository.findByIdAndWriter(concertId, user)).thenReturn(Optional.of(concert));

        assertThrows(ConcertException.ConcertAlreadyDeletedException.class, () -> {
            concertServiceImpl.deleteConcert(userId, concertId);
        });

        //then
        verify(concertRepository, never()).save(concert);
    }
}