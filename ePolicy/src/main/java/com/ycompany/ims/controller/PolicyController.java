package com.ycompany.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ycompany.ims.model.Policy;
import com.ycompany.ims.service.PolicyService;

/**
 * @author Sameer Kalra
 * 
 * Provides view mappings for handling policy.  
 */
@Controller
public class PolicyController {

	@Autowired
	private PolicyService policyService;

	@RequestMapping(value="/policies")
	public ModelAndView policies(){
		ModelAndView modelAndView = new ModelAndView();
		List<Policy> policies = policyService.findAllPolicies();
		modelAndView.addObject("policies",policies);
		modelAndView.setViewName("policies");
		return modelAndView;
	}
}
