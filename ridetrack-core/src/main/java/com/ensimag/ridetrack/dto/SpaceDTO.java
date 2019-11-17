package com.ensimag.ridetrack.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceDTO {

	@Pattern(regexp = "^[a-z0-9_][a-z0-9._-]{2,15}$", message = "Non-valid space name")
	private String name;

	@Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "Non-valid clientName")
	@NotBlank(message = "client name cannot be empty")
	private String clientName;

}
