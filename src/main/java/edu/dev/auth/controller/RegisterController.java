package edu.dev.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.dev.auth.dto.RegistrationDto;
import edu.dev.auth.exception.DuplicateEmailException;
import edu.dev.auth.exception.DuplicateUsernameException;
import edu.dev.auth.service.RegisterService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@GetMapping
	public String showRegisterForm(Model model) {
		model.addAttribute("registrationDto", new RegistrationDto());

		return "auth/register";
	}

	@PostMapping
	public String register(@Valid @ModelAttribute("registrationDto") RegistrationDto dto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "auth/register";
		}

		try {
			registerService.registerUser(dto);

		} catch (DuplicateUsernameException e) {
			bindingResult.rejectValue("username", "user.username.taken");
			return "auth/register";

		} catch (DuplicateEmailException e) {
			bindingResult.rejectValue("email", "user.email.taken");
			return "auth/register";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
			return "redirect:register?error=true";
			
		}
		
		redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in to your account.");
		
		return "redirect:login?register=successful";
	}
}
