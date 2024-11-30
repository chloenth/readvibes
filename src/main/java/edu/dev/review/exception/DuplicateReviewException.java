package edu.dev.review.exception;

public class DuplicateReviewException extends RuntimeException {
	public DuplicateReviewException(String message) {
		super(message);
	}
}
