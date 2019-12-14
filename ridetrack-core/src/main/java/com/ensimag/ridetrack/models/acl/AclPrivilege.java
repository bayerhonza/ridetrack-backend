package com.ensimag.ridetrack.models.acl;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import com.ensimag.ridetrack.privileges.PrivilegeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(
    name = "UQ_PRIVILEGE_NAME", columnNames = {"privilege_name"}
))
public class AclPrivilege  {
  
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
