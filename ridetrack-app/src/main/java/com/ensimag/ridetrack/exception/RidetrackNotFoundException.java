package com.ensimag.ridetrack.exception;

public class RidetrackNotFoundException extends RuntimeException {

	public RidetrackNotFoundException(String msg) {
		super(msg);
	}

	public RidetrackNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
