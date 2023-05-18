package com.andos.security.repos;

import com.andos.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p></p>
 *
 * @author Anderson Ouattara 2023-05-13
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByRole(String role);
}
