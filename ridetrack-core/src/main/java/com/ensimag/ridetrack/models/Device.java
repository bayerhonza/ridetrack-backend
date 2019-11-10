package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_DEVICE_UID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "device", uniqueConstraints = {
    @UniqueConstraint(name = UQ_DEVICE_DEVICE_UID, columnNames = {"device_uid"})
})
@Getter
@Setter
@NoArgsConstructor
public class Device extends AbstractTimestampEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_device", nullable = false)
  private long id;

  @OneToOne(cascade = {CascadeType.ALL})
  private Sensor sensor;

  @NotNull
  @Column(name = "device_uid")
  private String deviceUid;

  @ManyToOne
  @JoinColumn(name = "id_device_group")
  private DeviceGroup deviceGroup;

}
