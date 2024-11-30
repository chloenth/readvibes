package edu.dev.book.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import edu.dev.book.dto.BookDto;
import edu.dev.book.entity.Book;

@Component
public class BookMapper {

	// Map Book to BookDto
	public BookDto mapBookToDto(Book book) {
		return new BookDto(book.getId().toString(), book.getTitle(), book.getAuthor(), book.getGenre(),
				book.getAverageRating());
	}

	// Map Page<Book> to Page<BookDto>
	public Page<BookDto> mapPageBookToPageDto(Page<Book> pageData, Pageable pageable) {
		List<BookDto> bookDtos = pageData.getContent().stream().map(this::mapBookToDto) // Won't call mapBookToDto if
																						// the list is empty
				.toList();

		return new PageImpl<>(bookDtos, pageable, pageData.getTotalElements());
	}
}
