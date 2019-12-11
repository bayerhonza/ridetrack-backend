package com.ensimag.ridetrack.models;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "id_admin", foreignKey = @ForeignKey(name = "FK_ADMIN_USER_ID"))
public final class AdminUser extends RtUser {
	
	public AdminUser() {}
	
	public AdminUser(String username, String password) {
		super(username, password);
	}
	
}
