package edu.dev.book.dto;

import java.math.BigDecimal;

public class BookDto {
	private String id;
	private String title;
	private String author;
	private String genre;
	private BigDecimal averageRating;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
	}

	public BookDto(String id, String title, String author, String genre, BigDecimal averageRating) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.averageRating = averageRating;
	}

}
