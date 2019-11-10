package com.ensimag.ridetrack.auth;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private final SuperRideTrackUser superUser = SuperRideTrackUser.getSuperUser();
	private final Set<RideTrackUser> adminUsers = new HashSet<>();

	private AuthenticationService() {
		
	}

	public boolean isAllowedToDelete(RideTrackUser rideTrackUser) {
		return rideTrackUser == this.superUser || adminUsers.contains(rideTrackUser);
	}
	
	
}
