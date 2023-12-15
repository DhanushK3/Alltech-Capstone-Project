package com.group.doconnect.dto;

public class QuestionUpdateDTO {

	private boolean isApproved;

	public QuestionUpdateDTO(boolean isApproved) {
		super();
		this.isApproved = isApproved;
	}

	public QuestionUpdateDTO() {
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
		return "QuestionUpdateDTO{" +
				"isApproved=" + isApproved +
				'}';
	}
}
