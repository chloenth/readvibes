package edu.dev.auth.dto;

import edu.dev.auth.validation.annotation.PasswordMatch;
import edu.dev.util.StringUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatch
public class RegistrationDto extends BaseUserDto {
	
	@NotBlank(message = "{user.confirmPassword.required}")
	@Size(max = 50, message = "{user.confirmPassword.size}")
	private String confirmPassword;
	
	@NotBlank(message = "{user.fullname.required}")
	@Size(max = 50, message = "{user.fullname.size}")
	private String fullname;
	
	@NotBlank(message = "{user.email.required}")
	@Size(max = 255, message = "{user.email.size}")
	@Email(message = "{user.email.invalid}")
	private String email;
	
	@Size(max = 255, message = "{user.address.size}")
	private String address;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = StringUtils.trim(confirmPassword);
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = StringUtils.trim(fullname);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = StringUtils.trim(email);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = StringUtils.trim(address);
	}
}
