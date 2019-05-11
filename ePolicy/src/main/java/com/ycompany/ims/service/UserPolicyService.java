package com.ycompany.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycompany.ims.model.Policy;
import com.ycompany.ims.model.User;
import com.ycompany.ims.model.UserPolicy;
import com.ycompany.ims.repository.UserPolicyRepository;

/**
 * @author Sameer Kalra
 * 
 * Defines userpolicy services which interacts with userpolicy repository to communicate with the database.  
 */
@Service("userPolicyService")
public class UserPolicyService {

	private UserPolicyRepository userPolicyRepository;

	@Autowired
	public UserPolicyService(UserPolicyRepository userPolicyRepository) {
		this.userPolicyRepository = userPolicyRepository;
	}

	public UserPolicy findUserPolicyByApplication(int appId) {
		return userPolicyRepository.findById(appId);
	}

	public UserPolicy findUserPolicyByUserAndPolicy(User user, Policy policy) {
		return userPolicyRepository.findByUserAndPolicy(user, policy);
	}

	public UserPolicy findUserPolicyByUserAndApplication(User user, int appId) {
		return userPolicyRepository.findByUserAndId(user, appId);
	}

	public List<UserPolicy> findAllUserPolicies() {
		return userPolicyRepository.findAll();
	}

	public List<UserPolicy> findAllUserPoliciesByUser(User user) {
		return userPolicyRepository.findByUser(user);
	}

	public UserPolicy saveUserPolicy(UserPolicy userPolicy) {
		return userPolicyRepository.save(userPolicy);
	}
}
