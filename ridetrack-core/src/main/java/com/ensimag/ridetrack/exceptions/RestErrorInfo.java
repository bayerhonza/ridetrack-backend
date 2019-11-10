package com.ensimag.ridetrack.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RestErrorInfo {

	private String timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

}
