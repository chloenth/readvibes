package edu.dev.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.dev.auth.entity.User;
import edu.dev.auth.exception.UserNotFoundException;
import edu.dev.auth.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserById(String id) {
		return userRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));
	}

}
