package com.ensimag.ridetrack.models.constants;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RideTrackConstraintTest {

	@Test
	public void testNumberOfConstraints() {
		int count = 0;
		Field[] fields = RideTrackConstraint.class.getFields();
		for (Field field : fields) {
			if (field.getType() == String.class && field.getName().contains("UQ_")) {
				count++;
			}
		}
		Assertions.assertEquals(count, RideTrackConstraint.getAllConstraints().size());
	}

}