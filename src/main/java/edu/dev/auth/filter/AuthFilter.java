package edu.dev.auth.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic if needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		// Check if the user is authenticated
		if (session != null && session.getAttribute("userDto") != null) {
			chain.doFilter(request, response); // Continue to the requested resource
			
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login"); // Redirect to login page
		}
	}

	@Override
	public void destroy() {
		// Cleanup logic if needed
	}
}
