package edu.dev.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.dev.auth.dto.LoginDto;
import edu.dev.auth.dto.RegistrationDto;
import edu.dev.auth.dto.UserDto;
import edu.dev.auth.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/")
	public String welcome() {
		return "index";
	}

	@GetMapping("login")
	public String showLoginForm(Model model) {
		model.addAttribute("loginDto", new RegistrationDto());
		return "auth/login";
	}

	@PostMapping("login")
	public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {

			return "auth/login";
		}

		UserDto userDto = loginService.loginUser(loginDto);

		if (userDto == null) {
			bindingResult.rejectValue("username", "user.invalid.credentials");
			bindingResult.rejectValue("password", "user.invalid.credentials");
			return "auth/login";
		}
		
		session.setAttribute("userDto", userDto);

		return "redirect:books";
	}
}
