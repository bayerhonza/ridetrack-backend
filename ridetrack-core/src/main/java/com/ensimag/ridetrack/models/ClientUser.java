package com.ensimag.ridetrack.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ensimag.ridetrack.models.acl.SidType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Table(name = "client_user")
@PrimaryKeyJoinColumn(name = "id_client_user", foreignKey = @ForeignKey(name = "FK_CLIENT_USER_USER_ID"))
public class ClientUser extends RtUser {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT_USER_CLIENT_ID"))
	private Client assignedClient;

	public ClientUser() { }
}
