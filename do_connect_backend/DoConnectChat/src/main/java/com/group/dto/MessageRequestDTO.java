package com.group.dto;

public class MessageRequestDTO {

	private String message;
	
	private String postedBy;

	public MessageRequestDTO() {

	}

	public MessageRequestDTO(String message, String postedBy) {
		this.message = message;
		this.postedBy = postedBy;
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
		return "MessageRequestDTO{" +
				"message='" + message + '\'' +
				", postedBy='" + postedBy + '\'' +
				'}';
	}
}
