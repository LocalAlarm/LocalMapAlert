package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "moims")
public class Moim {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private String profilePic;
    private String profilePicPI;
    private String leader;
    private String chatRoomId;
    private List<String> subLeader;
    private List<String> participants;
    private List<String> banUserList;
    
    public Moim() {
    	setInitValue();
    }
    public Moim(String name, String description, Map<String, String> profilePicMap) {
    	setInitValue();
        this.setName(name);
        this.setDescription(description);
        if (profilePicMap == null) {
            this.setProfilePic("https://res.cloudinary.com/djlee4yl2/image/upload/v1713834954/logo/github_logo_icon_tpisfg.png");
            this.setProfilePicPI(null);
        } else {
        	this.setProfilePic(profilePicMap.get("url"));
        	this.setProfilePicPI(profilePicMap.get("public_id"));        	
        }
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

	public void setLeader(UserRooms userRooms) throws Exception {
		userRooms.addMasterMoims(this);
		this.leader = userRooms.getId();
	}
	
	public String getChatRoomId() {
		return chatRoomId;
	}
	
	public void setChatRoomId(ChatRoom chatRoom) {
		if (chatRoom.getId() != null) {			
			this.chatRoomId = chatRoom.getId();
		} else {
			throw new NullPointerException("ChatRoom ID is null.");
		}
	}
	
	public List<String> getSubLeader() {
		return subLeader;
	}
	
	public void addSubLeader(UserRooms userRoom) {
		this.subLeader.add(userRoom.getId());
	}
	
	public void deleteSubLeader(UserRooms userRoom) {
		this.subLeader.remove(userRoom.getId());
	}

	public List<String> getParticipants() {
        return participants;
    }

    public void addParticipant(UserRooms userRoom) {
        this.participants.add(userRoom.getId());
    }
    
    public void removeParticipant(UserRooms participant) {
        this.participants.remove(participant.getId());
        participant.removeMoim(this);
    }
    
    public void banUser(UserRooms userRoom) {
    	this.participants.remove(userRoom.getId());
    	userRoom.removeMoim(this);
    	banUserList.add(userRoom.getId());
    }
    
	public List<String> getBanUserList() {
		return banUserList;
	}
	
	public void addBanUserList(UserRooms userRoom) {
		this.banUserList.add(userRoom.getId());
	}
	
	public void removeBanUserList(UserRooms userRoom) {
		this.banUserList.remove(userRoom.getId());
	}
    
    
    
    private void setInitValue() {
    	this.subLeader = new ArrayList<String>();
    	this.participants = new ArrayList<String>();
    	this.banUserList = new ArrayList<String>();
    	this.setProfilePicPI(null);
    }
	@Override
	public String toString() {
		return "Moim [id=" + id + ", name=" + name + ", description=" + description + ", profilePic=" + profilePic
				+ ", profilePicPI=" + profilePicPI + ", leader=" + leader + ", chatRoomId=" + chatRoomId
				+ ", subLeader=" + subLeader + ", participants=" + participants + ", banUserList=" + banUserList + "]";
	}

}