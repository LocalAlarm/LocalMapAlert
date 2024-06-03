package com.spring.dongnae.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class UserSessionService {
	
	private List<Integer> activeUserHashes = Collections.synchronizedList(new ArrayList<>());
	
	public UserSessionService() {
        System.out.println("UserSessionService instance created: " + this);
    }
	
    public void addUserSession(String username) {
        int userHash = username.hashCode();
        activeUserHashes.add(userHash);
        System.out.println("Added user hash: " + userHash);
    }

    public void removeUserSession(String username) {
        int userHash = username.hashCode();
        activeUserHashes.remove(Integer.valueOf(userHash));
        System.out.println("Removed user hash: " + userHash);
    }

    public List<Integer> getActiveUserHashes() {
        return activeUserHashes;
    }
}