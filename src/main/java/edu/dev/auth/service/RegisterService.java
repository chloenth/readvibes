package edu.dev.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.dev.auth.dto.RegistrationDto;
import edu.dev.auth.entity.User;
import edu.dev.auth.exception.DuplicateEmailException;
import edu.dev.auth.exception.DuplicateUsernameException;
import edu.dev.auth.repository.UserRepository;

@Service
public class RegisterService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void registerUser(RegistrationDto dto) {
		// check username duplicate
		if (isUsernameAlreadyExisted(dto.getUsername())) {
			throw new DuplicateUsernameException(
					"Registration failed: The username " + dto.getUsername() + " is already taken.");
		}

		// check email duplicate
		if (isEmailAlreadyExisted(dto.getEmail())) {
			throw new DuplicateEmailException(
					"Registration failed: The email " + dto.getEmail() + " is already taken.");
		}

		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
		user.setFullname(dto.getFullname());
		user.setEmail(dto.getEmail());
		user.setAddress(dto.getAddress());

		userRepository.save(user);
	}

	public boolean isUsernameAlreadyExisted(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	public boolean isEmailAlreadyExisted(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

}
