<div align="center">

```
 ██████╗ ██╗   ██╗████████╗███████╗██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗
██╔═══██╗██║   ██║╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝
██║   ██║██║   ██║   ██║   █████╗  ██████╔╝██████╔╝███████║██████╔╝█████╔╝ 
██║   ██║██║   ██║   ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ 
╚██████╔╝╚██████╔╝   ██║   ███████╗██║  ██║██║     ██║  ██║██║  ██║██║  ██╗
 ╚═════╝  ╚═════╝    ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝
```

</div>

<div align="center">

## 🎪 프로젝트 소개

**OuterPark**는 Redis 분산 락을 활용한 동시성 제어 기반의 콘서트 예약 시스템입니다.

체계적인 예약/결제 프로세스와 안정적인 동시성 처리를 제공하는 백엔드 서비스입니다.

</div>

<div align="center">

### 👥 팀원 소개

 <table>
   <tr>
     <td align="center">
       <a href="https://github.com/DOGYUN0903">
         <img src="https://github.com/DOGYUN0903.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/DOGYUN0903">김도균 (DOGYUN)</a></b></sub><br />
       <sub>Auth & User, Redis 락</sub>
     </td>
     <td align="center">
       <a href="https://github.com/kimyongjun0129">
         <img src="https://github.com/kimyongjun0129.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/kimyongjun0129">김용준 (kimyongjun0129)</a></b></sub><br />
       <sub>Concert</sub>
     </td>
     <td align="center">
       <a href="https://github.com/balsohn">
         <img src="https://github.com/balsohn.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/balsohn">손지호 (balsohn)</a></b></sub><br />
       <sub>Seat</sub>
     </td>
   </tr>
   <tr>
     <td align="center">
       <a href="https://github.com/Zyooon">
         <img src="https://github.com/Zyooon.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/Zyooon">우지운 (Zyooon)</a></b></sub><br />
       <sub>Payment</sub>
     </td>
     <td align="center">
       <a href="https://github.com/kcc5107">
         <img src="https://github.com/kcc5107.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/kcc5107">장경혁 (kcc5107)</a></b></sub><br />
       <sub>Reservation</sub>
     </td>
     <td align="center">
       <a href="https://github.com/GyeongSe99">
         <img src="https://github.com/GyeongSe99.png" width="100px;" alt=""/>
       </a><br />
       <sub><b><a href="https://github.com/GyeongSe99">천세경 (GyeongSe99)</a></b></sub><br />
       <sub>CI/CD</sub>
     </td>
   </tr>
 </table>

</div>

### 📋 주요 기능

#### 🎫 콘서트 예약 시스템

- **동시성 제어**: Redis 분산 락으로 안전한 좌석 예약 처리
- **다중 좌석 예약**: 한 번에 여러 좌석 예약 가능
- **좌석 검증**: 예약 가능 여부 및 중복 예약 방지
- **자연순 정렬**: 좌석 번호 자연순 정렬 (A-1, A-2, A-10, B-1...)

#### 👤 사용자 관리

- **JWT 인증**: 60분 만료 토큰 기반 인증
- **Spring Security**: Filter Chain 기반 보안
- **역할 기반 접근**: USER/ADMIN 권한 분리
- **잔액 관리**: 결제용 가상 잔액 시스템

#### 💳 결제 시스템

- **가상 잔액 결제**: 사용자 잔액 차감 방식
- **트랜잭션 처리**: 결제 실패 시 예약 자동 롤백
- **자동 예약 확정**: 결제 성공 시 예약 상태 변경
- **환불 처리**: 공연일 전까지 환불 가능

#### 📊 관리자 기능

- **콘서트 관리**: 콘서트 CRUD (ADMIN 권한)
- **페이징 지원**: 콘서트 목록 페이징 조회

### 🛠️ 기술 스택

**Backend**

<img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Boot-3.5.3-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 

<img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">

**Database**

<img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">

**Build & Test**

<img src="https://img.shields.io/badge/Gradle-8.14.2-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"> <img src="https://img.shields.io/badge/Mockito-78A641?style=for-the-badge">

**Development Tools**

<img src="https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">

## 🏗️ 아키텍처

```
Controller Layer → Service Layer → Repository Layer → Database
                                  ↓
                             Redis (Lock & Cache)
```

### 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/lockers/outerpark/
│   │   ├── OuterparkApplication.java    # 메인 애플리케이션
│   │   ├── common/                      # 공통 컴포넌트
│   │   │   ├── config/                  # 설정 클래스 (Security, Redis)
│   │   │   ├── dto/                     # 공통 DTO
│   │   │   ├── entity/                  # 기본 엔티티 (BaseEntity)
│   │   │   ├── exception/               # 글로벌 예외 처리
│   │   │   ├── jwt/                     # JWT 인증/인가
│   │   │   └── response/                # 공통 응답 포맷
│   │   └── domain/                      # 도메인별 패키지
│   │       ├── auth/                    # 인증 도메인
│   │       ├── concert/                 # 콘서트 도메인
│   │       ├── lock/                    # 분산 락 도메인 (AOP 기반)
│   │       ├── payment/                 # 결제 도메인
│   │       ├── reservation/             # 예약 도메인
│   │       ├── seat/                    # 좌석 도메인
│   │       └── user/                    # 사용자 도메인
│   └── resources/
│       ├── application.yml              # 메인 설정
│       └── application-test.yml         # 테스트 설정
└── test/                               # 테스트 코드
    └── java/com/lockers/outerpark/
        ├── OuterparkApplicationTests.java
        └── domain/                     # 도메인별 테스트
            ├── auth/service/
            ├── concert/service/
            ├── payment/service/
            ├── reservation/service/
            ├── seat/service/
            └── user/service/
```

### 🚀 시작하기

#### 요구사항

- Java 17 이상
- MySQL 8.0 이상
- Redis (동시성 제어 및 락 관리)

#### 설치 및 실행

```bash
# 저장소 클론
git clone https://github.com/DOGYUN0903/outerpark.git
cd outerpark

# 데이터베이스 설정
# application.yml에서 MySQL 연결 정보 설정

# 애플리케이션 빌드 및 실행
./gradlew bootRun
```

#### 테스트 실행

```bash
# 전체 테스트 실행
./gradlew test

# 특정 도메인 테스트
./gradlew test --tests "*SeatServiceImplTest*"
```

### 📖 API 문서

#### 🔐 Auth (인증)

<details>
<summary><code>🟢 POST /api/auth/signup</code> - 회원가입</summary>

**Request Body:**

| 필드명      | 타입          | 설명     | 필수 |
|----------|-------------|--------|----|
| email    | `String`    | 이메일    | ✅  |
| nickname | `String`    | 사용자 별명 | ✅  |
| password | `String`    | 비밀번호   | ✅  |
| birth    | `LocalDate` | 생년월일   | ✅  |

**Sample Request:**

```json
{
  "email": "example@naver.com",
  "nickname": "jun1",
  "password": "1234",
  "birth": "2001-01-29"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "회원가입에 성공했습니다.",
  "data": {
    "token": "Bearer eyJhbGciOiJIUzI1NiJ9..."
  },
  "timestamp": "2024-03-15T10:30:00Z"
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "이미 존재하는 이메일입니다.",
  "data": null,
  "timestamp": "2025-07-12T01:17:19.9211592"
}
```

**상태 코드:**

- `200 OK`: 회원가입 성공
- `400 Bad Request`: 클라이언트 오류 (중복 이메일/닉네임)

</details>

<details>
<summary><code>🟢 POST /api/auth/signin</code> - 로그인</summary>

**Request Body:**

| 필드명      | 타입       | 설명   | 필수 |
|----------|----------|------|----|
| email    | `String` | 이메일  | ✅  |
| password | `String` | 비밀번호 | ✅  |

**Sample Request:**

```json
{
  "email": "example@naver.com",
  "password": "1234"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "로그인에 성공하였습니다.",
  "data": {
    "token": "Bearer eyJhbGciOiJIUzI1NiJ9..."
  },
  "timestamp": "2025-07-12T01:18:25.1982859"
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "잘못된 비밀번호입니다.",
  "data": null,
  "timestamp": "2025-07-12T01:19:44.7245013"
}
```

**상태 코드:**

- `200 OK`: 로그인 성공
- `400 Bad Request`: 인증 실패

</details>

<details>
<summary><code>🔴 DELETE /api/auth/withdraw</code> - 회원탈퇴</summary>

**Request Body:**

```json
{
  "password": "12Er@45ATa"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "회원 탈퇴에 성공했습니다.",
  "data": null,
  "timestamp": "2024-03-15T10:30:00Z"
}
```

**상태 코드:**

- `200 OK`: 회원 탈퇴 성공
- `400 Bad Request`: 클라이언트 오류

</details>

**주요 기능:**

- JWT 토큰 기반 인증 (60분 만료)
- Spring Security 통합
- 이메일/닉네임 중복 체크
- 안전한 비밀번호 암호화

---

#### 👤 User (사용자)

<details>
<summary><code>🔵 GET /api/users/me</code> - 내 정보 조회</summary>

**Request:** 없음 (JWT 토큰 필요)

**Response Body:**

| 필드명      | 타입          | 설명      |
|----------|-------------|---------|
| id       | `Long`      | 고유 ID   |
| nickname | `String`    | 사용자 닉네임 |
| balance  | `Long`      | 잔액      |
| birth    | `LocalDate` | 생년월일    |
| userRole | `Enum`      | 권한      |

**Sample Response:**

```json
{
  "success": true,
  "message": "회원 조회를 성공적으로 하였습니다.",
  "data": {
    "id": 1,
    "nickname": "kimkimk2im",
    "balance": 100000,
    "birth": "2001-01-29",
    "userRole": "ADMIN"
  },
  "timestamp": "2025-07-12T01:23:03.391035"
}
```

**상태 코드:**

- `200 OK`: 사용자 조회 성공
- `401 UNAUTHORIZED`: 인증 실패

</details>

<details>
<summary><code>🔵 GET /api/users/me/reservations</code> - 내 예약 목록 조회</summary>

**Request Parameters:**

| 필드명  | 타입     | 설명     | 필수 | 기본값 |
|------|--------|--------|----|-----|
| page | `Long` | 페이지 수  | ❌  | 0   |
| size | `Long` | 페이지 크기 | ❌  | 10  |

**Response Body:**

| 필드명           | 타입       | 설명     |
|---------------|----------|--------|
| reservationId | `Long`   | 예매 ID  |
| concertInfo   | `Object` | 콘서트 정보 |
| totalAmount   | `int`    | 결제 금액  |
| seatsInfo     | `Object` | 좌석 정보  |

**Sample Response:**

```json
{
  "success": true,
  "message": "내 예매 목록 조회에 성공하였습니다.",
  "data": {
    "content": [
      {
        "reservationId": 2,
        "concertInfo": {
          "id": 1,
          "title": "팬텀",
          "performanceDate": "2025-07-11"
        },
        "totalAmount": 300000,
        "seatsInfo": {
          "seatId": [
            1,
            2
          ],
          "seatNumber": [
            "A-1",
            "A-2"
          ]
        }
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 20
    },
    "totalElements": 3,
    "totalPages": 1
  },
  "timestamp": "2025-07-11T03:38:13.5901125"
}
```

**상태 코드:**

- `200 OK`: 예매 목록 조회 성공
- `401 UNAUTHORIZED`: 인증 실패

</details>

**주요 기능:**

- 사용자 정보 조회 (이메일, 닉네임, 생년월일, 권한)
- 가상 잔액 관리
- 예약 내역 페이징 조회
- 회원 탈퇴 기능

---

#### 🎪 Concert (콘서트)

<details>
<summary><code>🟢 POST /api/concerts</code> - 콘서트 등록 (ADMIN)</summary>

**Request Body:**

| 필드명             | 타입          | 설명          | 필수 |
|-----------------|-------------|-------------|----|
| title           | `String`    | 콘서트 제목      | ✅  |
| runningTime     | `int`       | 상영 시간       | ✅  |
| price           | `int`       | 공연 가격       | ✅  |
| limitAge        | `int`       | 관람 가능 연령 제한 | ✅  |
| performanceDate | `LocalDate` | 공연 날짜       | ✅  |

**Sample Request:**

```json
{
  "title": "팬텀",
  "runningTime": 150,
  "price": 150000,
  "limitAge": 19,
  "performanceDate": "2025-07-07"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "콘서트가 등록되었습니다.",
  "data": {
    "title": "팬텀",
    "writerId": 1,
    "createdAt": "2025-07-11T13:06:01.716803",
    "updatedAt": "2025-07-11T13:06:01.716803"
  },
  "timestamp": "2025-07-11T13:06:01.7289084"
}
```

**상태 코드:**

- `200 OK`: 콘서트 등록 성공
- `403 Forbidden`: 권한 부족 (ADMIN 권한 필요)

</details>

<details>
<summary><code>🟡 PATCH /api/concerts/{id}</code> - 콘서트 수정 (ADMIN)</summary>

**Request Body:** (모든 필드 선택사항)

| 필드명             | 타입          | 설명          | 필수 |
|-----------------|-------------|-------------|----|
| title           | `String`    | 콘서트 제목      | ❌  |
| runningTime     | `Integer`   | 상영 시간       | ❌  |
| price           | `Integer`   | 공연 가격       | ❌  |
| limitAge        | `Integer`   | 관람 가능 연령 제한 | ❌  |
| performanceDate | `LocalDate` | 공연 날짜       | ❌  |

**Sample Request:**

```json
{
  "title": "팬텀",
  "runningTime": 150,
  "price": 150000,
  "limitAge": 19,
  "performanceDate": "2025-07-07"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "콘서트가 수정되었습니다.",
  "data": {
    "title": "팬텀",
    "writerId": 1,
    "createdAt": "2025-07-11T20:02:36.455891",
    "updatedAt": "2025-07-11T20:02:36.455891"
  },
  "timestamp": "2025-07-12T02:05:09.798243"
}
```

**상태 코드:**

- `200 OK`: 콘서트 수정 성공
- `403 Forbidden`: 권한 부족

</details>

<details>
<summary><code>🔵 GET /api/concerts</code> - 콘서트 목록 조회 (페이징)</summary>

**Request Parameters:**

| 필드명  | 타입     | 설명     | 필수 | 기본값 |
|------|--------|--------|----|-----|
| page | `Long` | 페이지 수  | ❌  | 0   |
| size | `Long` | 페이지 크기 | ❌  | 10  |

**Sample Request:**

```
GET /api/concerts?page=0&size=5
```

**Response Body:**

| 필드명             | 타입          | 설명     |
|-----------------|-------------|--------|
| title           | `String`    | 콘서트 제목 |
| runningTime     | `int`       | 상영시간   |
| price           | `Long`      | 가격     |
| limitAge        | `int`       | 연령 제한  |
| performanceDate | `LocalDate` | 공연 날짜  |

**Sample Response:**

```json
{
  "success": true,
  "message": "콘서트가 조회되었습니다.",
  "data": {
    "content": [
      {
        "title": "팬텀",
        "runningTime": 150,
        "writerId": 1,
        "price": 150000,
        "limitAge": 19,
        "performanceDate": "2025-07-07",
        "updatedAt": "2025-07-12T02:08:42.254206"
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 20
    },
    "totalPages": 1,
    "totalElements": 3
  },
  "timestamp": "2025-07-12T02:37:35.5617391"
}
```

**상태 코드:**

- `200 OK`: 콘서트 조회 성공

</details>

<details>
<summary><code>🔵 GET /api/concerts/{id}</code> - 콘서트 상세 조회</summary>

**Request:** Path Variable

| 변수명        | 타입     | 필수 | 설명     |
|------------|--------|----|--------|
| concert_id | `Long` | ✅  | 콘서트 ID |

**Sample Request:**

```
GET /api/concerts/1
```

**Response Body:**

| 필드명             | 타입              | 설명     |
|-----------------|-----------------|--------|
| title           | `String`        | 콘서트 제목 |
| runningTime     | `Int`           | 상영 시간  |
| price           | `Int`           | 가격     |
| limitAge        | `Int`           | 나이 제한  |
| performanceDate | `LocalDate`     | 공연 날짜  |
| writerId        | `Long`          | 작성자 ID |
| updatedAt       | `LocalDateTime` | 수정 날짜  |

**Sample Response:**

```json
{
  "success": true,
  "message": "콘서트가 조회되었습니다.",
  "data": {
    "title": "팬텀",
    "runningTime": 150,
    "writerId": 1,
    "price": 150000,
    "limitAge": 19,
    "performanceDate": "2025-07-07",
    "updatedAt": "2025-07-12T02:08:42.254206"
  },
  "timestamp": "2025-07-12T02:30:18.273127"
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "공연이 존재하지 않습니다.",
  "data": null,
  "timestamp": "2025-07-12T02:31:41.8588538"
}
```

**상태 코드:**

- `200 OK`: 콘서트 조회 성공
- `404 NOT FOUND`: 존재하지 않는 콘서트 ID

</details>

<details>
<summary><code>🔴 DELETE /api/concerts/{id}</code> - 콘서트 삭제 (ADMIN)</summary>

**Request:** Path Variable

| 변수명        | 타입     | 필수 | 설명     |
|------------|--------|----|--------|
| concert_id | `Long` | ✅  | 콘서트 ID |

**Sample Request:**

```
DELETE /api/concerts/1
```

**Sample Response:**

```json
{
  "success": true,
  "message": "콘서트가 삭제되었습니다.",
  "data": null,
  "timestamp": "2024-03-15T10:30:00Z"
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "콘서트 삭제를 실패했습니다.",
  "data": null,
  "timestamp": "2024-03-15T10:30:00Z"
}
```

**상태 코드:**

- `200 OK`: 콘서트 삭제 성공
- `403 Forbidden`: 권한 부족 (ADMIN 권한 필요)

</details>

**주요 기능:**

- 콘서트 정보: 제목, 상영시간, 가격, 연령제한, 공연일
- 날짜별, 제목 검색 지원
- 페이징 처리
- 관리자 권한 필요 (생성/수정/삭제)

---

#### 🪑 Seat (좌석)

<details>
<summary><code>🔵 GET /api/concerts/{concertId}/seats</code> - 콘서트 좌석 현황 조회</summary>

**Request:** Path Variable

| 변수명       | 타입     | 필수 | 설명     |
|-----------|--------|----|--------|
| concertId | `Long` | ✅  | 콘서트 ID |

**Sample Request:**

```
GET /api/concerts/1/seats
```

**Response Body:**

| 필드명            | 타입       | 설명          |
|----------------|----------|-------------|
| concertId      | `Long`   | 콘서트 ID      |
| totalSeats     | `int`    | 전체 좌석 수     |
| availableSeats | `int`    | 예약 가능한 좌석 수 |
| reservedSeats  | `int`    | 예약된 좌석 수    |
| seats          | `Array`  | 좌석 상세 정보    |
| ㄴ seatId       | `int`    | 좌석 ID       |
| ㄴ seatNumber   | `String` | 좌석 번호       |
| ㄴ status       | `String` | 좌석 상태       |

**Sample Response:**

```json
{
  "success": true,
  "message": "좌석 정보를 조회했습니다.",
  "data": {
    "concertId": 1,
    "totalSeats": 100,
    "availableSeats": 85,
    "reservedSeats": 15,
    "seats": [
      {
        "seatId": 1,
        "seatNumber": "A-1",
        "status": null
      },
      {
        "seatId": 2,
        "seatNumber": "A-2",
        "status": "CONFIRMED"
      },
      {
        "seatId": 3,
        "seatNumber": "A-3",
        "status": null
      },
      {
        "seatId": 4,
        "seatNumber": "A-4",
        "status": "PENDING"
      }
    ]
  },
  "timestamp": "2025-07-09T10:30:00Z"
}
```

**상태 코드:**

- `200 OK`: 좌석 조회 성공
- `404 NOT FOUND`: 콘서트를 찾을 수 없음

</details>

**주요 기능:**

- 좌석 상태: `null`(예약가능), `PENDING`, `CONFIRMED`
- 자연순 정렬 (A-1, A-2, A-10, B-1...)
- 총 좌석 수, 예약 가능 좌석 수, 예약된 좌석 수 통계
- 좌석별 상태 확인

---

#### 📝 Reservation (예약)

<details>
<summary><code>🟢 POST /api/reservations/concerts/{concertId}</code> - 좌석 예약</summary>

**Request:** Path Variable + Body

| 변수명       | 타입     | 필수 | 설명     |
|-----------|--------|----|--------|
| concertId | `Long` | ✅  | 콘서트 ID |

**Request Body:**

| 필드명     | 타입           | 설명       | 필수 | 제약조건                  |
|---------|--------------|----------|----|-----------------------|
| seatIds | `List<Long>` | 좌석 ID 목록 | ✅  | `seatIds.size() <= 2` |

**Sample Request:**

```json
{
  "seatIds": [
    10,
    11
  ]
}
```

**Response Body:**

| 필드명               | 타입             | 설명     |
|-------------------|----------------|--------|
| reservationId     | `Long`         | 예매 ID  |
| seatId            | `List<Long>`   | 좌석 ID  |
| userId            | `Long`         | 유저 ID  |
| reservationNumber | `List<String>` | 예매 번호  |
| count             | `int`          | 티켓 수량  |
| status            | `Enum`         | 예매 상태  |
| totalAmount       | `int`          | 결제 금액  |
| concertInfo       | `Object`       | 콘서트 정보 |
| reservedAt        | `LocalDate`    | 예매일    |

**Sample Response:**

```json
{
  "success": true,
  "message": "공연 예매에 성공하였습니다.",
  "data": {
    "reservationId": 5,
    "seatId": [
      3,
      4
    ],
    "userId": 1,
    "reservationNumber": [
      "T000211A-3",
      "T000211A-4"
    ],
    "count": 2,
    "status": "PENDING",
    "totalAmount": 300000,
    "concertInfo": {
      "id": 2,
      "title": "팬텀",
      "performanceDate": "2025-07-11"
    },
    "reservedAt": "2025-07-11"
  },
  "timestamp": "2025-07-11T03:28:12.4575854"
}
```

**Error Responses:**

```json
{
  "success": false,
  "message": "공연이 존재하지 않습니다.",
  "data": null,
  "timestamp": "2025-07-11T12:38:44.0931454"
}
```

```json
{
  "success": false,
  "message": "이미 예약된 좌석입니다.",
  "data": null,
  "timestamp": "2025-07-11T12:41:10.8914228"
}
```

```json
{
  "success": false,
  "message": "예매 좌석 수는 1에서 2 사이여야 합니다",
  "data": null,
  "timestamp": "2025-07-11T13:07:44.434372"
}
```

**상태 코드:**

- `200 OK`: 예매 성공
- `400 BAD REQUEST`: 잘못된 좌석 개수 또는 해당 콘서트에 속하지 않는 좌석
- `401 UNAUTHORIZED`: 인증 실패
- `404 NOT FOUND`: 유효하지 않은 콘서트 또는 좌석
- `409 CONFLICT`: 이미 예매된 좌석

</details>

<details>
<summary><code>🔴 DELETE /api/reservations/{reservationId}</code> - 예약 취소</summary>

**Request:** Path Variable

| 변수명           | 타입     | 필수 | 설명    |
|---------------|--------|----|-------|
| reservationId | `Long` | ✅  | 예매 ID |

**Sample Request:**

```
DELETE /api/reservations/5
```

**Sample Response:**

```json
{
  "success": true,
  "message": "예매를 취소하였습니다.",
  "data": null,
  "timestamp": "2025-06-26T10:30:00Z"
}
```

**상태 코드:**

- `200 OK`: 예매 취소 성공
- `401 UNAUTHORIZED`: 인증 실패
- `404 NOT FOUND`: 유효하지 않은 예매

</details>

**주요 기능:**

- **1인당 최대 2매** 예약 제한
- 다중 좌석 동시 예약 가능
- 예약번호 자동 생성: `T{콘서트ID:4자리}{공연일:2자리}{좌석번호}`
- Redis 분산 락으로 동시성 제어
- 예약 상태: `PENDING` → `CONFIRMED` → `CANCELLED`
- 중복 예약 방지 (같은 공연에 PENDING 예약 존재 시 불가)

---

#### 💳 Payment (결제)

<details>
<summary><code>🟢 POST /api/payments/concerts/{concertId}</code> - 결제 생성</summary>

**Request:** Path Variable + Body

| 변수명       | 타입     | 필수 | 설명    |
|-----------|--------|----|-------|
| concertId | `Long` | ✅  | 공연 ID |

**Request Body:**

| 필드명    | 타입       | 설명                                       | 필수 |
|--------|----------|------------------------------------------|----|
| method | `String` | 결제 방식 (`CARD`, `BANK_TRANSFER`)          | ✅  |
| status | `String` | 결제 상태 (`SUCCESS`, `FAILED`, `CANCELLED`) | ✅  |

**Sample Request:**

```json
{
  "method": "CARD",
  "status": "SUCCESS"
}
```

**Sample Response:**

```json
{
  "success": true,
  "message": "결제 내역이 생성 되었습니다.",
  "data": {
    "id": 44
  },
  "timestamp": "2025-07-12T01:46:34.8369814"
}
```

**Error Responses:**

```json
{
  "success": false,
  "message": "예매 상태가 유효하지 않습니다.",
  "data": null,
  "timestamp": "2025-07-11T13:39:16.6810585"
}
```

```json
{
  "success": false,
  "message": "결제 금액이 부족합니다.",
  "data": null,
  "timestamp": "2025-07-12T01:45:37.0038738"
}
```

**상태 코드:**

- `201 CREATED`: 결제 결과 저장 성공
- `400 BAD REQUEST`: DB 저장 실패
- `404 NOT FOUND`: 예약 내역 없음

</details>

<details>
<summary><code>🔵 GET /api/payments/{paymentId}</code> - 결제 조회</summary>

**Request:** Path Variable

| 변수명       | 타입     | 필수 | 설명    |
|-----------|--------|----|-------|
| paymentId | `Long` | ✅  | 결제 ID |

**Response Body:**

| 필드명           | 타입       | 설명          |
|---------------|----------|-------------|
| id            | `Long`   | 생성된 결제 ID   |
| reservationId | `Long`   | 예약 ID       |
| totalAmount   | `int`    | 결제 금액       |
| method        | `String` | 결제 수단       |
| status        | `String` | 결제 상태       |
| createdAt     | `String` | 결제 완료 시각    |
| updatedAt     | `String` | 결제 정보 갱신 시각 |

**Sample Response:**

```json
{
  "success": true,
  "message": "결제 정보를 조회했습니다.",
  "data": {
    "id": 1,
    "reservationId": 4,
    "totalAmount": 15000,
    "method": "CARD",
    "status": "SUCCESS",
    "createdAt": "2025-07-11T15:14:46.698896",
    "updatedAt": "2025-07-11T15:14:46.698896"
  },
  "timestamp": "2025-07-11T15:21:26.2436877"
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "잘못된 결제 정보입니다.",
  "data": null,
  "timestamp": "2025-07-11T15:21:35.3937018"
}
```

**상태 코드:**

- `200 OK`: 결제 결과 조회 성공
- `400 BAD REQUEST`: DB 조회 실패

</details>

<details>
<summary><code>🟡 PATCH /api/payments/{paymentId}</code> - 결제 취소 (환불)</summary>

**Request:** Path Variable

| 변수명       | 타입     | 필수 | 설명    |
|-----------|--------|----|-------|
| paymentId | `Long` | ✅  | 결제 ID |

**Sample Response:**

```json
{
  "success": true,
  "message": "결제가 취소되었습니다.",
  "data": null,
  "timestamp": "2025-07-09T11:00:00Z"
}
```

**Error Responses:**

```json
{
  "success": false,
  "message": "잘못된 결제 정보입니다.",
  "data": null,
  "timestamp": "2025-07-12T01:48:08.0253004"
}
```

```json
{
  "success": false,
  "message": "취소 가능한 기간이 지났습니다.",
  "data": null,
  "timestamp": "2025-07-12T01:48:32.0525942"
}
```

**상태 코드:**

- `204 NO CONTENT`: 결제 취소 성공
- `400 BAD REQUEST`: DB 조회 실패, 처리 불가 상태

</details>

**주요 기능:**

- 가상 잔액 기반 결제
- 결제 상태: `SUCCESS`, `FAILED`, `CANCELLED`
- 결제 성공 시 예약 자동 확정
- 공연일 전까지 환불 가능
- 트랜잭션 처리: 결제 실패 시 예약 자동 롤백
- 결제 금액과 예약 금액 정합성 검증

### 🧪 테스트

#### 구현된 테스트

- **단위 테스트**: 각 도메인별 Service 층 테스트
- **Mock 테스트**: 외부 의존성 격리 테스트
- **비즈니스 로직 테스트**: 핵심 로직 검증

#### 주요 테스트 시나리오

- **Redis 분산 락**: 동시성 제어 테스트
- **좌석 정렬 알고리즘**: 자연순 정렬 테스트
- **예약 프로세스**: 예약 생성/취소 테스트
- **결제 트랜잭션**: 결제 성공/실패 시나리오
- **JWT 인증**: 토큰 검증 테스트

### 🔧 개발 가이드

#### 커밋 컨벤션

```
feat: 새로운 기능 추가
fix: 버그 수정
refactor: 코드 리팩토링
test: 테스트 코드 추가/수정
docs: 문서 수정
style: 코드 스타일 수정
```

#### 브랜치 전략

- `main`: 운영 환경 브랜치
- `dev`: 개발 환경 브랜치
- `feat/*`: 기능 개발 브랜치

### 📞 문의

프로젝트에 대한 질문이나 제안사항이 있으시면 언제든지 [연락](https://github.com/DOGYUN0903/outerpark/issues) 주세요!

---

⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!
