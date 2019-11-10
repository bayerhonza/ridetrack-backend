package com.ensimag.ridetrack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sensors")
public class Sensor extends AbstractTimestampEntity {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_sensor")
  private long id;

  @OneToOne(mappedBy = "sensor")
  private Device device;

  @NotBlank(message = "deviceUID may not be empty")
  @Column(name = "device_uid")
  private String deviceUid;
}
