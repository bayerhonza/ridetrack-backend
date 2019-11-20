package com.ensimag.ridetrack.models;

import com.ensimag.ridetrack.models.constants.Operation;
import java.time.ZonedDateTime;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserDevGroupConfigPK.class)
public class UserDevGroupConfig {

	@Id
	@Column(name = "id_user_config")
	private Long userConfigId;

	@CreationTimestamp
	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@Id
	@Column(name = "id_device_group")
	private Long devGroupId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_config", nullable = false, updatable = false, insertable = false)
	private UserConfiguration userConfiguration;

	@ManyToOne
	@JoinColumn(name = "id_device_group", nullable = false, updatable = false, insertable = false)
	private DeviceGroup deviceGroup;

	@ElementCollection(targetClass = Operation.class)
	@CollectionTable(
		name = "permissions_operations",
		joinColumns = {
			@JoinColumn(
				referencedColumnName = "id_user_config",
				name = "id_user_config",
				foreignKey = @ForeignKey(name = "fk_perm_operations_userconfig")
			),
			@JoinColumn(
				referencedColumnName = "id_device_group",
				name = "id_device_group",
				foreignKey = @ForeignKey(name = "fk_perm_operations_devgroup_id")
			)
		},
		foreignKey = @ForeignKey(name = "fk_perm_operations_id_perm_operation"))
	@Enumerated(EnumType.STRING)
	@Column(name = "operation")
	private Set<Operation> permissions;


}
