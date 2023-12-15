package com.group.doconnect.dto;

public class ChatMessageDTO {
	
	private String message;
	
	private String postedBy;

	public ChatMessageDTO(String message, String postedBy) {
		super();
		this.message = message;
		this.postedBy = postedBy;
	}

	public ChatMessageDTO() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}


	@Override
	public String toString() {
		return "ChatMessageDTO{" +
				"message='" + message + '\'' +
				", postedBy='" + postedBy + '\'' +
				'}';
	}
}
