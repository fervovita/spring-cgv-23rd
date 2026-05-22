# 트랜잭션 전파

**'이미 트랜잭션이 진행중일 때 추가 트랜잭션 진행을 어떻게 할지 결정하는 것'**

## 📌 트랜잭션 종류

트랜잭션 안에서 다른 트랜잭션이 실행되기 때문에 스프링은 이를 구분하기 위해 트랜잭션은 **물리 트랜잭션**과 **논리 트랜잭션**으로 구분한다.

- **물리 트랜잭션**: 실제 데이터베이스에 적용되는 트랜잭션으로, 커넥션을 통해 커밋/롤백하는 단위
- **논리 트랜잭션**: 스프링이 트랜잭션 매니저를 통해 트랜잭션을 처리하는 단위

**트랜잭션 원칙**

- 원칙1: 모든 논리 트랜잭션이 커밋되어야 물리 트랜잭션이 커밋됨
- 원칙2: 하나의 논리 트랜잭션이라도 롤백되면 물리 트랜잭션은 롤백됨

## 📌 스프링 트랜잭션 전파 속성

#### REQUIRED (default)

- 현재 진행 중인 트랜잭션이 있으면 해당 트랜잭션에 참여하고, 없으면 새로운 트랜잭션을 시작

#### REQUIRES_NEW

- 항상 새로운 트랜잭션을 시작. 현재 진행 중인 트랜잭션이 있더라도 일시중지 시키고 새 트랜잭션을 시작

#### SUPPORTS

- 현재 진행 중인 트랜잭션이 있으면 그 트랜잭션에 참여하고, 없으면 트랜잭션 없이 실행

#### MANDATORY

- 반드시 현재 진행 중인 트랜잭션이 있어함. 없으면 예외 발생

#### NOT_SUPPORTED

- 트랜잭션을 지원하지 않는 환경에서 실행해야 할 때 사용. 현재 진행 중인 트랜잭션이 있으면 일시 중단

#### NEVER

- 트랜잭션을 사용하지 않아야 함. 현재 진행 중인 트랜잭션이 있으면 예외 발생

#### NESTED

- 현재 진행 중인 트랜잭션이 있으면 중첩된 트랜잭션을 시작. 중첩된 트랜잭션은 외부 트랜잭션에 롤백되지 않는 독립적인 커밋이나 롤백을 가질 수 있음

---

# 인덱스 종류

인덱스는 데이터의 검색 속도를 향상시키기 위해 특정 컬럼의 데이터를 정렬하여 별도의 공간에 저장하는 자료구조이다.   
해당 컬럼의 데이터들을 정렬하여 별도의 공간에 데이터의 ROW_ID와 함께 저장한다.

#### B-Tree Index

- **가장 보편적인 인덱스**: RDBMS(관계형 데이터베이스)에서 기본적으로 생성되는 인덱스 구조이다.
- **균형 트리 구조**: 데이터를 트리 형태로 정렬하여, 루트 노드에서 리프 노드까지의 깊이가 항상 일정하게 유지되도록 관리한다.


- 👍 장점: 정확한 일치(=) 검색뿐만 아니라, 부등호(<, >)나 BETWEEN을 활용한 범위 검색에 모두 뛰어난 성능을 보장한다.
- 👎 단점: 데이터의 삽입, 삭제, 수정 시 트리의 균형을 맞추기 위한 추가 연산(Tree Rebalancing)이 발생하여 잦은 쓰기 작업 시 성능이 저하될 수 있다.

#### Hash Index

- **해시 함수 활용**: 컬럼의 값을 해시 함수에 넣어 나온 결과(해시값)를 이용해 데이터의 위치를 찾니다.


- 👍 장점: 해시 함수를 통해 데이터 위치를 직접 계산하므로, 정확한 일치(=) 검색에서 B-Tree보다 훨씬 빠른 성능을 낸다.
- 👎 단점: 데이터가 정렬되어 저장되지 않으므로, 부등호(<, >)나 부분 일치(LIKE) 같은 범위 검색에는 전혀 사용할 수 없다.

#### Unique Index

- **고유성 보장**: 인덱스가 적용된 컬럼에 중복된 값이 들어올 수 없도록 강제한다.
- **자동 생성**: 일반적으로 테이블의 기본키(Primary Key)나 Unique 제약조건을 설정할 때 자동으로 생성된다.


- 👍 장점: 데이터의 무결성을 보장(중복 데이터 방지)함과 동시에 검색 속도를 향상시킨다.
- 👎 단점: 새로운 데이터가 삽입되거나 수정될 때마다 중복 검사를 반드시 수행해야 하므로 일반 인덱스보다 쓰기 성능이 약간 떨어진다.

#### Composite Index

- **여러 컬럼을 하나로**: 2개 이상의 컬럼을 조합하여 생성한 인덱스이다.
- **순서가 핵심**: 인덱스를 구성하는 컬럼의 배치 순서가 쿼리 성능에 절대적인 영향을 미친다.


- 👍 장점: 여러 컬럼을 동시에 조건으로 거는 WHERE 절이나 정렬(ORDER BY) 시 매우 뛰어난 검색 성능을 제공한다.
- 👎 단점: 인덱스의 용량이 커지며, 컬럼의 배치 순서가 잘못되거나 인덱스의 선행 컬럼이 쿼리 조건에서 빠지면 인덱스를 전혀 타지 못하는 등 사용 제약이 크다.

#### Full-Text Index

- **긴 텍스트 검색 최적화**: 게시글 본문, 기사, 설명 등 길이가 긴 텍스트 데이터 내에서 특정 단어나 문장을 빠르게 찾기 위한 인덱스이다.
- **동작 원리**: 텍스트를 형태소 분석이나 N-gram 방식으로 분해하여 단어(키워드) 단위로 인덱싱한다.


- 👍 장점: 일반적인 LIKE '%단어%' 검색으로는 처리하기 힘든 대용량 텍스트 데이터에서 특정 단어를 매우 빠르게 검색할 수 있다.
- 👎 단점: 형태소 분석 및 인덱싱 과정 때문에 데이터를 삽입/수정할 때 많은 시간과 리소스가 소모되며, 인덱스가 차지하는 용량도 상당히 크다.

#### Clustered Index

- **물리적 정렬 기준**: 테이블의 실제 데이터(레코드) 자체를 인덱스의 순서대로 물리적으로 정렬하여 저장한다.
- **단 하나만 존재**: 데이터베이스 테이블당 오직 1개만 생성할 수 있으며, 보통 Primary Key가 이 역할을 한다


- 👍 장점: 실제 데이터가 정렬되어 모여 있기 때문에, 범위 검색이나 다량의 순차적 데이터를 읽어올 때 압도적으로 빠른 성능을 보여준다.
- 👎 단점: 중간에 새로운 데이터가 삽입되거나 값이 수정될 경우, 실제 물리적인 데이터의 이동 및 재정렬(Page Split)이 발생하여 쓰기 성능 부하가 매우 크다.

#### Non-Clustered Index

- **논리적 정렬 (별도 공간)**: 실제 데이터의 물리적인 순서는 그대로 둔 채, 별도의 공간에 인덱스(정렬된 키값)와 해당 데이터의 위치 정보(ROW_ID)를 저장한다.
- **여러 개 생성 가능**: 한 테이블에 다양한 검색 조건을 위해 여러 개의 인덱스를 만들 수 있다.


- 👍 장점: 실제 데이터의 물리적 정렬에 영향을 주지 않으므로, 다양한 컬럼에 대해 유연하게 검색 성능을 향상시킬 수 있다.
- 👎 단점: 인덱스를 찾은 후 실제 데이터를 가져오기 위해 테이블(또는 Clustered Index)에 한 번 더 접근해야 하므로, Clustered Index보다는 검색 속도가 느리다.

#### Bitmap Index

- **비트 배열 활용**: 컬럼의 데이터를 0과 1의 비트(Bit) 배열로 변환하여 저장한다.
- **사용처**: 성별(남/여), 결혼 여부 등 값의 종류가 적은 컬럼에 생성한다.


- 👍 장점: 매우 적은 저장 공간을 차지하며, 여러 컬럼의 조건이 결합된 복잡한 논리 연산(AND, OR)에서 엄청나게 빠른 성능을 낸다.
- 👎 단점: 레코드 하나가 변경(INSERT, UPDATE)될 때마다 관련된 전체 비트맵을 수정하고 락(Lock)을 걸어야 하므로, 동시성 트랜잭션이 잦은 환경에서는 치명적인 성능 저하를 유발한다.

---

# 📌 성능 최적화

### ✅ ScreeningSeatEntity

```bash
EXPLAIN ANALYZE
SELECT rs.seat_id
FROM reservation_seat rs
         JOIN reservation r ON rs.reservation_id = r.reservation_id
WHERE r.screening_id = 1
  AND r.status = 'COMPLETED';
```

```bash
-> Nested loop inner join  (cost=38.1 rows=84) (actual time=0.0539..0.238 rows=171 loops=1)
    -> Filter: (r.status = 'COMPLETED')  (cost=8.69 rows=84) (actual time=0.035..0.0562 rows=84 loops=1)
        -> Covering index lookup on r using idx_reservation_screening_status (screening_id = 1, status = 'COMPLETED')  (cost=8.69 rows=84) (actual time=0.0336..0.0466 rows=84 loops=1)
    -> Index lookup on rs using FKmppl4hty8sagbei7ywefv0qxy (reservation_id = r.reservation_id)  (cost=0.251 rows=1) (actual time=0.0018..0.00199 rows=2.04 loops=84)
```

이를 최적화 하기 위해 `Composite Index`를 사용.

```
@Table(
	name = "reservation_seat",
	indexes = {
		@Index(
			name = "idx_reservation_seat_reservation_id_seat_id",
			columnList = "reservation_id, seat_id"
		)
	}
)
```

```bash
-> Nested loop inner join  (cost=79.1 rows=177) (actual time=0.309..0.766 rows=171 loops=1)
    -> Filter: (r.status = 'COMPLETED')  (cost=8.69 rows=84) (actual time=0.167..0.256 rows=84 loops=1)
        -> Covering index lookup on r using idx_reservation_screening_status (screening_id = 1, status = 'COMPLETED')  (cost=8.69 rows=84) (actual time=0.164..0.218 rows=84 loops=1)
    -> Covering index lookup on rs using idx_reservation_seat_reservation_id_seat_id (reservation_id = r.reservation_id)  (cost=0.63 rows=2.11) (actual time=0.00462..0.00561 rows=2.04 loops=84)
```

초기 테스트 시 복합 인덱스를 추가했음에도 오히려 실행 시간이 증가하는 현상이 발생했다.   
이는 데이터가 너무 적어 인덱스를 탐색하는 오버헤드가 부각되어 실행 시간이 증가했다고 판단했다.  
따라서, 인덱스의 실질적인 성능 개선 효과를 검증하기 위해 충분한 더미 데이터를 적재한 후 성능을 재측정했다.

다시 데이터를 추가하고 before/after를 비교해보자.

**before**

```
-> Nested loop inner join  (cost=90598 rows=59410) (actual time=4.33..218 rows=32046 loops=1)
    -> Table scan on rs  (cost=20251 rows=200991) (actual time=3.78..72.7 rows=201126 loops=1)
    -> Filter: ((r.`status` = 'COMPLETED') and (r.screening_id = 1))  (cost=0.25 rows=0.296) (actual time=656e-6..667e-6 rows=0.159 loops=201126)
        -> Single-row index lookup on r using PRIMARY (reservation_id = rs.reservation_id)  (cost=0.25 rows=1) (actual time=504e-6..522e-6 rows=1 loops=201126)
```

**after**

```
-> Nested loop inner join  (cost=16055 rows=56627) (actual time=0.0826..31.2 rows=32046 loops=1)
    -> Filter: (r.status = 'COMPLETED')  (cost=2960 rows=29448) (actual time=0.0506..5.76 rows=16067 loops=1)
        -> Covering index lookup on r using idx_reservation_screening_status (screening_id = 1, status = 'COMPLETED')  (cost=2960 rows=29448) (actual time=0.0493..3.38 rows=16067 loops=1)
    -> Covering index lookup on rs using idx_reservation_seat_reservation_id_seat_id (reservation_id = r.reservation_id)  (cost=0.252 rows=1.92) (actual time=0.0011..0.00142 rows=1.99 loops=16067)
```

|            단계             | actual time |                                        핵심 동작                                        |
|:-------------------------:|:-----------:|:-----------------------------------------------------------------------------------:|
| Before (covering index O) |    218ms    | Table scan on rs (200,991 rows 풀스캔) → 각 row마다 reservation PK lookup × 201,126 loops |
| After (covering index X)  |   31.2ms    |               Covering index lookup으로 reservation → rs 순회, 테이블 액세스 0                |

Composite Index를 이용해 Covering Index를 적용함으로써 reservation_seat 테이블에 대한 Full Table Scan과 불필요한 데이터 블록 접근을 완전히 제거했다.   
그 결과 조회 성능이 약 7배 향상되었다.

### ✅ ScreeningEntity

```bash
EXPLAIN ANALYZE
SELECT s.*
FROM screening s
         JOIN hall h ON s.hall_id = h.hall_id
WHERE h.theater_id = 1
  AND CAST(s.start_at AS date) = '2026-05-21'
ORDER BY s.movie_id, s.start_at;
```

```bash
-> Sort: s.movie_id, s.start_at  (actual time=152..154 rows=42546 loops=1)
    -> Stream results  (cost=115 rows=326) (actual time=0.878..135 rows=42546 loops=1)
        -> Nested loop inner join  (cost=115 rows=326) (actual time=0.873..127 rows=42546 loops=1)
            -> Covering index lookup on h using UKlc574fxibgtbl4yu6k223iyru (theater_id = 1)  (cost=0.968 rows=7) (actual time=0.0424..0.0497 rows=7 loops=1)
            -> Filter: (cast(s.start_at as date) = '2026-05-21')  (cost=12.3 rows=46.5) (actual time=0.217..18 rows=6078 loops=7)
                -> Index lookup on s using FKmabwib04cmaxjgf252iimc18e (hall_id = h.hall_id)  (cost=12.3 rows=46.5) (actual time=0.215..17.4 rows=17156 loops=7)
```

`WHERE 절`에서 컬럼(start_at)에 `CAST 함수`를 씌우면서 가공이 발생했다.     
이로 인해 인덱스를 정상적으로 활용하지 못하고, `hall_id`로 매칭된 17,156건의 데이터를 모두 메모리로 가져온 뒤 DB 엔진에서 후필터링을 수행했다.  
CAST 함수 사용으로 인한 풀 스캔을 방지하기 위해, 컬럼을 원형 그대로 유지하는 범위 검색 형태로 쿼리를 수정했다.

```
EXPLAIN ANALYZE
SELECT s.*
FROM screening s
         JOIN hall h ON s.hall_id = h.hall_id
WHERE h.theater_id = 1
  AND s.start_at >= '2026-05-21 00:00:00'
  AND s.start_at <  '2026-05-22 00:00:00'
ORDER BY s.movie_id, s.start_at;
```

```bash
-> Sort: s.movie_id, s.start_at  (actual time=117..119 rows=42546 loops=1)
    -> Stream results  (cost=115 rows=36.2) (actual time=0.351..103 rows=42546 loops=1)
        -> Nested loop inner join  (cost=115 rows=36.2) (actual time=0.346..94.7 rows=42546 loops=1)
            -> Covering index lookup on h using UKlc574fxibgtbl4yu6k223iyru (theater_id = 1)  (cost=0.968 rows=7) (actual time=0.0587..0.0663 rows=7 loops=1)
            -> Filter: ((s.start_at >= TIMESTAMP'2026-05-21 00:00:00') and (s.start_at < TIMESTAMP'2026-05-22 00:00:00'))  (cost=11.7 rows=5.17) (actual time=0.0845..13.3 rows=6078 loops=7)
                -> Index lookup on s using FKmabwib04cmaxjgf252iimc18e (hall_id = h.hall_id)  (cost=11.7 rows=46.5) (actual time=0.081..12.5 rows=17156 loops=7)
```

CAST 연산 비용이 사라져 실행 시간은 119ms로 단축되었으나,   
적절한 인덱스가 없어 여전히 hall_id로 전체 데이터를 스토리지 엔진에서 끌어온 뒤 start_at을 후필터링(17,156건 → 6,078건)하는 문제가 남아있었다.

```bash
@Table(
	name = "screening",
	indexes = {
		@Index(
			name = "idx_screening_hall_start_at",
			columnList = "hall_id, start_at"
		)
	}
)
```

```bash
-> Sort: s.movie_id, s.start_at  (actual time=98.4..101 rows=42546 loops=1)
    -> Stream results  (cost=101588 rows=107434) (actual time=8.31..84.7 rows=42546 loops=1)
        -> Nested loop inner join  (cost=101588 rows=107434) (actual time=8.3..74.8 rows=42546 loops=1)
            -> Covering index lookup on h using UKlc574fxibgtbl4yu6k223iyru (theater_id = 1)  (cost=0.968 rows=7) (actual time=0.061..0.0684 rows=7 loops=1)
            -> Index lookup on s using idx_screening_hall_start_at (hall_id = h.hall_id), with index condition: ((s.start_at >= TIMESTAMP'2026-05-21 00:00:00') and (s.start_at < TIMESTAMP'2026-05-22 00:00:00'))  (cost=721 rows=15348) (actual time=4.08..10.4 rows=6078 loops=7)
```

조회 조건에 맞춰 `hall_id`와 `start_at`을 묶은 Composite Index를 생성하여 적용했다.

|  단계	  | actual time |   	한 loop당 fetch row    |                              	핵심 동작                              |
|:-----:|:-----------:|:-----------------------:|:----------------------------------------------------------------:|
| 초기 상황 |   	154ms	   | 17,156 → filter → 6,078 |                    	CAST 함수 때문에 풀로 가져온 뒤 후필터                     |
| 1 단계  |    119ms    | 17,156 → filter → 6,078 |          CAST 비용은 사라졌지만 여전히 hall_id로만 끌어옴, start_at 후필터          |
| 2 단계  |    101ms    |          6,078          | idx_screening_hall_start_at로 hall_id 등호 + start_at 범위까지 인덱스에서 처리 |
