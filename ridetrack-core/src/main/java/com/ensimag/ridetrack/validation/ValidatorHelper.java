package com.ensimag.ridetrack.validation;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class ValidatorHelper {

	private final Map<ValidationPatterns, Pattern> patterns = new EnumMap<>(
		ValidationPatterns.class);

	private ValidatorHelper() {
		patterns.put(ValidationPatterns.USERNAME,
			Pattern.compile(ValidationPatterns.USERNAME.getRegex()));
		patterns.put(ValidationPatterns.CLIENT_NAME,
			Pattern.compile(ValidationPatterns.CLIENT_NAME.getRegex()));
	}

	public boolean validateUsername(String username) {
		Pattern usernamePattern = patterns.get(ValidationPatterns.USERNAME);
		return usernamePattern.matcher(username).matches();
	}

	public boolean validateClientName(String clientName) {
		Pattern usernamePattern = patterns.get(ValidationPatterns.CLIENT_NAME);
		return usernamePattern.matcher(clientName).matches();
	}
}
