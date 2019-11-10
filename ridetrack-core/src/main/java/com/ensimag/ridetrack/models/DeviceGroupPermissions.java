package com.ensimag.ridetrack.models;

import com.ensimag.ridetrack.models.constants.Operation;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "permissions")
public class DeviceGroupPermissions extends AbstractTimestampEntity {


  @Id
  @NotNull
  @Column(name = "id_permissions")
  private long id;

  @ManyToOne
  @JoinColumn(name = "id_group")
  private DeviceGroup deviceGroup;

  @ElementCollection(targetClass = Operation.class)
  @CollectionTable(name = "permissions_operations", joinColumns = @JoinColumn(name = "id_permissions"))
  @Enumerated(EnumType.STRING)
  @Column(name = "operation")
  private Set<Operation> operations;


}
