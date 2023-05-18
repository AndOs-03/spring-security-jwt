package com.andos.security.repos;

import com.andos.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p></p>
 *
 * @author Anderson Ouattara 2023-05-13
 */
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
