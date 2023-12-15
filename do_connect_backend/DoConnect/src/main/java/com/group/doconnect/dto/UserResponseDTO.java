package com.group.doconnect.dto;

public class UserResponseDTO {
	
	private Long id;
	private String username;
	private String name;
	private String email;
	private boolean isAdmin;

	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public UserResponseDTO(Long id, String username, String name, String email, boolean isAdmin) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "UserResponseDTO{" +
				"id=" + id +
				", username='" + username + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", isAdmin=" + isAdmin +
				'}';
	}
}
