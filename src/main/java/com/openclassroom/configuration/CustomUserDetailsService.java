package com.openclassroom.configuration;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassroom.model.DBUser;
import com.openclassroom.repository.DBUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private DBUserRepository dbUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		DBUser user = dbUserRepository.findByEmail(email);

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
}
