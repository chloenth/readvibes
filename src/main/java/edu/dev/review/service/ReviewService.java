package edu.dev.review.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.dev.auth.entity.User;
import edu.dev.auth.service.UserService;
import edu.dev.book.entity.Book;
import edu.dev.review.dto.ReviewDto;
import edu.dev.review.dto.ReviewFormDto;
import edu.dev.review.entity.Review;
import edu.dev.review.exception.ReviewNotFoundException;
import edu.dev.review.mapper.ReviewMapper;
import edu.dev.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserService userService;

	private final ReviewMapper reviewMapper;

	public static final int PAGE_SIZE = 5;

	public ReviewService(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}

	public void addReviewToBookByUser(ReviewFormDto reviewFormDto, Book book, String userId) {
		User user = userService.getUserById(userId);

		Review review = new Review();
		review.setBook(book);
		review.setUser(user);
		review.setContent(reviewFormDto.getContent());
		review.setRating(reviewFormDto.getRating());

		reviewRepository.save(review);
	}

	// fetch review list with pagination
	public Page<ReviewDto> getPageReviewsByBookId(String bookId, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Review> pageData = reviewRepository.findReviewsWithUsersByBookId(UUID.fromString(bookId), pageable);

		return reviewMapper.mapPageReviewToPageDtos(pageData, pageable);
	}

	public boolean hasUserReviewedBook(String bookId, String userId) {
		return reviewRepository.existsByBookIdAndUserId(UUID.fromString(bookId), UUID.fromString(userId));
	}

	public ReviewFormDto getReviewFormDtoById(String reviewId) {
		Review review = getReviewById(reviewId);
		return reviewMapper.mapReviewToFormDto(review);
	}

	public void updateReview(String reviewId, ReviewFormDto reviewFormDto) {
		Review review = getReviewById(reviewId);
		review.setContent(reviewFormDto.getContent());
		review.setRating(reviewFormDto.getRating());

		reviewRepository.save(review);
	}

	@Transactional
	public void removeReview(String reviewId) {
		Review review = getReviewById(reviewId);

		User user = review.getUser();
		user.getReviews().remove(review);
		review.setUser(null);

		Book book = review.getBook();
		book.getReviews().remove(review);
		review.setBook(null);
	}

	public BigDecimal calculateAverageRating(Book book) {
		List<Review> reviews = reviewRepository.findByBook(book);

		if (reviews.isEmpty()) {
			return BigDecimal.ZERO;
		}

		BigDecimal total = reviews.stream().map(review -> new BigDecimal(review.getRating())).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		BigDecimal averageRating = total.divide(new BigDecimal(reviews.size()), 1, RoundingMode.HALF_UP);

		return averageRating;
	}

	public String getReviewIdByBookIdAndUserId(String bookId, String userId) {
		Optional<Review> review = reviewRepository.findByBookIdAndUserId(UUID.fromString(bookId),
				UUID.fromString(userId));

		if (review.isEmpty()) {
			return null;
		}

		return review.get().getId().toString();
	}

	private Review getReviewById(String reviewId) {
		return reviewRepository.findById(UUID.fromString(reviewId))
				.orElseThrow(() -> new ReviewNotFoundException("Review not found for ID: " + reviewId));
	}
}
