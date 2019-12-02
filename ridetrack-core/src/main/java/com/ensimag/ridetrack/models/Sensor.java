package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sensors")
public class Sensor {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_sensor")
  private Long id;

  @CreationTimestamp
 @Column(name = "created_at")
private ZonedDateTime createdAt;

  @UpdateTimestamp
 @Column(name = "updated_at")
private ZonedDateTime updatedAt;

  @OneToOne(mappedBy = "sensor")
  private Device device;

  @NotBlank(message = "deviceUID may not be empty")
  @Column(name = "device_uid")
  private String deviceUid;
}
