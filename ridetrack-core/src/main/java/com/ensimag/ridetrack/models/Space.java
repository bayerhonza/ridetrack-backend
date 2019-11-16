package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "space",
    uniqueConstraints = {
        @UniqueConstraint(name = UQ_SPACE_CLIENT_SPACE_NAME, columnNames = {"name", "client_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space extends AbstractTimestampEntity {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_space")
  private Long id;

  @NotBlank
  @Column(name = "name")
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_space_client_id"))
  private Client owner;

  @OneToMany(mappedBy = "space")
  private final Set<User> users = new HashSet<>();

}
