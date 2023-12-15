package com.group.doconnect.repository;

import java.util.List;
import java.util.Optional;

import com.group.doconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	List<User> findByIsAdminTrue();

}
