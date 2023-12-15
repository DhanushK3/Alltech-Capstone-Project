package com.group.doconnect.dto;

public class AnswerUpdateDTO {
	
	private boolean isApproved;

	public AnswerUpdateDTO(boolean isApproved) {
		super();
		this.isApproved = isApproved;
	}

	public AnswerUpdateDTO() {
		super();
	}

	public boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}


	@Override
	public String toString() {
		return "AnswerUpdateDTO{" +
				"isApproved=" + isApproved +
				'}';
	}
}
