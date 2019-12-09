package com.ensimag.ridetrack.models;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_user")
public class ClientUser extends RtUser {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT_USER_CLIENT_ID"))
	private Client assignedClient;
	
}
