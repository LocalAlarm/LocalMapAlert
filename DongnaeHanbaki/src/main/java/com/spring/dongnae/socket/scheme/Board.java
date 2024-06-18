package com.spring.dongnae.socket.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "moimBoards")
public class Board {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private List<String> images;
    private List<Comment> comments;
    private List<String> likes;
    
    @DBRef
    private Moim moim;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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


	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", content=" + content + ", author=" + author + ", images="
				+ images + ", comments=" + comments + ", likes=" + likes + ", moim=" + moim + "]";
	}
}
