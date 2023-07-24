package com.maxence_macia.RezoProjectJava.messages;

public class MessageResponse {
	private int status;
	private String author;
	private String content;
	private long timeStamp;
	
	public MessageResponse() {}
	public MessageResponse(int status, String author, String content, long timeStamp) {
		super();
		this.status = status;
		this.author = author;
		this.content = content;
		this.timeStamp = timeStamp;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
