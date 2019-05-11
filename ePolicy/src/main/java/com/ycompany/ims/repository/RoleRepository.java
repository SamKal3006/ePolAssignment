package com.ycompany.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ycompany.ims.model.Role;

/**
 * @author Sameer Kalra
 * 
 * Defines role repository to perform CRUD operations in database.  
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);
}
