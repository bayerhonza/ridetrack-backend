package com.ensimag.ridetrack.models;


import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_USER_USERNAME;

import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(name = UQ_USER_USERNAME, columnNames = {"username"})
})
public class User extends AbstractTimestampEntity {

  @Id
  @NotNull(message = "id cannot be empty")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_user")
  private long id;

  @NotBlank(message = "username cannot be empty")
  @Size(max = 100)
  @Column(name = "username")
  private String username;

  @NotBlank(message = "password cannot be empty")
  @Column(name = "hash_password")
  private String hashPassword;

  @NotBlank(message = "name cannot be empty")
  @Size(max = 100)
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank(message = "surname cannot be empty")
  @Size(max = 100)
  @Column(name = "surname", nullable = false)
  private String surname;

  @Email
  @Column(name = "email")
  private String email;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_space")
  private Space space;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_permissions_mapping",
      joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id_user")}
  )
  @MapKeyJoinColumn(name = "id_group")
  private Map<DeviceGroup, DeviceGroupPermissions> deviceGroupPermissions;



}
