package com.ensimag.ridetrack.auth;

public class SuperRideTrackUser extends RideTrackUser {

	private static SuperRideTrackUser instance = null;

	private SuperRideTrackUser() {
	}

	public static SuperRideTrackUser getSuperUser() {
		if (instance == null) {
			instance = new SuperRideTrackUser();
		}
		return instance;
	}
}
