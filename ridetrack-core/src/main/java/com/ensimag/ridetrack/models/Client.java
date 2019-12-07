package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_CLIENT_CLIENT_NAME;

import java.time.ZonedDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_name"}, name = UQ_CLIENT_CLIENT_NAME)
    }
)
public class Client {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id_client")
  private Long id;

  @NotBlank
  @Column(name = "client_name")
  private String clientName;

  @NotBlank
  @Column(name = "full_name")
  private String fullName;

  @CreationTimestamp
 @Column(name = "created_at")
private ZonedDateTime createdAt;

  @UpdateTimestamp
 @Column(name = "updated_at")
private ZonedDateTime updatedAt;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
  private final Set<Space> spaces = new HashSet<>();
  
  public void addSpace(Space space) {
    spaces.add(space);
  }
  
  public void removeSpace(Space space) {
    spaces.remove(space);
  }

}
