package com.ycompany.ims.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ycompany.ims.enums.ApplicationStatus;
import com.ycompany.ims.enums.PaymentPlan;
import com.ycompany.ims.model.Policy;
import com.ycompany.ims.model.User;
import com.ycompany.ims.model.UserPolicy;
import com.ycompany.ims.service.PolicyService;
import com.ycompany.ims.service.UserPolicyService;
import com.ycompany.ims.service.UserService;

/**
 * @author Sameer Kalra
 * 
 * Provides view mappings for customer.  
 */
@Controller
public class CustomerController {

	@Autowired
	private UserService userService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private UserPolicyService userPolicyService;

	@RequestMapping(value="/customer/registration", method = RequestMethod.GET)
	public ModelAndView customerRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("customer/registration");
		return modelAndView;
	}

	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public ModelAndView createNewCustomer(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
			.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("customer/registration");
		} else {
			userService.saveCustomer(user);
			modelAndView.addObject("successMessage", "Customer has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("customer/registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/customer/home", method = RequestMethod.GET)
	public ModelAndView customerHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("customerMessage","Content Available Only for Users with Customer Role");
		modelAndView.setViewName("customer/home");
		return modelAndView;
	}

	@RequestMapping(value="/customer/buyPolicy", method = RequestMethod.GET)
	public ModelAndView customerPolicyRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		UserPolicy userPolicy = new UserPolicy();
		List<Policy> allPolicies = policyService.findAllPolicies();
		userPolicy.setUser(user);
		modelAndView.addObject("userPolicy", userPolicy);
		modelAndView.addObject("allPolicies",allPolicies);
		modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
		modelAndView.setViewName("customer/buyPolicy");
		return modelAndView;
	}

	@RequestMapping(value="/customer/buyPolicy", method = RequestMethod.POST)
	public ModelAndView applyPolicy(@Valid UserPolicy userPolicy, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Policy> allPolicies = policyService.findAllPolicies();
		User user = userService.findUserByEmail(auth.getName());
		UserPolicy userPolicyExists = userPolicyService.findUserPolicyByUserAndPolicy(user,userPolicy.getPolicy());
		if (userPolicyExists != null) {
			bindingResult
			.rejectValue("policy", "error.user",
					"You have already registered for the same policy");
		}
		if (bindingResult.hasErrors()) {
			userPolicy.setUser(user);
			modelAndView.addObject("userPolicy", userPolicy);
			modelAndView.addObject("allPolicies",allPolicies);
			modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
			modelAndView.setViewName("customer/buyPolicy");
		} else {
			userPolicy.setUser(user);
			userPolicy.setApplicationStatus(ApplicationStatus.IN_PROGRESS.toString());
			userPolicyService.saveUserPolicy(userPolicy);
			modelAndView.addObject("successMessage", "Policy application has been filed successfully");
			modelAndView.addObject("userPolicy", userPolicy);
			modelAndView.addObject("allPolicies",allPolicies);
			modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
			modelAndView.setViewName("customer/buyPolicy");

		}
		return modelAndView;
	}

	@RequestMapping(value="/customer/viewPolicyStatus", method = RequestMethod.GET)
	public ModelAndView viewPolicyApplicationSatus(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<UserPolicy> userPolicies = userPolicyService.findAllUserPoliciesByUser(user);
		modelAndView.addObject("userPolicies", userPolicies);
		modelAndView.setViewName("customer/viewPolicyStatus");
		return modelAndView;
	}

	@RequestMapping(value="/customer/updateBillingCycle", method = RequestMethod.GET)
	public ModelAndView updateCustomerPolicyBillingCycle(@RequestParam(name = "appId") int appId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		UserPolicy userPolicy = userPolicyService.findUserPolicyByUserAndApplication(user,appId);
		List<Policy> allPolicies = policyService.findAllPolicies();
		modelAndView.addObject("userPolicy", userPolicy);
		modelAndView.addObject("allPolicies",allPolicies);
		modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
		modelAndView.setViewName("customer/updateBillingCycle");
		return modelAndView;
	}

	@RequestMapping(value="/customer/updateBillingCycle", method = RequestMethod.POST)
	public ModelAndView applyUpdate(@Valid UserPolicy userPolicy, @RequestParam(name = "appId") int appId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		UserPolicy uPolicy = userPolicyService.findUserPolicyByUserAndApplication(user,appId);
		List<Policy> allPolicies = policyService.findAllPolicies();
		uPolicy.setPremiumPaymentPlan(userPolicy.getPremiumPaymentPlan());
		uPolicy.setPremiumAmount(userPolicy.getPremiumAmount());
		uPolicy.setApplicationStatus(ApplicationStatus.IN_PROGRESS.toString());
		userPolicyService.saveUserPolicy(uPolicy);
		modelAndView.addObject("successMessage", "Policy application has been updated successfully");
		modelAndView.addObject("userPolicy", uPolicy);
		modelAndView.addObject("allPolicies",allPolicies);
		modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
		modelAndView.setViewName("customer/updateBillingCycle");	
		return modelAndView;
	}

	@RequestMapping(value="/customer/payment", method = RequestMethod.GET)
	public ModelAndView policyPayment(@RequestParam(name = "appId") int appId){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("customer/payment");
		return modelAndView;
	}

}
