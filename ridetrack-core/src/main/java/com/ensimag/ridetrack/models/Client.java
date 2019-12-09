package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_CLIENT_CLIENT_NAME;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "client",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "client_name" }, name = UQ_CLIENT_CLIENT_NAME)
		}
)
public class Client extends AbstractTimestampedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "client_sequence")
	@GenericGenerator(name = "client_sequence", strategy = "native")
	@Column(name = "id_client")
	private Long id;
	
	@NotBlank
	@Column(name = "client_name")
	private String clientName;
	
	@NotBlank
	@Column(name = "full_name")
	private String fullName;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<Space> spaces = new HashSet<>();
	
	@OneToMany(mappedBy = "assignedClient", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<ClientUser> clientUsers = new HashSet<>();
	
	public Client() {
		// no-arg constructor
	}
	
	public void addSpace(Space space) {
		spaces.add(space);
	}
	
	public void addClientUser(ClientUser clientUser) {
		clientUsers.add(clientUser);
	}
	
	public void removeClientUser(ClientUser clientUser) {
		clientUsers.remove(clientUser);
	}
	
	public void removeSpace(Space space) {
		spaces.remove(space);
	}
	
}
