package com.ensimag.ridetrack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "operation", uniqueConstraints = @UniqueConstraint(
    name = "UQ_PRIVILEGE_OPERATION_NAME", columnNames = {"operation_name"}
))
public class Privilege implements GrantedAuthority {

  private static final long serialVersionUID = -870668537914976236L;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id_privilege")
  private Long id;

  @Column(name = "operation_name")
  private String operationName;

  public Privilege() {
  }

  public Privilege(String operationName) {
    this.operationName = operationName;
  }

  public static Privilege of(String operationName) {
    return new Privilege(operationName);
  }

  @Override
  public String getAuthority() {
    return operationName;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof Privilege) {
      return operationName.equals(((Privilege) obj).operationName);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return this.operationName.hashCode();
  }

  @Override
  public String toString() {
    return this.operationName;
  }
}
