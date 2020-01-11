package com.ensimag.ridetrack.exception;

public class RidetrackConflictException extends AbstractEntityException {


	public RidetrackConflictException(Class<?> entity, String property, String message) {
		super(entity, property, message);
	}

	public RidetrackConflictException(Class<?> entity, String property, String message, Throwable throwable) {
		super(entity, property, message, throwable);
	}

	public String getConflictingProperty() {
		return getProperty();
	}
}
