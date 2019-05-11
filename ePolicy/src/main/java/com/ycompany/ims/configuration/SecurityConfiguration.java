package com.ycompany.ims.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * @author Sameer Kalra
 * 
 * Provides security configurations for role based authentication/authorization and store encrypted user password in the data base. 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
		jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.
		authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/registration").permitAll()
		.antMatchers("/agent/registration").permitAll()
		.antMatchers("/admin/registration").permitAll()
		.antMatchers("/customer/registration").permitAll()
		.antMatchers("/policies").permitAll()
		.antMatchers("/agent/**").hasAnyAuthority("AGENT","ADMIN")
		.antMatchers("/customer/**").hasAnyAuthority("CUSTOMER","ADMIN")
		.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
		.authenticated().and().csrf().disable().formLogin()
		.loginPage("/login").failureUrl("/login?error=true")
		.usernameParameter("email")
		.passwordParameter("password")
		.successHandler(authenticationSuccessHandler)
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").and().exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
