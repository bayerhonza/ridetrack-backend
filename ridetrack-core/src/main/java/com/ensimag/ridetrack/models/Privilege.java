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
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(
    name = "UQ_PRIVILEGE_NAME", columnNames = {"privilege_name"}
))
public class Privilege implements GrantedAuthority {

  private static final long serialVersionUID = -870668537914976236L;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="privilege_sequence")
  @GenericGenerator(name = "privilege_sequence", strategy = "native")
  @Column(name = "id_privilege")
  private Long id;

  @Column(name = "privilege_name")
  private String privilegeName;

  public Privilege() {
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
