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
 * Provides view mappings for agent.  
 */
@Controller
public class AgentController {

	@Autowired
	private UserService userService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private UserPolicyService userPolicyService;

	@RequestMapping(value="/agent/registration", method = RequestMethod.GET)
	public ModelAndView agentRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("agent/registration");
		return modelAndView;
	}

	@RequestMapping(value = "/agent/registration", method = RequestMethod.POST)
	public ModelAndView createNewAgent(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
			.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("agent/registration");
		} else {
			userService.saveAgent(user);
			modelAndView.addObject("successMessage", "Agent has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("agent/registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/agent/home", method = RequestMethod.GET)
	public ModelAndView agentHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("agentMessage","Content Available Only for Users with Agent Role");
		modelAndView.setViewName("agent/home");
		return modelAndView;
	}

	@RequestMapping(value="/agent/viewPolicyStatus", method = RequestMethod.GET)
	public ModelAndView viewPolicyApplicationSatus(){
		ModelAndView modelAndView = new ModelAndView();
		List<UserPolicy> userPolicies = userPolicyService.findAllUserPolicies();
		modelAndView.addObject("userPolicies", userPolicies);
		modelAndView.setViewName("agent/viewPolicyStatus");
		return modelAndView;
	}

	@RequestMapping(value="/agent/updatePremiumAmount", method = RequestMethod.GET)
	public ModelAndView updateCustomerPolicyBillingCycle(@RequestParam(name = "appId") int appId){
		ModelAndView modelAndView = new ModelAndView();
		UserPolicy userPolicy = userPolicyService.findUserPolicyByApplication(appId);
		List<Policy> allPolicies = policyService.findAllPolicies();
		modelAndView.addObject("userPolicy", userPolicy);
		modelAndView.addObject("allPolicies",allPolicies);
		modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
		modelAndView.setViewName("agent/updatePremiumAmount");
		return modelAndView;
	}

	@RequestMapping(value="/agent/updatePremiumAmount", method = RequestMethod.POST)
	public ModelAndView applyUpdate(@Valid UserPolicy userPolicy, @RequestParam(name = "appId") int appId){
		ModelAndView modelAndView = new ModelAndView();		
		UserPolicy uPolicy = userPolicyService.findUserPolicyByApplication(appId);
		List<Policy> allPolicies = policyService.findAllPolicies();
		uPolicy.setCommissionAmount(userPolicy.getCommissionAmount());
		uPolicy.setPremiumAmount(userPolicy.getPremiumAmount());
		uPolicy.setApplicationStatus(ApplicationStatus.PENDING_PAYMENT.toString());
		userPolicyService.saveUserPolicy(uPolicy);
		modelAndView.addObject("successMessage", "Policy application has been updated successfully");
		modelAndView.addObject("userPolicy", uPolicy);
		modelAndView.addObject("allPolicies",allPolicies);
		modelAndView.addObject("allPaymentPlans",PaymentPlan.values());
		modelAndView.setViewName("agent/updatePremiumAmount");	
		return modelAndView;
	}
}
