package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_GROUP_SPACE_GROUP_NAME;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "device_group",
    uniqueConstraints = {
        @UniqueConstraint(
            name = UQ_DEVICE_GROUP_SPACE_GROUP_NAME, columnNames = {"name", "id_space"}),
    }
)
@Getter
@Setter
@NoArgsConstructor
public class DeviceGroup extends AbstractTimestampEntity {

  @Id
  @Column(name = "id_dev_group")
  private Long id;


  @Column(name = "name")
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "id_space", foreignKey = @ForeignKey(name = "fk_devgroup_id_space"))
  private Space space;

  @OneToMany(mappedBy = "deviceGroup")
  private Set<Device> devices;


}
