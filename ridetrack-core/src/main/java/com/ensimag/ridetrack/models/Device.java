package com.ensimag.ridetrack.models;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_device", nullable = false)
  private long id;

  @OneToOne(cascade = {CascadeType.ALL})
  private Sensor sensor;

  @NotNull
  @Column(name = "device_uid")
  private String deviceUid;
}
