package com.ensimag.ridetrack.models.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "acl_sid",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_ACL_SID_SID_SID_TYPE",
                        columnNames = {"sid_type", "sid"}
                )
        })
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AclSid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_sid_sequence")
    @GenericGenerator(name = "acl_sid_sequence", strategy = "native")
    @Column(name = "sid")
    private Long sid;

    @NotNull
    @Column(name = "sid_type")
    @Enumerated(EnumType.STRING)
    private SidType sidType;
    
    public AclSid() { }

    public AclSid(SidType type) {
        this.sidType = type;
    }
}
