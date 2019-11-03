package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.exceptions.RidetrackException;
import com.ensimag.ridetrack.models.User;

public interface SpaceService {

  public void addUserToSpace(User user, Long spaceId) throws RidetrackException;

  public void removeUserFromSpace(User user, Long spaceId) throws RidetrackException;

}
