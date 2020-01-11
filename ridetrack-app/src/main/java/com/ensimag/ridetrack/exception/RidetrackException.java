package com.ensimag.ridetrack.exception;

public class RidetrackException extends Exception {

	public RidetrackException(String message) {
		super(message);
	}

	public RidetrackException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
