package edu.dev.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.dev.auth.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAttribute {

	@ModelAttribute
	public void addGlobalAttributes(Model model, HttpSession session, HttpServletRequest request) {
		if (session != null) {
			UserDto userDto = (UserDto) session.getAttribute("userDto");

			if (userDto != null) {
				model.addAttribute("userFullname", userDto.getFullname());
				model.addAttribute("userId", userDto.getId());
			}
		}
		
		model.addAttribute("basePath", request.getContextPath());
		model.addAttribute("homePath", request.getContextPath() + "/books");
	}
}
