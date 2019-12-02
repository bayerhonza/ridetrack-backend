package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_GROUP_SPACE_GROUP_NAME;

import java.time.ZonedDateTime;
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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
    name = "device_group",
    uniqueConstraints = {
        @UniqueConstraint(
            name = UQ_DEVICE_GROUP_SPACE_GROUP_NAME, columnNames = {"name", "id_space"}),
    }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_dev_group")
  private Long id;

  @CreationTimestamp
 @Column(name = "created_at")
private ZonedDateTime createdAt;

  @UpdateTimestamp
 @Column(name = "updated_at")
private ZonedDateTime updatedAt;

  @Column(name = "name")
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "id_space", foreignKey = @ForeignKey(name = "fk_devgroup_id_space"))
  private Space space;

  @OneToMany(mappedBy = "deviceGroup")
  private Set<Device> devices;


}
