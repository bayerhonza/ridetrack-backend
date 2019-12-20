package com.ensimag.ridetrack.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "space_users")
@Entity
@PrimaryKeyJoinColumn(name = "id_space_user", foreignKey = @ForeignKey(name = "FK_SPACE_USER_USER_ID"))
public class SpaceUser extends RtUser {
	
	
	@NotBlank(message = "name cannot be empty")
	@Size(max = 100)
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "surname cannot be empty")
	@Size(max = 100)
	@Column(name = "surname")
	private String surname;
	
	@Email
	@Column(name = "email")
	private String email;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_space", foreignKey = @ForeignKey(name = "fk_user_id_space"))
	private Space space;
	
	public SpaceUser() {
		// no-arg constructor
	}
	
}
