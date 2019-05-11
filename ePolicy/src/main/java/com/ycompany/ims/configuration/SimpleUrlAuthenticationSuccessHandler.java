package com.ycompany.ims.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Sameer Kalra
 * 
 * Manages and Handles successful role based authentication redirection url.  
 */
public class SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	protected Logger log = LoggerFactory.getLogger(SimpleUrlAuthenticationSuccessHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication)
					throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication)
					throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			log.debug(
					"Response has already been committed. Unable to redirect to "
							+ targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isAgent = false;
		boolean isAdmin = false;
		boolean isCustomer = false;
		Collection<? extends GrantedAuthority> authorities
		= authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("AGENT")) {
				isAgent = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ADMIN")) {
				isAdmin = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}

		if (isAgent) {
			return "agent/home";
		} else if (isAdmin) {
			return "admin/home";
		} else if (isCustomer) {
			return "customer/home";
		} else {
			throw new IllegalStateException();
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
