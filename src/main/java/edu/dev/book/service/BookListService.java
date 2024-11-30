package edu.dev.book.service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import edu.dev.book.dto.BookDto;
import edu.dev.book.entity.Book;
import edu.dev.book.mapper.BookMapper;
import edu.dev.book.repository.BookRepository;

@Service
public class BookListService {

	@Autowired
	private BookRepository bookRepository;
	
	private final BookMapper bookMapper;
	
	 public BookListService(BookMapper bookMapper) {
	        this.bookMapper = bookMapper;
	    }

	public static final int PAGE_SIZE = 5;
	private String DEFAULT_SORT_FIELD = "title";

	// It can be developed into a Genre entity for further enhancement.
	public static final List<String> GENRES = List.of("Horror", "Literary", "Drama", "Thriller", "Historical", "Sci-Fi",
			"Young Adult", "Classics", "Mystery", "Fantasy", "Fiction", "Adventure", "Romance");

	// fetch book list with pagination
	public Page<BookDto> getBookList(int pageNumber, String rating) {
		Pageable pageable = createPageableWithSort(pageNumber, rating);

		Page<Book> pageData = bookRepository.findAll(pageable);

		return bookMapper.mapPageBookToPageDto(pageData, pageable);
	}

	// fetch book list by search text and pagination
	public Page<BookDto> searchBooksByText(String searchText, int pageNumber, String rating) {
		Pageable pageable = createPageableWithSort(pageNumber, rating);

		Page<Book> pageData = bookRepository.searchByText(searchText, pageable);

		return bookMapper.mapPageBookToPageDto(pageData, pageable);

	}

	// fetch book list by genre and pagination
	public Page<BookDto> filterBooksByGenre(String genre, int pageNumber, String rating) {
		Pageable pageable = createPageableWithSort(pageNumber, rating);

		Page<Book> pageData = bookRepository.findByGenre(genre, pageable);

		return bookMapper.mapPageBookToPageDto(pageData, pageable);
	}

	private Pageable createPageableWithSort(int pageNumber, String rating) {
		Sort sort;

		if ("desc".equals(rating)) {
			sort = Sort.by(Sort.Direction.DESC, "averageRating");

		} else if ("asc".equals(rating)) {
			sort = Sort.by(Sort.Direction.ASC, "averageRating");

		} else {
			sort = Sort.by(Sort.Direction.ASC, DEFAULT_SORT_FIELD);

		}

		return PageRequest.of(pageNumber, PAGE_SIZE, sort);
	}

	// import data from csv to database
	public void importCsvToDatabase() {
		try (
				// Load the CSV file and create CSV Reader
				InputStreamReader reader = new InputStreamReader(
						new ClassPathResource("data/book.csv").getInputStream());
				CSVReader csvReader = new CSVReader(reader)) {

			String[] line;
			List<Book> books = new ArrayList<>();

			csvReader.readNext();

			while ((line = csvReader.readNext()) != null) {
				Book book = new Book();

				book.setTitle(line[0]);
				book.setAuthor(line[1]);
				book.setGenre(line[2]);
				book.setAverageRating(new BigDecimal(line[3]));

				System.out.println("book: " + book);

				books.add(book);
			}

			// Save to database
			bookRepository.saveAll(books);
			System.out.println("Books successfully imported!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
