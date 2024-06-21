package com.spring.dongnae.socket.scheme;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Comment {
    @Id
    private String id;
    private String content;
    private String author;
    private Date createdDate;
    
    public Comment(String content) {
        this.content = content;
        this.createdDate = new Date();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
    
}
