package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

@MappedSuperclass
public class AbstractTimestampEntity {

	@Column(name = "created")
	private ZonedDateTime created;

	@Column(name = "updated")
	private ZonedDateTime updated;

	@PrePersist
	protected void onCreate() {
		created = ZonedDateTime.now();
		if (updated == null) {
			updated = created;
		}
	}

	@PostUpdate
	protected void onUpdate() {
		updated = ZonedDateTime.now();
	}

}
