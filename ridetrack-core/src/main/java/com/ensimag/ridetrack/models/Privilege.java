package com.ensimag.ridetrack.models;

import javax.persistence.*;

import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.models.acl.SidType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(
    name = "UQ_PRIVILEGE_NAME", columnNames = {"privilege_name"}
))
@PrimaryKeyJoinColumn(name = "id_privilege", foreignKey = @ForeignKey(name = "FK_PRIVILEGE_SID"))
public class Privilege extends AclSid implements GrantedAuthority {

  @Column(name = "privilege_name")
  private String privilegeName;

  @CreationTimestamp
  @Column(name = "created_at")
  protected ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  protected ZonedDateTime updatedAt;

  public Privilege() {
    super(SidType.PRIVILEGE);
  }

  public Privilege(String privilegeName) {
    this.privilegeName = privilegeName;
  }

  public static Privilege of(String operationName) {
    return new Privilege(operationName);
  }

  @Override
  public String getAuthority() {
    return privilegeName;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof Privilege) {
      return privilegeName.equals(((Privilege) obj).privilegeName);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return this.privilegeName.hashCode();
  }

  @Override
  public String toString() {
    return this.privilegeName;
  }
}
