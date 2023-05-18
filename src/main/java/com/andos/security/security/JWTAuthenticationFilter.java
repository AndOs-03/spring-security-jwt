package com.andos.security.security;

import static com.andos.security.security.SecParams.EXP_TIME;
import static com.andos.security.security.SecParams.SECRET;

import com.andos.security.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p></p>
 *
 * @author Anderson Ouattara 2023-05-13
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    super();
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {
    User user = null;
    try {
      user = new ObjectMapper().readValue(request.getInputStream(), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        user.getPassword()
    ));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult
  ) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User springUser =
        (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

    List<String> roles = new ArrayList<>();
    springUser.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));

    String jwt = JWT.create().
        withSubject(springUser.getUsername()).
        withArrayClaim("roles", roles.toArray(new String[roles.size()])).
        withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME)).
        sign(Algorithm.HMAC256(SECRET));
    response.addHeader("Authorization", jwt);
  }
}
