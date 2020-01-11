package com.ensimag.ridetrack.auth;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.RtUser;
import com.ensimag.ridetrack.repository.RtUserRepository;

@Service
@Primary
public class RtUserManager implements UserDetailsService, UserDetailsPasswordService {
	
	@Autowired
	private RtUserRepository rtUserRepository;
	
	
	@Override
	public RtUserPrincipal loadUserByUsername(String username) {
		RtUser user = rtUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new RtUserPrincipal(user, user.getUsername(), user.getPassword(), getAuthorities(user));
	}

	private Set<Role> getAuthorities(RtUser user) {
		return user.getRoles();
	}
	
	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		RtUser rtUser = rtUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> new BadCredentialsException(user.getUsername()));
		if (!user.getPassword().equals(newPassword)) {
			rtUser.setPassword(newPassword);
			rtUserRepository.saveAndFlush(rtUser);
		}
		return user;
		
	}
}
