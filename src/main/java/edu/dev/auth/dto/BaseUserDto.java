package edu.dev.auth.dto;

import edu.dev.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public abstract class BaseUserDto {

	@NotBlank(message = "{user.username.required}")
	@Size(max = 30, message = "{user.username.size}")
	private String username;

	@NotBlank(message = "{user.password.required}")
	@Size(max = 50, message = "{user.password.size}")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = StringUtils.trim(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = StringUtils.trim(password);
	}
}
