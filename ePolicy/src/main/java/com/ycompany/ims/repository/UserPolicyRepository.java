package com.ycompany.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ycompany.ims.model.Policy;
import com.ycompany.ims.model.User;
import com.ycompany.ims.model.UserPolicy;

/**
 * @author Sameer Kalra
 * 
 * Defines userpolicy repository to perform CRUD operations in database.  
 */
@Repository("userPolicyRepository")
public interface UserPolicyRepository extends JpaRepository<UserPolicy, Integer>{
	UserPolicy findById(int id);
	UserPolicy findByUserAndPolicy(User user, Policy policy);
	UserPolicy findByUserAndId(User user, int appId);
	List<UserPolicy> findByUser(User user);
}