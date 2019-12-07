package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_DEVICE_UID;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "device",
    uniqueConstraints = {
        @UniqueConstraint(name = UQ_DEVICE_DEVICE_UID, columnNames = {"device_uid"})
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Device {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id_device")
  private Long id;

  @CreationTimestamp
 @Column(name = "created_at")
private ZonedDateTime createdAt;

  @UpdateTimestamp
 @Column(name = "updated_at")
private ZonedDateTime updatedAt;

  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(
      name = "id_sensor",
      referencedColumnName = "id_sensor",
      foreignKey = @ForeignKey(name = "fk_device_sensor")
  )
  private Sensor sensor;

  @NotNull
  @Column(name = "device_uid")
  private String deviceUid;

  @ManyToOne
  @JoinColumn(name = "id_device_group", foreignKey = @ForeignKey(name = "fk_device_id_device_group"))
  private DeviceGroup deviceGroup;

  @OneToOne(mappedBy = "device")
  private DeviceData deviceData;

}
