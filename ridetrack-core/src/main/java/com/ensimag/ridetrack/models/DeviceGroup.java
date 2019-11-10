package com.ensimag.ridetrack.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    name = "device_group",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uniqueConstDevGroupNameAndSpace",
            columnNames = {"name", "id_space"}
        ),
    }
)
@Getter
@Setter
public class DeviceGroup {

  @Id
  @NotNull
  @Column(name = "id_dev_group")
  private long id;


  @Column(name = "name")
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "id_space")
  private Space space;

  @OneToMany(mappedBy = "space")
  private Set<User> users;

}
