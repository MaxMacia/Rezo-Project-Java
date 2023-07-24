package com.maxence_macia.RezoProjectJava.messages;

public class MessageRequest {
	private String content;
	
	public MessageRequest() {}
	public MessageRequest(String content) {
		super();
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
