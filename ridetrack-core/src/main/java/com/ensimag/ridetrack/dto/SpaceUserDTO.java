package com.ensimag.ridetrack.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SpaceUserDTO {
	
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{2,15}$")
	private String username;
	
	@NotBlank(message = "password cannot be empty")
	private String password;
	
	@NotBlank(message = "name cannot be empty")
	private String name;
	
	@NotBlank(message = "surname cannot be empty")
	private String surname;
	
	private boolean enabled;
}
