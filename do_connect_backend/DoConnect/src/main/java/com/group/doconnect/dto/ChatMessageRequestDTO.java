package com.group.doconnect.dto;

public class ChatMessageRequestDTO {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public ChatMessageRequestDTO() {
		super();
	}

	public ChatMessageRequestDTO(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatMessageRequestDTO{" +
				"message='" + message + '\'' +
				'}';
	}
}
