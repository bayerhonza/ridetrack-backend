package com.ensimag.ridetrack.validation;

public enum ValidationPatterns {

	USERNAME("^[a-z0-9_-]{3,15}$"),
	CLIENT_NAME("^[a-zA-B0-9_-]{3,15}$");

	private final String regex;

	ValidationPatterns(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}
}
