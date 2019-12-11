package com.ensimag.ridetrack.models.acl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
