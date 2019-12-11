package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_CLIENT_CLIENT_NAME;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "client",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"client_name"}, name = UQ_CLIENT_CLIENT_NAME)
        }
)
@PrimaryKeyJoinColumn(name = "id_client", foreignKey = @ForeignKey(name = "FK_DEVICE_OID"))
public class Client extends AclObjectIdentity {

    @NotBlank
    @Column(name = "client_name")
    private String clientName;

    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private final Set<Space> spaces = new HashSet<>();

    @OneToMany(mappedBy = "assignedClient", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private final Set<ClientUser> clientUsers = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;

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
