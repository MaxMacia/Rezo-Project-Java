package com.maxence_macia.RezoProjectJava.entities;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	USER(Collections.emptySet()),
	ADMIN(
			Set.of(
					Permission.ADMIN_CREATE,
					Permission.ADMIN_READ,
					Permission.ADMIN_UPDATE,
					Permission.ADMIN_DELETE
					)
			);
	
	private final Set<Permission> permissions;
	
	private Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = this.getPermissions()
				.stream()
				.map(
						permissions -> new SimpleGrantedAuthority(permissions.getPermission())
						)
				.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		
		return authorities;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}
}
