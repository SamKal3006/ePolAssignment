package com.ycompany.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ycompany.ims.model.Policy;

/**
 * @author Sameer Kalra
 * 
 * Defines policy repository to perform CRUD operations in database.  
 */
@Repository("policyRepository")
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
	Policy findById(int id);
}
