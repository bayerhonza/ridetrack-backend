package com.ensimag.ridetrack.models;

import java.util.Collections;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_client", unique = true)
  private long id;

  @NotNull
  @Column(name = "client_name", unique = true)
  private String clientName;

  @NotNull
  @Column(name = "full_name")
  private String fullName;

  @OneToMany(mappedBy = "owner")
  private Set<Space> spaces = Collections.emptySet();

}
