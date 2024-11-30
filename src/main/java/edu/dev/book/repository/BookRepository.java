package edu.dev.book.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.dev.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

	@Query("SELECT b FROM Book b WHERE " + "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR "
			+ "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchText, '%')) OR "
			+ "LOWER(b.genre) LIKE LOWER(CONCAT('%', :searchText, '%'))")
	Page<Book> searchByText(@Param("searchText") String searchText, Pageable pageable);

	Page<Book> findByGenre(String genre, Pageable pageable);
	
	Optional<Book> findById(UUID id);
}
