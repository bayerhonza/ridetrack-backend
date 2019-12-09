package com.ensimag.ridetrack.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SpaceUserDTO {
	
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{2,}$")
	private String username;
	
	@NotBlank(message = "password cannot be empty")
	private String password;
	
	private String name;
	
	private String surname;
	
	private boolean enabled;
	
	private String spaceName;
	
	@Email
	private String email;
}
