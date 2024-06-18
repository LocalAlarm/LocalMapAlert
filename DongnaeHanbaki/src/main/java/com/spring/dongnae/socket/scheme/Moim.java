package com.spring.dongnae.socket.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "moims")
public class Moim {

    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private List<UserRooms> participants;
    @DBRef
    private List<Board> boards;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<UserRooms> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserRooms> participants) {
        this.participants = participants;
    }

    public void addParticipant(UserRooms userRoom) {
        this.participants.add(userRoom);
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addPost(Board board) {
        this.boards.add(board);
    }

	@Override
	public String toString() {
		return "Moim [id=" + id + ", name=" + name + ", description=" + description + ", participants=" + participants
				+ ", boards=" + boards + "]";
	}
}