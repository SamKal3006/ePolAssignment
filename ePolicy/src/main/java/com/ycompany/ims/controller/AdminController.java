package com.ycompany.ims.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ycompany.ims.model.User;
import com.ycompany.ims.service.UserService;

/**
 * @author Sameer Kalra
 * 
 * Provides view mappings for internal or admin user.  
 */
@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/admin/registration", method = RequestMethod.GET)
	public ModelAndView employeeRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("admin/registration");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
	public ModelAndView createNewEmployee(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
			.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/registration");
		} else {
			userService.saveEmployee(user);
			modelAndView.addObject("successMessage", "Employee has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("admin/registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView adminHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
}
