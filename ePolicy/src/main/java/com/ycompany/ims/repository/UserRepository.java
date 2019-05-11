package com.ycompany.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ycompany.ims.model.User;

/**
 * @author Sameer Kalra
 * 
 * Defines user repository to perform CRUD operations in database.  
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
