package com.andos.security.service;

import com.andos.security.entities.Role;
import com.andos.security.entities.User;

/**
 * <p></p>
 *
 * @author Anderson Ouattara 2023-05-13
 */
public interface UserService {

  User saveUser(User user);

  User findUserByUsername(String username);

  Role addRole(Role role);

  User addRoleToUser(String username, String rolename);
}
