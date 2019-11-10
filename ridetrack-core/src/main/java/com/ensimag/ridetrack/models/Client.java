package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_CLIENT_CLIENT_NAME;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_name"}, name = UQ_CLIENT_CLIENT_NAME)
    }
)
public class Client extends AbstractTimestampEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_client", unique = true)
  private long id;

  @NotBlank
  @Column(name = "client_name")
  private String clientName;

  @NotBlank
  @Column(name = "full_name")
  private String fullName;

  @OneToMany(mappedBy = "owner")
  private final Set<Space> spaces = new HashSet<>();

}
