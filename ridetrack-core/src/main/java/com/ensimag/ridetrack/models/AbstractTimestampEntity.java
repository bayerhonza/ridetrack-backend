package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class AbstractTimestampEntity {

	@Column(name = "created")
	@CreationTimestamp
	private ZonedDateTime created;

	@Column(name = "updated")
	@UpdateTimestamp
	private ZonedDateTime updated;

}
