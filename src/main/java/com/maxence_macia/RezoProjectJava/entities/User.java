package com.maxence_macia.RezoProjectJava.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="login")
	private String login;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role;
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Token> tokens;
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Message> messages;
	@ManyToMany
	@JoinTable(
			name="users_liked",
			joinColumns=@JoinColumn(name="users_id"),
			inverseJoinColumns=@JoinColumn(name="messages_id")
			)
	private List<Message> messageLiked;
	
	public User() {}
	public User(
			String login,
			String email,
			String password,
			Role role
			) {
		super();
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.role.getAuthorities();
	}
	@Override
	public String getUsername() {
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Token> getTokens() {
		return tokens;
	}
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	public List<Message> getMessageLiked() {
		return messageLiked;
	}
	public void setMessageLiked(List<Message> messageLiked) {
		this.messageLiked = messageLiked;
	}
}
