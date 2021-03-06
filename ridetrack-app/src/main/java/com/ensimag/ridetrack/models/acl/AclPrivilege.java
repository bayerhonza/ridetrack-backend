package com.ensimag.ridetrack.models.acl;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.ensimag.ridetrack.auth.privileges.PrivilegeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(
    name = "UQ_PRIVILEGE_NAME", columnNames = {"privilege_name"}
))
public class AclPrivilege implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_privilege_sequence")
  @GenericGenerator(name = "acl_privilege_sequence", strategy = "native")
  @Column(name = "id_privilege")
  private Long privilegeId;
  
  @Column(name = "privilege_name")
  private String privilegeName;

  @CreationTimestamp
  @Column(name = "created_at")
  protected ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  protected ZonedDateTime updatedAt;

  public AclPrivilege() {
  }

  public AclPrivilege(String privilegeName) {
    this.privilegeName = privilegeName;
  }
  
  public AclPrivilege(PrivilegeEnum privilegeEnum) {
    this.privilegeName = privilegeEnum.getName();
  }
  
  
  public static AclPrivilege of(String operationName) {
    return new AclPrivilege(operationName);
  }
  
  public static AclPrivilege of(PrivilegeEnum privilegeEnum) {
    return new AclPrivilege(privilegeEnum);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof AclPrivilege) {
      return privilegeName.equals(((AclPrivilege) obj).privilegeName);
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
