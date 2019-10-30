package com.ensimag.ridetrack.models;

import java.util.Collections;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "space",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "client_id"})}
)
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Space {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_space", unique = true)
  private long id;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client owner;

  @OneToMany(mappedBy = "space")
  private Set<User> users = Collections.emptySet();

}
