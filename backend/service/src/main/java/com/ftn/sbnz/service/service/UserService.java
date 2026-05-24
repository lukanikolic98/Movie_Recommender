package com.ftn.sbnz.service.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    Object principal = authentication.getPrincipal();
    String email = null;

    if (principal instanceof UserDetails userDetails) {
      email = userDetails.getUsername(); // should be email in your JWT/UserDetails
    } else if (principal instanceof String) {
      // Sometimes principal is just the username/email string
      email = (String) principal;
    }

    if (email != null) {
      return userRepository.findByEmail(email).orElse(null);
    }

    return null;
  }
}
