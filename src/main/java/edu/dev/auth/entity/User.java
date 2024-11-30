package edu.dev.auth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.dev.review.entity.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(length = 50)
	private UUID id;

	@Column(unique = true, nullable = false, length = 30)
	private String username;

	@Column(name = "password_hash", nullable = false, length = 60)
	private String passwordHash;

	@Column(nullable = false, length = 50)
	private String fullname;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

	@Column(length = 255)
	private String address;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews;

	public User() {
		this.id = UUID.randomUUID();
		reviews = new ArrayList<>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

}
