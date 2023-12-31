package com.group.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Size(max = 512)
    @Column(name = "message", nullable = false)
    private String message;

    @CreationTimestamp
    @Column(name = "posted_at", nullable = false, updatable = false)
    private Date postedAt;

    @NotEmpty
    @Column(name = "posted_by", nullable = false)
    private String postedBy;

    public Message(@NotNull @NotEmpty @Size(max = 512) String message, @NotNull @NotEmpty String postedBy) {
        super();
        this.message = message;
        this.postedBy = postedBy;
    }

    public Message() {
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

    public Long getId() {
        return id;
    }

    public Date getPostedAt() {
        return postedAt;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", postedAt=" + postedAt +
                ", postedBy='" + postedBy + '\'' +
                '}';
    }
}
