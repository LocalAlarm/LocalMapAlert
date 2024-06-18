package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
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
    private String profilePic;
    private String profilePicPI;
    @DBRef
    private String leader;
    @DBRef
    private List<UserRooms> subLeader;
    @DBRef
    private List<UserRooms> participants;
    @DBRef
    private List<Board> boards;
    public Moim() {
    	this.setSubLeader(new ArrayList<UserRooms>());
    	this.setParticipants(new ArrayList<UserRooms>());
    	this.setBoards(new ArrayList<Board>());
    	this.setProfilePicPI(null);
    }
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
    
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
    
	public String getProfilePicPI() {
		return profilePicPI;
	}

	public void setProfilePicPI(String profilePicPI) {
		this.profilePicPI = profilePicPI;
	}
	
    public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public List<UserRooms> getSubLeader() {
		return subLeader;
	}

	public void setSubLeader(List<UserRooms> subLeader) {
		this.subLeader = subLeader;
	}
	
	public void addSubLeader(UserRooms userRoom) {
		this.subLeader.add(userRoom);
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
		return "Moim [id=" + id + ", name=" + name + ", description=" + description + ", profilePic=" + profilePic
				+ ", profilePicPI=" + profilePicPI + ", leader=" + leader + ", subLeader=" + subLeader
				+ ", participants=" + participants + ", boards=" + boards + "]";
	}

}