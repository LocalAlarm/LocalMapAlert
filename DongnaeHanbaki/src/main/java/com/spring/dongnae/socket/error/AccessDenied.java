package com.spring.dongnae.socket.error;

public class AccessDenied extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AccessDenied(String message) {
		super(message);
	}
}
