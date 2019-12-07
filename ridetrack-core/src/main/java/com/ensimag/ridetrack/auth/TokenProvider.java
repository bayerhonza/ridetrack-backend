package com.ensimag.ridetrack.auth;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TokenProvider {
	
	/**
	 * Retrieves username from token
	 * @param token token
	 * @return username
	 */
	String getUsernameFromToken(String token);
	
	/**
	 * Generates token for username
	 * @param username username
	 * @return valid generated token
	 */
	@PreAuthorize("isFullyAuthenticated()")
	String generateToken(String username);
	
	/**
	 * Validates token.
	 *
	 * Depends on the underlying implementation
	 * @param token token
	 * @throws
	 */
	void validateToken(String token);
}
