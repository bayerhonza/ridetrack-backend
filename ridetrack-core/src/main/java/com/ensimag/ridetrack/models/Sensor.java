package com.ensimag.ridetrack.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_sensor", nullable = false)
  private long id;

  @OneToOne(mappedBy = "sensor")
  private Device device;

  @NotBlank
  @Column(name = "device_uid")
  private String deviceUid;
}
