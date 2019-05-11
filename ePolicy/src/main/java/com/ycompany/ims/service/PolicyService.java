package com.ycompany.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycompany.ims.model.Policy;
import com.ycompany.ims.repository.PolicyRepository;

/**
 * @author Sameer Kalra
 * 
 * Defines policy services which interacts with policy repository to communicate with the database.  
 */
@Service("policyService")
public class PolicyService {

	private PolicyRepository policyRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository) {
		this.policyRepository = policyRepository;
	}

	public Policy findPolicyById(int id) {
		return policyRepository.findById(id);
	}

	public List<Policy> findAllPolicies() {
		return policyRepository.findAll();
	}	

}
