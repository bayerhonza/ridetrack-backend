package com.ensimag.ridetrack.auth;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class RtUserPrincipal implements UserDetails, CredentialsContainer {
	
	private final String username;
	
	private String password;
	
	private final Set<GrantedAuthority> privileges;
	
	private final boolean accountNonExpired;
	
	private final boolean accountNonLocked;
	
	private final boolean credentialsNonExpired;
	
	private final boolean enabled;
	
	public RtUserPrincipal(String username, String password, Set<GrantedAuthority> privileges) {
		this(username, password, true, true, true, true, privileges);
	}
	
	public RtUserPrincipal(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Set<GrantedAuthority> privileges) {
		
		Assert.isTrue(!Strings.isEmpty(username),"Cannot pass null or empty values as username");
		Assert.notNull(password,"Cannot pass null as password");
		
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.privileges = Collections.unmodifiableSet(privileges);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return privileges;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void eraseCredentials() {
		this.password = null;
	}
}
