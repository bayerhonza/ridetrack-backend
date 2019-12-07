package com.ensimag.ridetrack.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.User;
import com.ensimag.ridetrack.repository.UserRepository;

@Service
@Primary
public class RtUserManager implements UserDetailsService, UserDetailsPasswordService {
	
	private final UserRepository userRepository;
	
	public RtUserManager(
			@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(),
			user.getPassword(),
			getAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		return new ArrayList<>(getPrivileges(user.getRoles()));
	}

	private List<GrantedAuthority> getPrivileges(Collection<Role> roles) {
		List<GrantedAuthority> privileges = new ArrayList<>();
		roles.forEach(role -> {
			privileges.add(role);
			privileges.addAll(role.getPrivileges());
		});
		return privileges;
	}
	
	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		User rtUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new BadCredentialsException(user.getUsername()));
		if (!user.getPassword().equals(newPassword)) {
			rtUser.setPassword(newPassword);
			userRepository.saveAndFlush(rtUser);
		}
		return user;
		
	}
}
