package edu.dev.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.dev.book.dto.BookDto;
import edu.dev.book.exception.BookNotFoundException;
import edu.dev.book.service.BookDetailsService;
import edu.dev.review.dto.ReviewDto;
import edu.dev.review.dto.ReviewFormDto;
import edu.dev.review.exception.DuplicateReviewException;
import edu.dev.review.exception.ReviewNotFoundException;
import edu.dev.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books/{bookId}")
public class BookDetailsController {

	@Autowired
	private BookDetailsService bookDetailsService;

	@GetMapping
	public String showBookDetailsPage(@PathVariable String bookId, Model model, HttpSession session,
			HttpServletRequest request, @RequestParam(defaultValue = "1") int page) {

		try {
			BookDto bookDto = bookDetailsService.getBookDetailsDto(bookId);
			model.addAttribute("bookDto", bookDto);

			Page<ReviewDto> pageReviewDtoData = bookDetailsService.getReviewList(bookId, page - 1);

			String userId = (String) model.getAttribute("userId");

			// check if user has already reviewed for this book
			String reviewIdByUserAndBook = bookDetailsService.getReviewIdByBookIdAndUserId(bookId, userId);

			if (reviewIdByUserAndBook != null) {
				model.addAttribute("isReviewed", true);
				model.addAttribute("reviewId", reviewIdByUserAndBook);
			}

			model.addAttribute("bookId", bookId);

			return initReviewPaginationOnBookPage(pageReviewDtoData, model);

		} catch (BookNotFoundException e) {
			e.printStackTrace();
			return "redirect:/books";
		}

	}

	@GetMapping("add-review")
	public String showAddReviewForm(Model model, @PathVariable String bookId, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("reviewFormDto", new ReviewFormDto());

		try {
			setBookAttributes(bookId, model);
		} catch (BookNotFoundException e) {
			e.printStackTrace();
			return "redirect:/books";
		}

		return "book/review-form";
	}

	@PostMapping("add-review")
	public String addReview(@Valid @ModelAttribute("reviewFormDto") ReviewFormDto reviewFormDto,
			BindingResult bindingResult, HttpSession session, @PathVariable String bookId, Model model) {

		if (bindingResult.hasErrors()) {
			setBookAttributes(bookId, model);
			return "book/review-form";
		}

		String userId = (String) model.getAttribute("userId");

		try {
			bookDetailsService.addReviewToBookByUser(reviewFormDto, bookId, userId);
		} catch (DuplicateReviewException e) {
			e.printStackTrace();
			return "redirect:/books/{bookId}?addReview=duplicate";
		}

		return "redirect:/books/{bookId}?addReview=successful";
	}

	@GetMapping("{reviewId}/update-review")
	public String showUpdateReviewForm(@PathVariable String bookId, @PathVariable String reviewId, Model model) {
		try {
			setBookAttributes(bookId, model);
			model.addAttribute("reviewFormDto", bookDetailsService.getReviewFormDtoById(reviewId));
			model.addAttribute("isUpdate", true);
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			return "redirect:/books/{bookId}";
		}

		return "book/review-form";
	}

	@PutMapping("{reviewId}/update-review")
	public String updateReview(@Valid @ModelAttribute("reviewFormDto") ReviewFormDto reviewFormDto,
			BindingResult bindingResult, @PathVariable String reviewId, @PathVariable String bookId, Model model) {

		if (bindingResult.hasErrors()) {
			setBookAttributes(bookId, model);
			return "book/review-form";
		}

		try {
			bookDetailsService.updateReview(reviewId, reviewFormDto, bookId);
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			return "redirect:/books/{bookId}";
		}

		return "redirect:/books/{bookId}?updateReview=successful";
	}

	@GetMapping("{reviewId}/remove-review")
	public String removeReview(@PathVariable String reviewId) {
		try {
			bookDetailsService.removeReview(reviewId);
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			return "redirect:/books/{bookId}";
		}

		return "redirect:/books/{bookId}?removeReview=successful";
	}

	private void setBookAttributes(String bookId, Model model) {
		BookDto bookDto = bookDetailsService.getBookDetailsDto(bookId);
		model.addAttribute("bookTitle", bookDto.getTitle());
		model.addAttribute("bookId", bookDto.getId());
	}

	private String initReviewPaginationOnBookPage(Page<ReviewDto> pageReviewDtoData, Model model) {
		model.addAttribute("isEmptyList", pageReviewDtoData.isEmpty());

		if (pageReviewDtoData.isEmpty()) {
			return "book/book-details";
		}

		int currentPage = pageReviewDtoData.getNumber();
		int totalPages = pageReviewDtoData.getTotalPages();

		int startPage = Math.max(0, currentPage - 7);
		int endPage = Math.min(totalPages - 1, startPage + 9);

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageSize", ReviewService.PAGE_SIZE);
		model.addAttribute("reviewDtos", pageReviewDtoData.getContent());

		return "book/book-details";
	}
}
