package com.group.doconnect.dto;

import java.util.List;

public class AnswerRequestDTO {
	
	private String answer;
	private List<String> images;

	public AnswerRequestDTO(String answer, List<String> images) {
		super();
		this.answer = answer;
		this.images = images;
	}

	public AnswerRequestDTO() {
		super();
	}

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "AnswerRequestDTO{" +
				"answer='" + answer + '\'' +
				", images=" + images +
				'}';
	}
}
