package com.ycompany.ims.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * @author Sameer Kalra
 * 
 * Provides thymeleaf configuration.  
 */
@Configuration
public class ThymeleafConfig {

	@Bean
	public SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}
}
