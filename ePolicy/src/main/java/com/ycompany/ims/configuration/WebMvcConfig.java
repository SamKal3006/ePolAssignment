package com.ycompany.ims.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sameer Kalra
 * 
 * Provides bean configurations.  
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public AuthenticationSuccessHandler authSuccessHandler(){
		return new SimpleUrlAuthenticationSuccessHandler();
	}

}
