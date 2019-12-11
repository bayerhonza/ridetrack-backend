package com.ensimag.ridetrack.models.acl;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class AclEntryPK implements Serializable {
    private Long sid;
    private Long oid;

}
