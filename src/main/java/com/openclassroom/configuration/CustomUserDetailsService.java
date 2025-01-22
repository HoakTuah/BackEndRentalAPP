package com.openclassroom.configuration;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassroom.Entity.DBUser;
import com.openclassroom.repository.UserRepository;

// ----------------------------------------------------------------------------------------
// Service for loading user details by username
// ----------------------------------------------------------------------------------------

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// ----------------------------------------------------------------------------------------
	// Dependencies
	// ----------------------------------------------------------------------------------------

	@Autowired
	private UserRepository dbUserRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// Find user in database by email
		DBUser user = dbUserRepository.findByEmail(login);

		return new User(user.getUserMail(), user.getPassword(), new ArrayList<>());
	}
}
