package com.maxence_macia.RezoProjectJava.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@ManyToOne
	@JoinColumn(name="users_id")
	private User user;
	@Column(name="content")
	private String content;
	@Column(name="likes")
	private long likes;
	@OneToMany(mappedBy="message")
	@Column(name="users_liked")
	private List<User> usersLiked;
	
	public Message() {}
	public Message(
			User user,
			String content,
			long likes,
			List<User> usersLiked
			) {
		super();
		this.user = user;
		this.content = content;
		this.likes = likes;
		this.usersLiked = usersLiked;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public List<User> getUsersLiked() {
		return usersLiked;
	}
	public void setUsersLiked(List<User> usersLiked) {
		this.usersLiked = usersLiked;
	}
}
