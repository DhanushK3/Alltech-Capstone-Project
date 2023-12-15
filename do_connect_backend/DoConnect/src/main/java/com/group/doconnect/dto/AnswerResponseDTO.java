package com.group.doconnect.dto;

import java.util.Date;
import java.util.List;

public class AnswerResponseDTO {

    private long id;
    private String answer;
    private List<String> images;
    private String postedBy;
    private Date postedAt;
    private String approvedBy;
    private boolean isApproved;
    private QuestionResponseDTO question;

    public AnswerResponseDTO(long id, String answer, List<String> images, String postedBy, Date postedAt,
                             String approvedBy, boolean isApproved, QuestionResponseDTO question) {
        super();
        this.id = id;
        this.answer = answer;
        this.images = images;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.approvedBy = approvedBy;
        this.isApproved = isApproved;
        this.question = question;
    }

    public AnswerResponseDTO() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public QuestionResponseDTO getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "AnswerResponseDTO{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", images=" + images +
                ", postedBy='" + postedBy + '\'' +
                ", postedAt=" + postedAt +
                ", approvedBy='" + approvedBy + '\'' +
                ", isApproved=" + isApproved +
                ", question=" + question +
                '}';
    }
}
