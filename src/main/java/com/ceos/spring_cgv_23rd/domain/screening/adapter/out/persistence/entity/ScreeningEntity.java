package com.ceos.spring_cgv_23rd.domain.screening.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import com.ceos.spring_cgv_23rd.domain.movie.adapter.out.persistence.entity.MovieEntity;
import com.ceos.spring_cgv_23rd.domain.theater.adapter.out.persistence.entity.HallEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "screening",
	indexes = {
		@Index(
			name = "idx_screening_hall_start_at",
			columnList = "hall_id, start_at"
		)
	}
)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScreeningEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "screening_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", nullable = false)
	private MovieEntity movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id", nullable = false)
	private HallEntity hall;

	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@Column(name = "total_seats", nullable = false)
	private Integer totalSeats;

	@Column(name = "remaining_seats", nullable = false)
	private Integer remainingSeats;

	@Column(name = "price", nullable = false)
	private Integer price;
}
