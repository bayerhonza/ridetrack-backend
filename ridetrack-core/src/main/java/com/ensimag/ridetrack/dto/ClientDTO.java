package com.ensimag.ridetrack.dto;


import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ClientDTO {

	@Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "Non-valid clientName")
	@NotBlank(message = "client name cannot be empty")
	private String clientName;

	@NotBlank(message = "full name cannot be empty")
	private String fullName;

	private Set<String> spaces;

}
