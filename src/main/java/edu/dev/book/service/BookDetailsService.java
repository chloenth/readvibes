package edu.dev.book.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import edu.dev.book.dto.BookDto;
import edu.dev.book.entity.Book;
import edu.dev.book.exception.BookNotFoundException;
import edu.dev.book.mapper.BookMapper;
import edu.dev.book.repository.BookRepository;
import edu.dev.review.dto.ReviewDto;
import edu.dev.review.dto.ReviewFormDto;
import edu.dev.review.exception.DuplicateReviewException;
import edu.dev.review.service.ReviewService;

@Service
public class BookDetailsService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReviewService reviewService;

	private final BookMapper bookMapper;

	public BookDetailsService(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	public Book getBookById(String bookId) {
		return bookRepository.findById(UUID.fromString(bookId))
				.orElseThrow(() -> new BookNotFoundException("Book not found for ID: " + bookId));
	}

	public BookDto getBookDetailsDto(String bookId) {
		Book book = getBookById(bookId);
		BookDto bookDto = bookMapper.mapBookToDto(book);

		return bookDto;
	}
	
	// fetch review list with pagination
	public Page<ReviewDto> getReviewList(String bookId, int pageNumber) {
		return reviewService.getPageReviewsByBookId(bookId, pageNumber);
	}
	
	// check if user has reviewed on the specific book
	public boolean hasUserReviewedBook(String bookId, String userId) {
		return reviewService.hasUserReviewedBook(bookId, userId);
	}

	// add new review to book
	public void addReviewToBookByUser(ReviewFormDto addReviewDto, String bookId, String userId) {
		if (hasUserReviewedBook(bookId, userId)) {
			throw new DuplicateReviewException("A review by this user ID " + userId.toString() + " for this book ID "
					+ bookId + " already exists.");
		}

		Book book = getBookById(bookId);
		reviewService.addReviewToBookByUser(addReviewDto, book, userId);
		
		updateAverageRating(book);
	}
	
	public String getReviewIdByBookIdAndUserId(String bookId, String userId) {
		return reviewService.getReviewIdByBookIdAndUserId(bookId, userId);
	}
	
	public ReviewFormDto getReviewFormDtoById(String reviewId) {
		return reviewService.getReviewFormDtoById(reviewId);
	}
	
	// update review
	public void updateReview(String reviewId, ReviewFormDto reviewFormDto, String bookId) {
		reviewService.updateReview(reviewId, reviewFormDto);
		
		Book book = getBookById(bookId);
		updateAverageRating(book);
	}
	
	public void removeReview(String reviewId) {
		reviewService.removeReview(reviewId);
	}
	
	private void updateAverageRating(Book book) {
		BigDecimal averageRating = reviewService.calculateAverageRating(book);
		book.setAverageRating(averageRating);
		bookRepository.save(book);
	}
}
