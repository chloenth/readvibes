package edu.dev.review.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewDto {

	private String id;
	private String content;
	private int rating;
	private String userId;
	private String userFullname;
	private LocalDateTime createdAt;
	private LocalDateTime editedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getEditedAt() {
		return editedAt;
	}

	public void setEditedAt(LocalDateTime editedAt) {
		this.editedAt = editedAt;
	}
	
	public String getCreatedAtString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
		return createdAt.format(formatter);
	}
	
	public String getEditedAtString() {
		if(editedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
			return editedAt.format(formatter);
		}
		
		return null;
	}
}
