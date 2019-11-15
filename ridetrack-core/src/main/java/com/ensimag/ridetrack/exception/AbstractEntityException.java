package com.ensimag.ridetrack.exception;

public class AbstractEntityException extends RuntimeException {
	private final Class<?> entity;
	private final String property;

	public AbstractEntityException(Class<?> entity, String property, String message) {
		super(message);
		this.entity = entity;
		this.property = property;
	}

	public AbstractEntityException(Class<?> entity, String property, String message, Throwable throwable) {
		super(message, throwable);
		this.entity = entity;
		this.property = property;
	}

	public String getEntityName() {
		return entity.getSimpleName();
	}

	public String getProperty() {return property;}
}
