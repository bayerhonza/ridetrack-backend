package com.ensimag.ridetrack.dto;

import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUserDTO {
	@Email
	private String username;
}
