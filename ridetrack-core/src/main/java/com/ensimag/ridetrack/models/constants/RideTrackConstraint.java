package com.ensimag.ridetrack.models.constants;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import java.util.List;

public class RideTrackConstraint {

	/** Name for unique constraint of {@link Client#getClientName()} */
	public static final String UQ_CLIENT_CLIENT_NAME = "uq_client_client_name";

	/** Name for unique constraint of {@link Device#getDeviceUid()} */
	public static final String UQ_DEVICE_DEVICE_UID = "uq_device_device_uid";

	/**
	 * Name for unique constraint of {@link DeviceGroup#getName()} and {@link
	 * DeviceGroup#getSpace()} that assures the uniqueness of device group in a space.
	 */
	public static final String UQ_DEVICE_GROUP_SPACE_GROUP_NAME = "uq_device_group_space_group_name";

	/**
	 * Name for unique constraint of {@link Space#getName()} and {@link Space#getOwner()} that
	 * assures the uniqueness of space name for one client }
	 */
	public static final String UQ_SPACE_CLIENT_SPACE_NAME = "uq_space_client_space_name";

	public static final String UQ_USER_USERNAME = "uq_user_username";

	public static List<String> getAllConstraints() {
		return List.of(
			UQ_CLIENT_CLIENT_NAME,
			UQ_DEVICE_DEVICE_UID,
			UQ_DEVICE_GROUP_SPACE_GROUP_NAME,
			UQ_SPACE_CLIENT_SPACE_NAME,
			UQ_USER_USERNAME
		);
	}

	private RideTrackConstraint() {

	}


}
