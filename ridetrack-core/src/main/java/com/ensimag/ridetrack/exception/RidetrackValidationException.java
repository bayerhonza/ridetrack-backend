package com.ensimag.ridetrack.exception;

public class RidetrackValidationException extends AbstractEntityException {


	public RidetrackValidationException(Class<?> entity, String property, String message) {
		super(entity, property, message);
	}

	public RidetrackValidationException(Class<?> entity, String property, String message, Throwable throwable) {
		super(entity, message, property, throwable);
	}

	public String getNonValidProperty() {
		return getProperty();
	}
}
