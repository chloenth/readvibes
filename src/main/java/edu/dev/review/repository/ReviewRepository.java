package edu.dev.review.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.dev.book.entity.Book;
import edu.dev.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

	boolean existsByBookIdAndUserId(UUID bookId, UUID UserId);

	@Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.book.id = :bookId")
	Page<Review> findReviewsWithUsersByBookId(@Param("bookId") UUID bookId, Pageable pageable);
	
	Optional<Review> findByBookIdAndUserId(UUID BookId, UUID UserId);

	Optional<Review> findById(UUID reviewId);
	
	List<Review> findByBook(Book book);
	
	List<Review> findByBookId(UUID bookId);
}
