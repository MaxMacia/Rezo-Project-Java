package com.maxence_macia.RezoProjectJava.entities;

import java.util.List;

public class Message {
	private long id;
	private String content;
	private long likes;
	private List<User> usersLiked;
	
	public Message() {}
	public Message(
			String content,
			long likes,
			List<User> usersLiked
			) {
		super();
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
