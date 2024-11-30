package edu.dev.review.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import edu.dev.review.dto.ReviewDto;
import edu.dev.review.dto.ReviewFormDto;
import edu.dev.review.entity.Review;

@Component
public class ReviewMapper {

	public ReviewDto mapReviewToDto(Review review) {
		ReviewDto reviewDto = new ReviewDto();

		reviewDto.setId(review.getId().toString());
		reviewDto.setContent(review.getContent());
		reviewDto.setRating(review.getRating());
		reviewDto.setUserId(review.getUser().getId().toString());
		reviewDto.setUserFullname(review.getUser().getFullname());
		reviewDto.setCreatedAt(review.getCreatedAt());
		reviewDto.setEditedAt(review.getEditedAt());

		return reviewDto;
	}

	public Page<ReviewDto> mapPageReviewToPageDtos(Page<Review> pageData, Pageable pageable) {
		List<ReviewDto> reviewDtos = pageData.getContent().stream().map(this::mapReviewToDto).toList();
		return new PageImpl<>(reviewDtos, pageable, pageData.getTotalElements());
	}

	public ReviewFormDto mapReviewToFormDto(Review review) {
		return new ReviewFormDto(review.getContent(), review.getRating());
	}
}
