package edu.dev.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewFormDto {

	@Size(max = 10000, message = "{review.content.size}")
	private String content;
	
	@NotNull(message = "{review.rating.invalid}")
	@Min(value = 1, message = "{review.rating.invalid}")
	@Max(value = 5, message = "{review.rating.invalid}")
	private Integer rating; // Rating between 1 and 5

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public ReviewFormDto() {

	}

	public ReviewFormDto(@Size(max = 10000, message = "{review.content.size}") String content,
			@Min(value = 1, message = "{review.rating.min}") @Max(value = 5, message = "{review.rating.max}") Integer rating) {

		this.content = content;
		this.rating = rating;
	}

}
