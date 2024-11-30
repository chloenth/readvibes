package edu.dev.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.dev.book.dto.BookDto;
import edu.dev.book.service.BookListService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class BookListController {

	@Autowired
	private BookListService bookListService;

	@GetMapping
	public String showBookListPage(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "none") String rating) {

		// bookService.importCsvToDatabase();

		Page<BookDto> pageDtoData = bookListService.getBookList(page - 1, rating);
		return initializeBookListPage(pageDtoData, model, session, rating);
	}

	@GetMapping(params = { "search" })
	public String searchBooksByText(@RequestParam String search, @RequestParam(defaultValue = "none") String rating,
			@RequestParam(defaultValue = "1") int page, Model model, HttpSession session) {

		Page<BookDto> pageDtoData = bookListService.searchBooksByText(search, page - 1, rating);

		model.addAttribute("search", search);

		return initializeBookListPage(pageDtoData, model, session, rating);

	}

	@GetMapping(params = { "filter" })
	public String filterBooksByGenre(@RequestParam String filter, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "none") String rating, Model model, HttpSession session) {

		Page<BookDto> pageDtoData = bookListService.filterBooksByGenre(filter, page - 1, rating);

		model.addAttribute("filter", filter);

		return initializeBookListPage(pageDtoData, model, session, rating);
	}

	private String initializeBookListPage(Page<BookDto> pageDtoData, Model model, HttpSession session, String rating) {
		if (!"none".equals(rating)) {
			model.addAttribute("rating", rating);
		}

		model.addAttribute("isEmptyList", pageDtoData.isEmpty());
		model.addAttribute("genres", BookListService.GENRES);

		if (pageDtoData.isEmpty()) {
			return "book/book-list";
		}

		int currentPage = pageDtoData.getNumber();
		int totalPages = pageDtoData.getTotalPages();

		int startPage = Math.max(0, currentPage - 7);
		int endPage = Math.min(totalPages - 1, startPage + 9);

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageSize", BookListService.PAGE_SIZE);
		model.addAttribute("bookDtos", pageDtoData.getContent());

		return "book/book-list";
	}
}
