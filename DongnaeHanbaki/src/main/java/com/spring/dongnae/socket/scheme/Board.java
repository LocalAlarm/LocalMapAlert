package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "moimBoards")
public class Board {
    @Id
    private String id;
    private String moimId;
    private String title;
    private String content;
    private String author;
    private List<Image> images;
    private List<Comment> comments;
    private List<String> likes;
    private Date createdDate; // 정렬에 사용할 필드
    
    @DBRef
    private Moim moim;
    
    public Board(String moimId, String title, String content, String author) {
    	this.setMoimId(moimId);
    	this.setTitle(title);
    	this.setContent(content);
    	this.setAuthor(author);
    	this.setImages(new ArrayList<Image>());
    	this.setComments(new ArrayList<Comment>());
    	this.setLikes(new ArrayList<String>());
    	this.setCreatedDate(createdDate = new Date());;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
	public String getMoimId() {
		return moimId;
	}

	public void setMoimId(String moimId) {
		this.moimId = moimId;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    public void addImage(Image image) {
    	this.images.add(image);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    
    public boolean deleteComment(String commentId) {
    	return this.comments.removeIf(comment -> comment.getId().equals(commentId));
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
    
    public boolean toggleLike(String userEmail) {
        if (likes.contains(userEmail)) {
            likes.remove(userEmail);
            return false;
        } else {        	
        	likes.add(userEmail);
        	return true;
        }
    }

    public int getLikesCount() {
    	return likes.size();
    }
    
    public Moim getMoim() {
        return moim;
    }

    public void setMoim(Moim moim) {
        this.moim = moim;
    }

    
    public Date getCreatedDate() {
    	return createdDate;
    }
    
    public Date setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    	return createdDate;
    }

	@Override
	public String toString() {
		return "Board [id=" + id + ", moimId=" + moimId + ", title=" + title + ", content=" + content + ", author="
				+ author + ", images=" + images + ", comments=" + comments + ", likes=" + likes + ", createdDate="
				+ createdDate + ", moim=" + moim + "]";
	}   
    
}
