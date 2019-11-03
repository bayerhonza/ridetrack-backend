package com.ensimag.ridetrack.models;


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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_user", unique = true)
  private long id;

  @NotNull
  @Size(max = 100)
  @Column(name = "username", unique = true)
  private String username;

  @NotNull
  @Column(name = "hash_password")
  private String hashPassword;

  @NotNull
  @Size(max = 100)
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @Size(max = 100)
  @Column(name = "surname", nullable = false)
  private String surname;

  @Email
  @Column(name = "email")
  private String email;

  @ManyToOne
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
