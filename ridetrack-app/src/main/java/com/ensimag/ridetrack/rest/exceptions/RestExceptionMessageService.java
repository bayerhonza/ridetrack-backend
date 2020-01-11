package com.ensimag.ridetrack.rest.exceptions;

import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import java.util.Map;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:restExceptions_en.properties")
public class RestExceptionMessageService {

	private final Environment env;

	private final Map<String, String> constraintMsgMap = Map.of(
		RideTrackConstraint.UQ_CLIENT_CLIENT_NAME, "error.rest.client.duplicate.clientName",
		RideTrackConstraint.UQ_DEVICE_DEVICE_UID, "error.rest.device.duplicate.deviceUID"
	);

	public RestExceptionMessageService(Environment env) {
		this.env = env;
	}

	public String getConstraintErrorCode(String constraintName) {
		StringBuilder result = new StringBuilder();
		constraintMsgMap.entrySet().stream()
			.filter(it -> it.getKey().equals(constraintName))
			.findAny()
			.ifPresentOrElse(
				it -> result.append(it.getValue()),
				() -> result.append("error.rest.unknown")
			);
		return result.toString();
	}

	public String translateCodeIntoMsg(String constraintName) {
		StringBuilder result = new StringBuilder();
		constraintMsgMap.entrySet()
			.stream()
			.filter(it -> it.getKey().equals(constraintName))
			.findAny()
			.ifPresentOrElse(
				it -> {
					String msg = env.getProperty(it.getValue(), String.class);
					if (msg == null) {
						result.append(constraintName);
					} else {
						result.append(msg);
					}
				},
				() -> result.append(constraintName));
		return result.toString().replaceAll("\"", "");
	}


}
