package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class AbstractTimestampedEntity {
	
	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public AbstractTimestampedEntity() {}
}
