package com.ensimag.ridetrack.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_device", nullable = false)
  private long id;

  @OneToOne(cascade = {CascadeType.ALL})
  private Sensor sensor;

  @Column(name = "device_uid")
  private String deviceUid;
}
