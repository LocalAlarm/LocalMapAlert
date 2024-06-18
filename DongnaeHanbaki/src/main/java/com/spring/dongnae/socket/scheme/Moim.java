package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.dongnae.user.vo.UserVO;

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
    	setInitValue();
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
	
	public void addSubLeader(UserRooms userRoom) {
		this.subLeader.add(userRoom);
	}

	public List<UserRooms> getParticipants() {
        return participants;
    }

    public void addParticipant(UserRooms userRoom) {
        this.participants.add(userRoom);
    }
    
    public void removeParticipant(UserRooms participant) {
        this.participants.remove(participant);
        participant.removeMoim(this);
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }
    
    private void setInitValue() {
    	this.subLeader = new ArrayList<UserRooms>();
    	this.participants = new ArrayList<UserRooms>();
    	this.boards = new ArrayList<Board>();
    	this.setProfilePicPI(null);
    }

	@Override
	public String toString() {
		return "Moim [id=" + id + ", name=" + name + ", description=" + description + ", profilePic=" + profilePic
				+ ", profilePicPI=" + profilePicPI + ", leader=" + leader + ", subLeader=" + subLeader
				+ ", participants=" + participants + ", boards=" + boards + "]";
	}

}