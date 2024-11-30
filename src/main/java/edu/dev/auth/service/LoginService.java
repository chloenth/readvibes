package edu.dev.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.dev.auth.dto.LoginDto;
import edu.dev.auth.dto.UserDto;
import edu.dev.auth.entity.User;
import edu.dev.auth.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDto loginUser(LoginDto loginDto) {
		Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

		if (userOptional.isPresent()) {
			User user = userOptional.get();

			if (passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
				UserDto userDto = new UserDto();

				userDto.setId(user.getId().toString());
				userDto.setFullname(user.getFullname());
				userDto.setUsername(user.getUsername());
				userDto.setEmail(user.getEmail());
				userDto.setAddress(user.getAddress());

				return userDto;
			}

		}

		return null;
	}
}
