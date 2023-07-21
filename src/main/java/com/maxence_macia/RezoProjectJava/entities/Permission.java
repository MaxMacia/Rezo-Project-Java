package com.maxence_macia.RezoProjectJava.entities;

public enum Permission {
	ADMIN_CREATE("admin:create"),
	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_DELETE("admin:delete");
	
	private final String permission;
	
	private Permission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
