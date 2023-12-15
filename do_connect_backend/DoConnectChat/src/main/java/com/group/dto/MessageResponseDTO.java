package com.group.dto;

import java.util.Date;

public class MessageResponseDTO {

	private String message;

	private Date postedAt;

	private String postedBy;

	public MessageResponseDTO() {
	}

	public MessageResponseDTO(String message, Date postedAt, String postedBy) {
		this.message = message;
		this.postedAt = postedAt;
		this.postedBy = postedBy;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	@Override
	public String toString() {
		return "MessageResponseDTO{" +
				"message='" + message + '\'' +
				", postedAt=" + postedAt +
				", postedBy='" + postedBy + '\'' +
				'}';
	}
}
